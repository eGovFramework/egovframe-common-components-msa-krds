package egovframework.com.ext.ops.service.impl;

import com.google.gson.Gson;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.OnnxEmbeddingModel;
import dev.langchain4j.model.embedding.onnx.PoolingMode;
import egovframework.com.config.EgovSearchConfig;
import egovframework.com.ext.ops.entity.BbsSyncLog;
import egovframework.com.ext.ops.repository.EgovBbsRepository;
import egovframework.com.ext.ops.repository.EgovBbsSyncLogRepository;
import egovframework.com.ext.ops.service.BbsDTO;
import egovframework.com.ext.ops.service.BoardVO;
import egovframework.com.ext.ops.service.EgovOpenSearchManageService;
import egovframework.com.ext.ops.service.EgovOpenSearchService;
import egovframework.com.ext.ops.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.OpenSearchException;
import org.opensearch.client.opensearch._types.analysis.*;
import org.opensearch.client.opensearch._types.mapping.TypeMapping;
import org.opensearch.client.opensearch.core.BulkRequest;
import org.opensearch.client.opensearch.core.BulkResponse;
import org.opensearch.client.opensearch.indices.CreateIndexRequest;
import org.opensearch.client.opensearch.indices.CreateIndexResponse;
import org.opensearch.client.opensearch.indices.DeleteIndexRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service("opsEgovOpenSearchManageService")
@RequiredArgsConstructor
@Slf4j
public class EgovOpenSearchManageServiceImpl extends EgovAbstractServiceImpl implements EgovOpenSearchManageService, InitializingBean {

    @Value("${opensearch.text.indexname}")
    private String textIndexName;

    @Value("${opensearch.vector.indexname}")
    private String vectorIndexName;

    @Value("${index.batch.size}")
    private int batchSize;

    @Value("${app.search-config-path}")
    private String configPath;

    private String modelPath;
    private String tokenizerPath;
    private String stopTagsPath;
    private String synonymsPath;
    private String dictionaryRulesPath;
    private EmbeddingModel embeddingModel;

