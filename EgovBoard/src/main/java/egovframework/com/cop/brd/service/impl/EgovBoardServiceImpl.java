package egovframework.com.cop.brd.service.impl;

import egovframework.com.cop.brd.entity.Bbs;
import egovframework.com.cop.brd.entity.BbsId;
import egovframework.com.cop.brd.entity.BbsSyncLog;
import egovframework.com.cop.brd.entity.Comment;
import egovframework.com.cop.brd.event.BoardEvent;
import egovframework.com.cop.brd.event.BoardEventType;
import egovframework.com.cop.brd.repository.EgovBbsSyncLogRepository;
import egovframework.com.cop.brd.repository.EgovBoardRepository;
import egovframework.com.cop.brd.repository.EgovCommentRepository;
import egovframework.com.cop.brd.service.*;
import egovframework.com.cop.brd.util.EgovBoardUtility;
import egovframework.com.cop.brd.util.EgovFileUtility;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("brdEgovBoardService")
@Slf4j
public class EgovBoardServiceImpl extends EgovAbstractServiceImpl implements EgovBoardService {

    private final EgovBoardRepository repository;
    private final EgovBbsSyncLogRepository bbsSyncLogRepository;
    private final EgovCommentRepository egovCommentRepository;

    private final EgovFileUtility egovFileUtility;
    private final EgovFileService egovFileService;
    @Qualifier("egovBoardIdGnrService")
    private final EgovIdGnrService boardIdGnrService;
    @Qualifier("egovBbsSyncLogIdGnrService")
    private final EgovIdGnrService bbsSyncLogIdGnrService;
    private final StreamBridge streamBridge;

    @Value("${opensearch.synclog.enabled}")
    private boolean SYNCLOG_ENABLED;

    public EgovBoardServiceImpl(
            EgovBoardRepository repository,
            EgovBbsSyncLogRepository bbsSyncLogRepository,
            EgovCommentRepository egovCommentRepository,
            EgovFileUtility egovFileUtility,
            EgovFileService egovFileService,
            @Qualifier("egovBoardIdGnrService") EgovIdGnrService boardIdGnrService,
            @Qualifier("egovBbsSyncLogIdGnrService") EgovIdGnrService bbsSyncLogIdGnrService,
            StreamBridge streamBridge
    ) {
        this.repository = repository;
        this.bbsSyncLogRepository = bbsSyncLogRepository;
        this.egovCommentRepository = egovCommentRepository;
        this.egovFileUtility = egovFileUtility;
        this.egovFileService = egovFileService;
        this.boardIdGnrService = boardIdGnrService;
        this.bbsSyncLogIdGnrService = bbsSyncLogIdGnrService;
        this.streamBridge = streamBridge;
    }

    @Override
    public List<BoardDTO> noticeList(BbsVO bbsVO) {
        String searchCondition = bbsVO.getSearchCondition();
        String searchKeyword = bbsVO.getSearchKeyword();
        String bbsId = bbsVO.getBbsId();
        return repository.boardNoticeList(searchCondition, searchKeyword, bbsId);
    }

