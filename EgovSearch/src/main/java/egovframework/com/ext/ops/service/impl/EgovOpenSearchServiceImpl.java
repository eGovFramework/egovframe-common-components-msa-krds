package egovframework.com.ext.ops.service.impl;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.OnnxEmbeddingModel;
import dev.langchain4j.model.embedding.onnx.PoolingMode;
import egovframework.com.config.ConfigUtils;
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

    private EmbeddingModel embeddingModel;
    private final OpenSearchClient client;
    
    private final ConfigUtils configUtils;

    @Override
    public void afterPropertiesSet() {
    	EgovSearchConfig config = configUtils.loadConfig();
    	if (config != null) {
            String modelPath = config.getModelPath();
            String tokenizerPath = config.getTokenizerPath();
            this.embeddingModel = new OnnxEmbeddingModel(modelPath, tokenizerPath, PoolingMode.MEAN);
        } 
    }

    @Override
    public void processOpenSearchOperations(Long nttId, BoardVO boardVO) {
        try {
            performOpenSearchTextOperation(nttId, boardVO);
            performOpenSearchVectorOperation(nttId, boardVO);
        } catch (Exception e) {
            log.warn("Error processing OpenSearch operations for nttId: {}", nttId, e);
        }
    }

    private void performOpenSearchTextOperation(Long nttId, BoardVO boardVO) {
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
            log.warn("Error in text operation for nttId: {}", nttId, e);
        }
    }

    private void performOpenSearchVectorOperation(Long nttId, BoardVO boardVO) {
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
            log.warn("Error in vector operation for nttId: {}", nttId);
        }
    }

    private BoardVectorVO addVector(BoardVO boardVO) {
        String combinedText = boardVO.getNttSj() + " " + boardVO.getNttCn();

        Embedding articleResponse = embeddingModel.embed(StrUtil.cleanString(combinedText)).content();
        float[] bbsArticleVector = articleResponse.vector();

        BoardVectorVO boardVectorVO = new BoardVectorVO();
        BeanUtils.copyProperties(boardVO, boardVectorVO); // 기존 필드 복사
        boardVectorVO.setBbsArticleVector(bbsArticleVector);

        return boardVectorVO;
    }

}