    private void loadConfig() {
        try {
            String jsonStr = new String(Files.readAllBytes(Paths.get(configPath)));
            EgovSearchConfig config = new Gson().fromJson(jsonStr, EgovSearchConfig.class);

            this.modelPath = config.getModelPath();
            this.tokenizerPath = config.getTokenizerPath();
            this.stopTagsPath = config.getStopTagsPath();
            this.synonymsPath = config.getSynonymsPath();
            this.dictionaryRulesPath = config.getDictionaryRulesPath();

        } catch (IOException e) {
            log.error("Failed to load search config: " + e.getMessage());
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    @Override
    public void afterPropertiesSet() {
        loadConfig();
        this.embeddingModel = new OnnxEmbeddingModel(modelPath, tokenizerPath, PoolingMode.MEAN);
    }

    private final OpenSearchClient client;

    private final EgovBbsSyncLogRepository egovBbsSyncLogRepository;
    private final EgovBbsRepository egovBbsRepository;
    private final EgovOpenSearchService egovOpenSearchService;

    private Map<String, CharFilter> createCharFilters() {
        Map<String, CharFilter> charFilterMap = new HashMap<>();

        // 줄바꿈 및 \를 공백으로 대체
        PatternReplaceCharFilter patternCharFilter = new PatternReplaceCharFilter.Builder().pattern("[\\r\\n\\\\]").replacement(" ").flags("CASE_INSENSITIVE|MULTILINE").build();
        charFilterMap.put("patternfilter", new CharFilter.Builder().definition(patternCharFilter._toCharFilterDefinition()).build());

        // remove punctuation chars : 구두점을 제거한다
        PatternReplaceCharFilter punctuationCharFilter = new PatternReplaceCharFilter.Builder().pattern("\\p{Punct}").replacement("").flags("CASE_INSENSITIVE|MULTILINE").build();
        charFilterMap.put("punctuationCharFilter", new CharFilter.Builder().definition(punctuationCharFilter._toCharFilterDefinition()).build());

        return charFilterMap;
    }

    private Map<String, TokenFilter> createTokenFilters() {
        Map<String, TokenFilter> tokenFilterMap = new HashMap<>();

        // 제거할 품사의 종류를 열거한다. 코드가 의미하는 품사는 다음 페이지를 참조한다
        // https://esbook.kimjmin.net/06-text-analysis/6.7-stemming/6.7.2-nori
        List<String> stopTags = StrUtil.readWordsFromFile(stopTagsPath);

        // 동의어로 처리될 단어를 열거한다.
        List<String> synonym = StrUtil.readWordsFromFile(synonymsPath);

        tokenFilterMap.put("lowercase", new TokenFilter.Builder().definition(new LowercaseTokenFilter.Builder().build()._toTokenFilterDefinition()).build());
        tokenFilterMap.put("asciifolding", new TokenFilter.Builder().definition(new AsciiFoldingTokenFilter.Builder().preserveOriginal(false).build()._toTokenFilterDefinition()).build());
        tokenFilterMap.put("nori_part_of_speech", new TokenFilter.Builder().definition(new NoriPartOfSpeechTokenFilter.Builder().stoptags(stopTags).build()._toTokenFilterDefinition()).build());
        tokenFilterMap.put("synonym_graph", new TokenFilter.Builder().definition(new SynonymGraphTokenFilter.Builder().synonyms(synonym).expand(true).build()._toTokenFilterDefinition()).build());

        return tokenFilterMap;
    }

    private Map<String, Tokenizer> createTokenizers() {
        // 분할처리하면 안되는 단어를 열거한다.
        List<String> userDictionaryRules = StrUtil.readWordsFromFile(dictionaryRulesPath);

        // 한글형태소분석기인 Nori 플러그인이 미리 설치되어 있어야 함
        NoriTokenizer noriTokenizer = new NoriTokenizer.Builder()
                .decompoundMode(NoriDecompoundMode.Discard)
                .discardPunctuation(true)
                .userDictionaryRules(userDictionaryRules)
                .build();

        Map<String, Tokenizer> tokenizerMap = new HashMap<>();

        tokenizerMap.put("nori-tokenizer", new Tokenizer.Builder().definition(noriTokenizer._toTokenizerDefinition()).build());

        return tokenizerMap;
    }

    private Map<String, Analyzer> createAnalyzers(List<String> charFilterList, List<String> tokenFilterList) {
        // 커스텀 Analyzer 구성 : char_filter ==> tokenizer ==> token filter
        CustomAnalyzer noriAnalyzer = new CustomAnalyzer.Builder()
                .charFilter(charFilterList)
                .tokenizer("nori-tokenizer")
                .filter(tokenFilterList).build();

        Map<String, Analyzer> analyzerMap = new HashMap<>();

        analyzerMap.put("nori-analyzer", new Analyzer.Builder().custom(noriAnalyzer).build());

        return analyzerMap;
    }

    private void addMappings(CreateIndexRequest.Builder builder, boolean includeVector) {
        builder.mappings(mapping -> {
            TypeMapping.Builder mappingBuilder = mapping
                    .properties("nttId",
                            p -> p.integer(f -> f.index(true)
                                    .fields("keyword", k -> k.keyword(kw -> kw.ignoreAbove(256)))))
                    .properties("bbsId",
                            p -> p.text(f -> f.index(true)
                                    .fields("keyword", k -> k.keyword(kw -> kw.ignoreAbove(256)))))
                    .properties("bbsNm",
                            p -> p.text(f -> f.index(true).analyzer("nori-analyzer")))
                    .properties("nttNo",
                            p -> p.integer(f -> f.index(true)))
                    .properties("nttSj",
                            p -> p.text(f -> f.index(true).analyzer("nori-analyzer")))
                    .properties("nttCn",
                            p -> p.text(f -> f.index(true).analyzer("nori-analyzer")))
                    .properties("answerAt",
                            p -> p.text(f -> f.index(true).analyzer("nori-analyzer")))
                    .properties("parntscttNo",
                            p -> p.integer(f -> f.index(true)))
                    .properties("answerLc",
                            p -> p.integer(f -> f.index(true)))
                    .properties("sortOrdr",
                            p -> p.integer(f -> f.index(true)))
                    .properties("useAt",
                            p -> p.text(f -> f.index(true).analyzer("nori-analyzer")))
                    .properties("ntceBgnde",
                            p -> p.date(f -> f.index(true)))
                    .properties("ntceEndde",
                            p -> p.date(f -> f.index(true)))
                    .properties("ntcrId",
                            p -> p.text(f -> f.index(true).analyzer("nori-analyzer")))
                    .properties("ntcrNm",
                            p -> p.text(f -> f.index(true).analyzer("nori-analyzer")))
                    .properties("atchFileId",
                            p -> p.text(f -> f.index(true).analyzer("nori-analyzer")))
                    .properties("noticeAt",
                            p -> p.text(f -> f.index(true).analyzer("nori-analyzer")))
                    .properties("sjBoldAt",
                            p -> p.text(f -> f.index(true).analyzer("nori-analyzer")))
                    .properties("secretAt",
                            p -> p.text(f -> f.index(true).analyzer("nori-analyzer")))
                    .properties("frstRegistPnttm",
                            p -> p.date(f -> f.index(true)))
                    .properties("lastUpdtPnttm",
                            p -> p.date(f -> f.index(true)))
                    .properties("frstRegisterId",
                            p -> p.text(f -> f.index(true).analyzer("nori-analyzer")));

            if (includeVector) {
                mappingBuilder = mappingBuilder.properties("bbsArticleVector",
                        p -> p.knnVector(k -> k.dimension(768)));
            }

            return mappingBuilder;
        });
    }

    private void createIndexInternal(String indexName, boolean enableKnn) throws IOException {
        Map<String, CharFilter> charFilterMap = createCharFilters();
        Map<String, TokenFilter> tokenFilterMap = createTokenFilters();
        Map<String, Tokenizer> tokenizerMap = createTokenizers();

        List<String> charFilterList = Arrays.asList("patternfilter", "punctuationCharFilter");
        // nori_number : 한국어 숫자의 검색을 가능하게 함
        // nori_part_of_speech : 한자의 한국어 검색을 가능하게 함
        List<String> tokenFilterList = Arrays.asList(
                "lowercase", "asciifolding", "synonym_graph",
                "nori_number", "nori_readingform", "nori_part_of_speech"
        );

        Map<String, Analyzer> analyzerMap = createAnalyzers(charFilterList, tokenFilterList);

        CreateIndexRequest.Builder requestBuilder = new CreateIndexRequest.Builder()
                .index(indexName)
                .settings(s -> {
                    if (enableKnn) {
                        s.knn(true);
                    }
                    return s.analysis(a -> a
                            .charFilter(charFilterMap)
                            .tokenizer(tokenizerMap)
                            .filter(tokenFilterMap)
                            .analyzer(analyzerMap));
                });

        // 각 필드를 추가
        addMappings(requestBuilder, enableKnn);

        try {
            CreateIndexResponse createIndexResponse = client.indices().create(requestBuilder.build());
            log.debug(String.format("Index %s.", createIndexResponse.index().toString().toLowerCase()));
        } catch (OpenSearchException ex) {
            final String errorType = Objects.requireNonNull(ex.response().error().type());
            if (!errorType.equals("resource_already_exists_exception")) {
                throw ex;
            }
        }
    }

    @Override
    public void createTextIndex() throws IOException {
        createIndexInternal(textIndexName, false);
    }

    @Override
    public void createVectorIndex() throws IOException {
        createIndexInternal(vectorIndexName, true);
    }

    @Override
    public void insertTotalData() {
        processIndexing(textIndexName, false);
    }

    @Override
    public void insertTotalVectorData() {
        processIndexing(vectorIndexName, true);
    }

    private void processIndexing(String indexName, boolean withVector) {
        long startTime = System.currentTimeMillis();

        // 전체 데이터 수와 페이지 수 계산
        long totalCount = egovBbsRepository.countAllArticles();
        int totalPages = (int) Math.ceil((double) totalCount / batchSize);

        log.info("Starting indexing process. Total records: {}, Batch size: {}, Total pages: {}",
                totalCount, batchSize, totalPages);

        for (int page = 0; page < totalPages; page++) {
            long pageStartTime = System.currentTimeMillis();

            try {
                // 페이지별로 데이터 조회
                Pageable pageable = PageRequest.of(page, batchSize, Sort.by("bbsId.nttId").descending());
                Page<BbsDTO> pageResult = egovBbsRepository.findAllArticlesWithPaging(pageable);

                if (!pageResult.isEmpty()) {
                    processBatchRequest(pageResult.getContent(), withVector, indexName, page, totalPages);
                }

                logPageProgress(page, totalPages, pageStartTime);
            } catch (Exception e) {
                log.error("Error processing page {}: {}", page, e.getMessage(), e);
            }
        }

        logTotalExecutionTime(startTime);
    }

    private void processBatchRequest(List<BbsDTO> batchData, boolean withVector, String indexName, int currentPage,
                                     int totalPages) {
        BulkRequest.Builder bulkRequestBuilder = new BulkRequest.Builder();

        batchData.forEach(bbsArticleInfo -> {
            try {
                Map<String, Object> dataMap = convertToMap(bbsArticleInfo, withVector);
                bulkRequestBuilder.operations(ops -> ops
                        .index(idx -> idx.index(indexName).id(String.valueOf(dataMap.get("nttId"))).document(dataMap)));
            } catch (Exception e) {
                log.error("Error processing document {}: {}", bbsArticleInfo.getNttId(), e.getMessage());
            }
        });

        executeBulkRequest(bulkRequestBuilder, currentPage, totalPages);
    }

    private Map<String, Object> convertToMap(BbsDTO bbsArticleInfo, boolean withVector) {
        Map<String, Object> dataMap = new HashMap<>();

        // 기본 필드 매핑
        dataMap.put("nttId", bbsArticleInfo.getNttId());
        dataMap.put("bbsId", bbsArticleInfo.getBbsId());
        dataMap.put("bbsNm", bbsArticleInfo.getBbsNm());
        dataMap.put("nttNo", bbsArticleInfo.getNttNo());
        dataMap.put("nttSj", StrUtil.cleanString(bbsArticleInfo.getNttSj()));
        dataMap.put("nttCn", StrUtil.cleanString(bbsArticleInfo.getNttCn()));
        dataMap.put("answerAt", bbsArticleInfo.getAnswerAt());
        dataMap.put("parntscttNo", bbsArticleInfo.getParntscttNo());
        dataMap.put("answerLc", bbsArticleInfo.getAnswerLc());
        dataMap.put("sortOrdr", bbsArticleInfo.getSortOrdr());
        dataMap.put("useAt", bbsArticleInfo.getUseAt());
        dataMap.put("ntceBgnde", bbsArticleInfo.getNtceBgnde());
        dataMap.put("ntceEndde", bbsArticleInfo.getNtceEndde());
        dataMap.put("ntcrId", bbsArticleInfo.getNtcrId());
        dataMap.put("ntcrNm", bbsArticleInfo.getNtcrNm());
        dataMap.put("atchFileId", bbsArticleInfo.getAtchFileId());
        dataMap.put("noticeAt", bbsArticleInfo.getNoticeAt());
        dataMap.put("sjBoldAt", bbsArticleInfo.getSjBoldAt());
        dataMap.put("secretAt", bbsArticleInfo.getSecretAt());
        dataMap.put("frstRegistPnttm", bbsArticleInfo.getFrstRegistPnttm());
        
        String lastUpdtPnttm = bbsArticleInfo.getLastUpdtPnttm();
        if (lastUpdtPnttm != null && !lastUpdtPnttm.trim().isEmpty()) {
            dataMap.put("lastUpdtPnttm", lastUpdtPnttm);
        } else {
            dataMap.put("lastUpdtPnttm", null);
        }

        // 임베딩이 필요한 경우
        if (withVector) {
            String combinedText = StrUtil.cleanString(bbsArticleInfo.getNttSj() + " " + bbsArticleInfo.getNttCn());
            Embedding bbsArticleResponse = embeddingModel.embed(combinedText).content();
            dataMap.put("bbsArticleVector", bbsArticleResponse.vector());
        }

        return dataMap;
    }

    private void executeBulkRequest(BulkRequest.Builder builder, int currentPage, int totalPages) {
        try {
            BulkResponse bulkResponse = client.bulk(builder.build());
            if (bulkResponse.errors()) {
                bulkResponse.items().forEach(item -> {
                    if (item.error() != null) {
                        log.error("Error indexing document with ID: {} , Error: {}",
                                item.id(), item.error().reason());
                    }
                });
            } else {
                log.debug("Batch {}/{} completed successfully", currentPage + 1, totalPages);
            }
        } catch (Exception e) {
            log.error("Error executing bulk request for page {}: {}", currentPage + 1, e.getMessage());
        }
    }

    private void logPageProgress(int currentPage, int totalPages, long startTime) {
        long executionTime = (System.currentTimeMillis() - startTime) / 1000;
        log.info("Processed page {}/{} in {} seconds",
                currentPage + 1, totalPages, executionTime);
    }

    private void logTotalExecutionTime(long startTime) {
        long totalTime = (System.currentTimeMillis() - startTime) / 1000;
        log.info("Total indexing process completed in {} seconds", totalTime);
    }

    @Override
    public void deleteIndex(String indexName) throws IOException {
        DeleteIndexRequest deleteRequest = new DeleteIndexRequest.Builder().index(indexName).build();
        client.indices().delete(deleteRequest);
        log.debug(String.format("Index %s.", deleteRequest.index().toString().toLowerCase()));
    }

    @Override
    public void reprocessFailedSync(String syncSttusCode) {
        List<BbsSyncLog> syncLogs = egovBbsSyncLogRepository.findBySyncSttusCode(syncSttusCode);

        if (syncLogs.isEmpty()) {
            log.info("No items found with status '{}' to process", syncSttusCode);
            return;
        }

        log.info("Found {} items with status '{}' to process", syncLogs.size(), syncSttusCode);

        // nttId별로 그룹화
        Map<Long, List<BbsSyncLog>> syncLogsByNttId = syncLogs.stream()
                .collect(Collectors.groupingBy(BbsSyncLog::getNttId));

        log.info("Processing {} unique nttIds", syncLogsByNttId.size());

        // 각 nttId별로 처리
        for (Map.Entry<Long, List<BbsSyncLog>> entry : syncLogsByNttId.entrySet()) {
            Long nttId = entry.getKey();
            List<BbsSyncLog> logsForNttId = entry.getValue();

            try {
                // 게시글 데이터 조회
                Optional<BoardVO> boardVOOptional = egovBbsRepository.findBBSDTOByNttId(nttId)
                        .map(dto -> {
                            BoardVO vo = new BoardVO();
                            BeanUtils.copyProperties(dto, vo);
                            return vo;
                        });

                if (boardVOOptional.isPresent()) {
                    // OpenSearch 처리 (한 번만 처리)
                    egovOpenSearchService.processOpenSearchOperations(nttId, boardVOOptional.get());

                    // 밀리초를 제거한 현재 시간 설정 (for문 밖에서 한 번만 생성)
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.MILLISECOND, 0);
                    Date dateWithoutMillis = calendar.getTime();

                    // 모든 로그 상태 업데이트
                    for (BbsSyncLog log : logsForNttId) {
                        log.setSyncSttusCode("C");  // Completed
                        log.setSyncPnttm(dateWithoutMillis);
                        egovBbsSyncLogRepository.save(log);
                    }

                    log.info("Successfully processed nttId: {} and updated {} logs", nttId, logsForNttId.size());
                } else {
                    // 게시글을 찾을 수 없는 경우, 모든 로그를 실패로 표시
                    // 밀리초를 제거한 현재 시간 설정 (for문 밖에서 한 번만 생성)
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.MILLISECOND, 0);
                    Date dateWithoutMillis = calendar.getTime();

                    for (BbsSyncLog log : logsForNttId) {
                        log.setSyncSttusCode("F");  // Failed
                        log.setErrorPnttm(dateWithoutMillis);
                        egovBbsSyncLogRepository.save(log);
                    }

                    log.error("Board not found with id: {}, marked {} logs as failed", nttId, logsForNttId.size());
                }
            } catch (Exception e) {
                // 오류 발생 시 모든 로그를 실패로 표시
                log.error("Failed to process nttId: {}", nttId, e);

                // 밀리초를 제거한 현재 시간 설정 (for문 밖에서 한 번만 생성)
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MILLISECOND, 0);
                Date dateWithoutMillis = calendar.getTime();

                for (BbsSyncLog log : logsForNttId) {
                    log.setSyncSttusCode("F");  // Failed
                    log.setErrorPnttm(dateWithoutMillis);
                    egovBbsSyncLogRepository.save(log);
                }
            }
        }

        log.info("Completed processing {} unique nttIds with status '{}'", syncLogsByNttId.size(), syncSttusCode);
    }

}
