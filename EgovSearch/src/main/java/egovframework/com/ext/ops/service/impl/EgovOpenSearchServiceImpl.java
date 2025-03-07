package egovframework.com.ext.ops.service.impl;

import com.google.gson.Gson;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.OnnxEmbeddingModel;
import dev.langchain4j.model.embedding.onnx.PoolingMode;
import egovframework.com.config.EgovSearchConfig;
import egovframework.com.ext.ops.service.BoardVO;
import egovframework.com.ext.ops.service.BoardVectorVO;
import egovframework.com.ext.ops.service.EgovOpenSearchService;
import egovframework.com.ext.ops.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.GetRequest;
import org.opensearch.client.opensearch.core.GetResponse;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.core.UpdateRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service("opsEgovOpenSearchService")
@RequiredArgsConstructor
@Slf4j
public class EgovOpenSearchServiceImpl extends EgovAbstractServiceImpl implements EgovOpenSearchService, InitializingBean {

    @Value("${opensearch.text.indexname}")
    private String textIndexName;

    @Value("${opensearch.vector.indexname}")
    private String vectorIndexName;

    @Value("${app.search-config-path}")
    private String configPath;

    private String modelPath;
    private String tokenizerPath;
    private final OpenSearchClient client;

    @Override
    public void afterPropertiesSet() {
        loadConfig();
    }

    private void loadConfig() {
        try {
            String jsonStr = new String(Files.readAllBytes(Paths.get(configPath)));
            EgovSearchConfig config = new Gson().fromJson(jsonStr, EgovSearchConfig.class);

            this.modelPath = config.getModelPath();
            this.tokenizerPath = config.getTokenizerPath();

        } catch (IOException e) {
            log.error("Failed to load search config: " + e.getMessage());
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    @Override
    public void processOpenSearchOperations(Long nttId, BoardVO boardVO) {
        try {
            performOpenSearchTextOperation(nttId, boardVO);
            performOpenSearchVectorOperation(nttId, boardVO);
        } catch (Exception e) {
            log.error("Error processing OpenSearch operations for nttId: {}", nttId, e);
            throw new RuntimeException("Failed to process OpenSearch operations", e);
        }
    }

    private void performOpenSearchTextOperation(Long nttId, BoardVO boardVO) throws IOException {
        try {

            // HTML 태그 제거
            BoardVO cleanedBoardVO = new BoardVO();
            BeanUtils.copyProperties(boardVO, cleanedBoardVO);
            cleanedBoardVO.setNttCn(StrUtil.cleanString(boardVO.getNttCn()));
            cleanedBoardVO.setNttSj(StrUtil.cleanString(boardVO.getNttSj()));
            
            if (cleanedBoardVO.getLastUpdtPnttm() != null && cleanedBoardVO.getLastUpdtPnttm().trim().isEmpty()) {
                cleanedBoardVO.setLastUpdtPnttm(null);
            }

            GetRequest getRequest = new GetRequest.Builder()
                    .index(textIndexName)
                    .id(String.valueOf(nttId))
                    .build();

            GetResponse<BoardVO> getResponse = client.get(getRequest, BoardVO.class);

            if (getResponse.found()) {
                // Update existing document
                UpdateRequest<BoardVO, BoardVO> updateRequest = new UpdateRequest.Builder<BoardVO, BoardVO>()
                        .index(textIndexName)
                        .id(String.valueOf(nttId))
                        .doc(cleanedBoardVO)
                        .build();

                client.update(updateRequest, BoardVO.class);
                log.info("Updated document in text index for nttId: {}", nttId);
            } else {
                // Insert new document
                IndexRequest<BoardVO> indexRequest = new IndexRequest.Builder<BoardVO>()
                        .index(textIndexName)
                        .id(String.valueOf(nttId))
                        .document(cleanedBoardVO)
                        .build();

                client.index(indexRequest);
                log.info("Inserted new document in text index for nttId: {}", nttId);
            }
        } catch (IOException e) {
            log.error("Error in text operation for nttId: {}", nttId, e);
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    private void performOpenSearchVectorOperation(Long nttId, BoardVO boardVO) throws IOException {
        try {
            // HTML 태그 제거
            BoardVO cleanedBoardVO = new BoardVO();
            BeanUtils.copyProperties(boardVO, cleanedBoardVO);
            cleanedBoardVO.setNttCn(StrUtil.cleanString(boardVO.getNttCn()));
            cleanedBoardVO.setNttSj(StrUtil.cleanString(boardVO.getNttSj()));
            
            if (cleanedBoardVO.getLastUpdtPnttm() != null && cleanedBoardVO.getLastUpdtPnttm().trim().isEmpty()) {
                cleanedBoardVO.setLastUpdtPnttm(null);
            }

            BoardVectorVO vectorVO = addVector(cleanedBoardVO);

            GetRequest getRequest = new GetRequest.Builder()
                    .index(vectorIndexName)
                    .id(String.valueOf(nttId))
                    .build();

            GetResponse<BoardVectorVO> getResponse = client.get(getRequest, BoardVectorVO.class);

            if (getResponse.found()) {
                // Update existing document
                UpdateRequest<BoardVectorVO, BoardVectorVO> updateRequest = new UpdateRequest.Builder<BoardVectorVO, BoardVectorVO>()
                        .index(vectorIndexName)
                        .id(String.valueOf(nttId))
                        .doc(vectorVO)
                        .build();

                client.update(updateRequest, BoardVectorVO.class);
                log.info("Updated document in vector index for nttId: {}", nttId);
            } else {
                // Insert new document
                IndexRequest<BoardVectorVO> indexRequest = new IndexRequest.Builder<BoardVectorVO>()
                        .index(vectorIndexName)
                        .id(String.valueOf(nttId))
                        .document(vectorVO)
                        .build();

                client.index(indexRequest);
                log.info("Inserted new document in vector index for nttId: {}", nttId);
            }
        } catch (IOException e) {
            log.error("Error in vector operation for nttId: {}", nttId);
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    private BoardVectorVO addVector(BoardVO boardVO) {
        EmbeddingModel model = new OnnxEmbeddingModel(modelPath, tokenizerPath, PoolingMode.MEAN);
        String combinedText = boardVO.getNttSj() + " " + boardVO.getNttCn();

        Embedding articleResponse = model.embed(StrUtil.cleanString(combinedText)).content();
        float[] bbsArticleVector = articleResponse.vector();

        BoardVectorVO boardVectorVO = new BoardVectorVO();
        BeanUtils.copyProperties(boardVO, boardVectorVO); // 기존 필드 복사
        boardVectorVO.setBbsArticleVector(bbsArticleVector);

        return boardVectorVO;
    }

}