    @Override
    public Map<String, Object> list(BbsVO bbsVO) {
        Pageable pageable = PageRequest.of(bbsVO.getPageIndex() > 0 ? bbsVO.getFirstIndex() : 0, bbsVO.getPageUnit());

        String searchCondition = bbsVO.getSearchCondition();
        String searchKeyword = bbsVO.getSearchKeyword();
        String bbsId = bbsVO.getBbsId();
        Page<BoardDTO> list = repository.boardList(searchCondition, searchKeyword, bbsId, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("content", list.getContent());
        response.put("number", list.getNumber());
        response.put("size", list.getSize());
        response.put("totalElements", list.getTotalElements());
        response.put("totalPages", list.getTotalPages());

        return response;
    }

    @Transactional
    @Override
    public BoardDTO detail(BbsVO bbsVO, Map<String, String> userInfo) {
        bbsVO.setRdcnt(repository.selectRdcntMax(bbsVO.getBbsId(), bbsVO.getNttId()));
        bbsVO.setLastUpdusrId(userInfo.get("uniqId"));
        bbsVO.setLastUpdtPnttm(String.valueOf(LocalDateTime.now()));
        repository.updataRdcnt(bbsVO.getRdcnt(), bbsVO.getLastUpdusrId(), LocalDateTime.parse(bbsVO.getLastUpdtPnttm()), bbsVO.getBbsId(), bbsVO.getNttId());

        return repository.selectBbsDetail(bbsVO.getBbsId(), bbsVO.getNttId());
    }

    @Transactional
    @Override
    public BbsVO insert(BbsVO bbsVO, List<MultipartFile> files, Map<String, String> userInfo) throws Exception {
        String attachFileId = null;
        if (!files.isEmpty()) {
            List<FileVO> filsVOList = egovFileUtility.parseFile(files);
            attachFileId = egovFileService.insertFiles(filsVOList);
        }

        bbsVO.setAtchFileId(attachFileId);
        bbsVO.setFrstRegisterId(userInfo.get("uniqId"));
        bbsVO.setLastUpdusrId(userInfo.get("uniqId"));
        bbsVO.setUseAt("Y");

        /* 답글 처리 */
        if ("Y".equals(bbsVO.getAnswerAt())) {
            bbsVO.setParntscttNo(Math.toIntExact(bbsVO.getNttId()));
            BoardDTO boardDTO = repository.selectBbsDetail(bbsVO.getBbsId(), (long) bbsVO.getParntscttNo());

            long nttId = boardIdGnrService.getNextLongId();
            bbsVO.setNttNo(boardDTO.getNttNo() + 1);
            bbsVO.setAnswerLc(boardDTO.getAnswerLc() + 1);
            bbsVO.setRdcnt(0);
            bbsVO.setNttId(nttId);
            bbsVO.setSortOrdr(boardDTO.getSortOrdr());
        } else {
            bbsVO.setParntscttNo(0);
            bbsVO.setAnswerLc(0);
            bbsVO.setNttNo(1);
            bbsVO.setAnswerAt("N");
            bbsVO.setNttId(boardIdGnrService.getNextLongId());
            bbsVO.setSortOrdr(Math.toIntExact(bbsVO.getNttId()));
        }

        /* 익명글 처리 */
        if (!ObjectUtils.isEmpty(bbsVO.getAnonymousAt())) {
            bbsVO.setNtcrId("annoymous");
            bbsVO.setNtcrNm("익명");
            bbsVO.setFrstRegisterId("annoymous");
        } else {
            bbsVO.setNtcrId(userInfo.get("uniqId"));
            bbsVO.setNtcrNm(userInfo.get("userName"));
        }

        bbsVO = EgovBoardUtility.bbsEntityToVO(repository.save(EgovBoardUtility.bbsVOToEntity(bbsVO)));

        if (SYNCLOG_ENABLED) {
            try {
                // 검색엔진 연계용 데이터 저장
                BbsSyncLog syncLog = new BbsSyncLog();
                syncLog.setSyncId(bbsSyncLogIdGnrService.getNextStringId());
                syncLog.setNttId(bbsVO.getNttId());
                syncLog.setBbsId(bbsVO.getBbsId());
                syncLog.setSyncSttusCode("P"); // Pending
                syncLog.setRegistPnttm(LocalDateTime.now());
                bbsSyncLogRepository.save(syncLog);

                // 게시글 저장 후 이벤트 발행
                BoardEvent event = BoardEvent.builder()
                        .eventType(BoardEventType.CREATE)
                        .nttId(bbsVO.getNttId())
                        .bbsId(bbsVO.getBbsId())
                        .nttSj(bbsVO.getNttSj())
                        .nttCn(bbsVO.getNttCn())
                        .eventDateTime(LocalDateTime.now())
                        .build();

                streamBridge.send("searchProducer-out-0", event);
            } catch (Exception e) {
                log.warn("Failed to send event to RabbitMQ. Event will be processed later via COMTNBBSSYNCLOG: {}", e.getMessage());
            }
        }

        return bbsVO;
    }

    @Override
    public BbsVO update(BbsVO bbsVO, List<MultipartFile> files, Map<String, String> userInfo) throws Exception {
        if (!files.isEmpty()) {
            List<FileVO> filsVOList = egovFileUtility.parseFile(files);
            for (int i = 0; i < filsVOList.size(); i++) {
                filsVOList.get(i).setAtchFileId(bbsVO.getAtchFileId());
            }
            String atchFileId = egovFileService.insertFiles(filsVOList);
        }

        BoardDTO dto = repository.selectBbsDetail(bbsVO.getBbsId(), bbsVO.getNttId());
        dto.setNoticeAt(bbsVO.getNoticeAt());
        dto.setSecretAt(bbsVO.getSecretAt());
        dto.setSjBoldAt(bbsVO.getSjBoldAt());
        dto.setNttCn(bbsVO.getNttCn());
        dto.setNttSj(bbsVO.getNttSj());
        dto.setNtceBgnde(bbsVO.getNtceBgnde());
        dto.setNtceEndde(bbsVO.getNtceEndde());
        dto.setAtchFileId(bbsVO.getAtchFileId());
        BeanUtils.copyProperties(dto, bbsVO);

        bbsVO.setLastUpdtPnttm(String.valueOf(LocalDateTime.now()));
        bbsVO.setLastUpdusrId(userInfo.get("uniqId"));

        repository.save(EgovBoardUtility.bbsVOToEntity(bbsVO));

        bbsVO = EgovBoardUtility.bbsEntityToVO(repository.save(EgovBoardUtility.bbsVOToEntity(bbsVO)));

        if (SYNCLOG_ENABLED) {
            try {
                // 검색엔진 연계용 데이터 저장
                BbsSyncLog syncLog = new BbsSyncLog();
                syncLog.setSyncId(bbsSyncLogIdGnrService.getNextStringId());
                syncLog.setNttId(bbsVO.getNttId());
                syncLog.setBbsId(bbsVO.getBbsId());
                syncLog.setSyncSttusCode("P"); // Pending
                syncLog.setRegistPnttm(LocalDateTime.now());
                bbsSyncLogRepository.save(syncLog);

                // 게시글 저장 후 이벤트 발행
                BoardEvent event = BoardEvent.builder()
                        .eventType(BoardEventType.CREATE)
                        .nttId(bbsVO.getNttId())
                        .bbsId(bbsVO.getBbsId())
                        .nttSj(bbsVO.getNttSj())
                        .nttCn(bbsVO.getNttCn())
                        .eventDateTime(LocalDateTime.now())
                        .build();

                streamBridge.send("searchProducer-out-0", event);
            } catch (Exception e) {
                log.warn("Failed to send event to RabbitMQ. Event will be processed later via COMTNBBSSYNCLOG: {}", e.getMessage());
            }
        }

        return bbsVO;
    }

    @Override
    public BbsVO delete(BbsVO bbsVO) {
        BbsId bbsId = new BbsId();
        bbsId.setBbsId(bbsVO.getBbsId());
        bbsId.setNttId(bbsVO.getNttId());

        // 메인(최상위)게시글인 경우
        if (bbsVO.getNoticeAt() == "N") {
            List<Bbs> bbsList = repository.findAllByBbsIdAndSortOrdr(bbsId, (long) bbsVO.getSortOrdr());
            List<Comment> commentList = egovCommentRepository.findAllByCommentId_BbsIdAndCommentId_NttId(bbsVO.getBbsId(), bbsVO.getNttId());
            if (bbsList != null) {
                for (int i = 0; i < bbsList.size(); i++) {
                    bbsList.get(i).setUseAt("N");
                    repository.save(bbsList.get(i));
                }
            }

            if (commentList != null) {
                for (Comment comment : commentList) {
                    comment.setUseAt("N");
                    egovCommentRepository.save(comment);
                }
            }
        } else {  // 답글인 경우
            Bbs bbs = repository.findById(bbsId).get();
            List<Comment> commentList = egovCommentRepository.findAllByCommentId_BbsIdAndCommentId_NttId(bbsId.getBbsId(), bbsId.getNttId());
            bbs.setUseAt("N");
            for (Comment comment : commentList) {
                comment.setUseAt("N");
                egovCommentRepository.save(comment);
            }
            repository.save(bbs);
            parntsItem(bbs.getBbsId().getBbsId(), bbs.getBbsId().getNttId());
        }
        if (SYNCLOG_ENABLED) {
            try {
                // 검색엔진 연계용 데이터 저장
                BbsSyncLog syncLog = new BbsSyncLog();
                syncLog.setSyncId(bbsSyncLogIdGnrService.getNextStringId());
                syncLog.setNttId(bbsVO.getNttId());
                syncLog.setBbsId(bbsVO.getBbsId());
                syncLog.setSyncSttusCode("P"); // Pending
                syncLog.setRegistPnttm(LocalDateTime.now());
                bbsSyncLogRepository.save(syncLog);

                // 게시글 저장 후 이벤트 발행
                BoardEvent event = BoardEvent.builder()
                        .eventType(BoardEventType.CREATE)
                        .nttId(bbsVO.getNttId())
                        .bbsId(bbsVO.getBbsId())
                        .nttSj(bbsVO.getNttSj())
                        .nttCn(bbsVO.getNttCn())
                        .eventDateTime(LocalDateTime.now())
                        .build();

                streamBridge.send("searchProducer-out-0", event);
            } catch (Exception e) {
                log.warn("Failed to send event to RabbitMQ. Event will be processed later via COMTNBBSSYNCLOG: {}", e.getMessage());
            }
        }

        return bbsVO;
    }

    public void parntsItem(String bbsId, long nttId) {
        List<Bbs> rList = repository.findAllByParntscttNo((int) nttId);
        List<Comment> commentList = egovCommentRepository.findAllByCommentId_BbsIdAndCommentId_NttId(bbsId, nttId);
        if (rList.isEmpty()) {
            log.debug("더 이상 답글 없음");
        } else {
            for (int i = 0; i < rList.size(); i++) {
                rList.get(i).setUseAt("N");
                repository.save(rList.get(i));
                parntsItem(rList.get(i).getBbsId().getBbsId(), rList.get(i).getBbsId().getNttId());
            }

            for (Comment comment : commentList) {
                comment.setUseAt("N");
                egovCommentRepository.save(comment);
            }
        }
    }
}
