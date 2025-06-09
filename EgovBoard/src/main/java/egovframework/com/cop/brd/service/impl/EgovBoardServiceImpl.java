package egovframework.com.cop.brd.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.com.cop.brd.entity.*;
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
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
    private final JPAQueryFactory queryFactory;

    @Value("${opensearch.synclog.enabled}")
    private boolean syncLogEnabled;

    public EgovBoardServiceImpl(
            EgovBoardRepository repository,
            EgovBbsSyncLogRepository bbsSyncLogRepository,
            EgovCommentRepository egovCommentRepository,
            EgovFileUtility egovFileUtility,
            EgovFileService egovFileService,
            @Qualifier("egovBoardIdGnrService") EgovIdGnrService boardIdGnrService,
            @Qualifier("egovBbsSyncLogIdGnrService") EgovIdGnrService bbsSyncLogIdGnrService,
            StreamBridge streamBridge,
            JPAQueryFactory queryFactory
    ) {
        this.repository = repository;
        this.bbsSyncLogRepository = bbsSyncLogRepository;
        this.egovCommentRepository = egovCommentRepository;
        this.egovFileUtility = egovFileUtility;
        this.egovFileService = egovFileService;
        this.boardIdGnrService = boardIdGnrService;
        this.bbsSyncLogIdGnrService = bbsSyncLogIdGnrService;
        this.streamBridge = streamBridge;
        this.queryFactory = queryFactory;
    }

    @Override
    public List<BoardDTO> noticeList(BbsVO bbsVO) {

        QBbs bbs = QBbs.bbs;
        QUserMaster userMaster = QUserMaster.userMaster;
        QBbsMaster bbsMaster = QBbsMaster.bbsMaster;
        QComment comment = QComment.comment;

        List<Tuple> results = boardListQuery(bbsVO,"notice").fetch();

        List<BoardDTO> content = results.stream().map(tuple -> {

            Bbs b = tuple.get(bbs);
            UserMaster user =tuple.get(userMaster);
            BbsMaster bm = tuple.get(bbsMaster);
            Comment c = tuple.get(comment);

            String userNm = (user != null && user.getUserNm() != null) ? user.getUserNm() : b.getNtcrNm();
            String bbsNm = bm != null && bm.getBbsNm() != null ? bm.getBbsNm() : "";

            return new BoardDTO(
                    Objects.requireNonNull(b).getBbsId().getNttId(),
                    b.getBbsId().getBbsId(),
                    b.getNttNo(),
                    b.getNttSj(),
                    b.getNttCn(),
                    b.getFrstRegisterId(),
                    userNm,
                    b.getFrstRegistPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    b.getRdcnt(),
                    b.getParntscttNo(),
                    b.getAnswerAt(),
                    b.getAnswerLc(),
                    b.getSortOrdr(),
                    b.getUseAt(),
                    b.getAtchFileId(),
                    b.getNtceBgnde(),
                    b.getNtceEndde(),
                    b.getNtcrId(),
                    b.getNtcrNm(),
                    b.getSjBoldAt(),
                    b.getNoticeAt(),
                    b.getSecretAt(),
                    0,
                    bbsNm
            );
        }).collect(Collectors.toList());
        return content;
    }

    @Override
    public Map<String, Object> list(BbsVO bbsVO) {
        Pageable pageable = PageRequest.of(bbsVO.getPageIndex() > 0 ? bbsVO.getFirstIndex() : 0, bbsVO.getPageUnit());
        String searchCondition = bbsVO.getSearchCondition();
        String searchKeyword = bbsVO.getSearchKeyword();

        QBbs bbs = QBbs.bbs;
        QUserMaster userMaster = QUserMaster.userMaster;
        QBbsMaster bbsMaster = QBbsMaster.bbsMaster;
        QComment comment = QComment.comment;

        BooleanBuilder where = new BooleanBuilder();
        if ("1".equals(searchCondition) && searchKeyword != null && !searchKeyword.isEmpty()) {
            where.and(bbs.nttSj.contains(searchKeyword));
        } else if ("2".equals(searchCondition) && searchKeyword != null && !searchKeyword.isEmpty()) {
            where.and(bbs.nttCn.contains(searchKeyword));
        } else if ("3".equals(searchCondition) && searchKeyword != null && !searchKeyword.isEmpty()) {
            where.and(userMaster.userNm.eq(searchKeyword));
        }
        where.and(bbs.useAt.eq("Y")).and(bbs.noticeAt.isNull());

        JPAQuery<Tuple> query = boardListQuery(bbsVO,"list");

        List<Tuple> results = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

        long total = Optional.ofNullable(
                queryFactory
                        .select(bbs.count()).
                        from(bbs)
                        .leftJoin(userMaster)
                        .on(bbs.frstRegisterId.eq(userMaster.esntlId))
                        .leftJoin(bbsMaster)
                        .on(bbs.bbsId.bbsId.eq(bbsMaster.bbsId))
                        .leftJoin(comment)
                        .on(bbs.bbsId.nttId.eq(comment.commentId.nttId)
                                .and(bbs.bbsId.bbsId.eq(comment.commentId.bbsId)))
                        .where(bbs.bbsId.bbsId.eq(bbsVO.getBbsId())
                                .and(bbs.useAt.eq("Y")).and(bbs.noticeAt.isNull())
                                .and(where))
                        .fetchOne()
        ).orElse(0L);

        List<BoardDTO> content = results.stream().map(tuple -> {

            Bbs b = tuple.get(bbs);
            UserMaster user =tuple.get(userMaster);
            BbsMaster bm = tuple.get(bbsMaster);
            Comment c = tuple.get(comment);

            String userNm = (user != null && user.getUserNm() != null) ? user.getUserNm() : b.getNtcrNm();
            String bbsNm = bm != null && bm.getBbsNm() != null ? bm.getBbsNm() : "";

            return new BoardDTO(
                    Objects.requireNonNull(b).getBbsId().getNttId(),
                    b.getBbsId().getBbsId(),
                    b.getNttNo(),
                    b.getNttSj(),
                    b.getNttCn(),
                    b.getFrstRegisterId(),
                    userNm,
                    b.getFrstRegistPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    b.getRdcnt(),
                    b.getParntscttNo(),
                    b.getAnswerAt(),
                    b.getAnswerLc(),
                    b.getSortOrdr(),
                    b.getUseAt(),
                    b.getAtchFileId(),
                    b.getNtceBgnde(),
                    b.getNtceEndde(),
                    b.getNtcrId(),
                    b.getNtcrNm(),
                    b.getSjBoldAt(),
                    b.getNoticeAt(),
                    b.getSecretAt(),
                    0,
                    bbsNm
            );
        }).collect(Collectors.toList());

        Page<BoardDTO> list = new PageImpl<>(content,pageable,total);

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

        QBbs bbs = QBbs.bbs;

        NumberTemplate<Integer> percentageExpr =Expressions.numberTemplate(Integer.class,
                "COALESCE(MAX({0}), 0) + 1",
                bbs.rdcnt);

        int count = Optional.ofNullable(
                queryFactory
                        .select(percentageExpr).
                        from(bbs).
                        where(bbs.bbsId.bbsId.eq(bbsVO.getBbsId())
                                .and(bbs.bbsId.nttId.eq(bbsVO.getNttId()))).fetchOne()
        ).orElse(0);

        bbsVO.setRdcnt(count);
        bbsVO.setLastUpdusrId(userInfo.get("uniqId"));
        bbsVO.setLastUpdtPnttm(String.valueOf(LocalDateTime.now()));

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDateTime formatdate = LocalDateTime.parse(bbsVO.getLastUpdtPnttm(), inputFormatter);
        String formatted = formatdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime date = LocalDateTime.parse(formatted, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        queryFactory.update(bbs)
                .set(bbs.rdcnt,bbsVO.getRdcnt())
                .set(bbs.lastUpdusrId,bbsVO.getLastUpdusrId())
                .set(bbs.lastUpdtPnttm, date)
                .where(bbs.bbsId.bbsId.eq(bbsVO.getBbsId()).and(bbs.bbsId.nttId.eq(bbsVO.getNttId())))
                .execute();

        return selectboardDetail(bbsVO);
    }

    @Transactional
    @Override
    public BbsVO insert(BbsVO bbsVO, List<MultipartFile> files, Map<String, String> userInfo) throws IOException, FdlException {

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
                BoardDTO boardDTO = selectboardDetail(bbsVO);

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

            syncLogProcess(bbsVO);

        return bbsVO;
    }

    @Override
    public BbsVO update(BbsVO bbsVO, List<MultipartFile> files, Map<String, String> userInfo) throws IOException, FdlException {

            if (!files.isEmpty()) {
                List<FileVO> filsVOList = egovFileUtility.parseFile(files);
                for (int i = 0; i < filsVOList.size(); i++) {
                    filsVOList.get(i).setAtchFileId(bbsVO.getAtchFileId());
                }
                egovFileService.insertFiles(filsVOList);
            }

//            BoardDTO dto = repository.selectBbsDetail(bbsVO.getBbsId(), bbsVO.getNttId());
            BoardDTO dto = selectboardDetail(bbsVO);
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

            syncLogProcess(bbsVO);
        return bbsVO;
    }

    @Override
    public BbsVO delete(BbsVO bbsVO) {
        BbsId bbsId = new BbsId();
        bbsId.setBbsId(bbsVO.getBbsId());
        bbsId.setNttId(bbsVO.getNttId());

        // 메인(최상위)게시글인 경우
        if ("N".equals(bbsVO.getNoticeAt())) {
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

        syncLogProcess(bbsVO);

        return bbsVO;
    }

    private void parntsItem(String bbsId, long nttId) {
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

    private void syncLogProcess(BbsVO bbsVO) {
        if (syncLogEnabled) {
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
    }

    private JPAQuery<Tuple> boardListQuery(BbsVO bbsVO, String listNm){

        String searchCondition = bbsVO.getSearchCondition();
        String searchKeyword = bbsVO.getSearchKeyword();

        QBbs bbs = QBbs.bbs;
        QUserMaster userMaster = QUserMaster.userMaster;
        QBbsMaster bbsMaster = QBbsMaster.bbsMaster;
        QComment comment = QComment.comment;

        BooleanBuilder where = new BooleanBuilder();
        if ("1".equals(searchCondition) && searchKeyword != null && !searchKeyword.isEmpty()) {
            where.and(bbs.nttSj.contains(searchKeyword));
        } else if ("2".equals(searchCondition) && searchKeyword != null && !searchKeyword.isEmpty()) {
            where.and(bbs.nttCn.contains(searchKeyword));
        } else if ("3".equals(searchCondition) && searchKeyword != null && !searchKeyword.isEmpty()) {
            where.and(userMaster.userNm.eq(searchKeyword));
        }

        if(listNm.equals("notice")){
            where.and(bbs.useAt.eq("Y")).and(bbs.noticeAt.eq("Y"));
        }else{
            where.and(bbs.useAt.eq("Y")).and(bbs.noticeAt.isNull());
        }

        return queryFactory
                .select(bbs, userMaster, bbsMaster, comment)
                .from(bbs)
                .leftJoin(userMaster)
                .on(bbs.frstRegisterId.eq(userMaster.esntlId))
                .leftJoin(bbsMaster)
                .on(bbs.bbsId.bbsId.eq(bbsMaster.bbsId))
                .leftJoin(comment)
                .on(bbs.bbsId.nttId.eq(comment.commentId.nttId)
                        .and(bbs.bbsId.bbsId.eq(comment.commentId.bbsId)))
                .where(bbs.bbsId.bbsId.eq(bbsVO.getBbsId())
                        .and(where))
                .orderBy(bbs.sortOrdr.desc(), bbs.parntscttNo.asc(), bbs.answerLc.asc(), bbs.nttNo.asc(), bbs.frstRegistPnttm.desc());
    }

    private BoardDTO selectboardDetail(BbsVO bbsVO){

        QBbs bbs = QBbs.bbs;
        QUserMaster userMaster = QUserMaster.userMaster;
        QBbsMaster bbsMaster = QBbsMaster.bbsMaster;

        Tuple tuple = queryFactory
                .select(bbs,userMaster,bbsMaster)
                .from(bbs)
                .leftJoin(userMaster)
                .on(bbs.frstRegisterId.eq(userMaster.esntlId))
                .leftJoin(bbsMaster)
                .on(bbs.bbsId.bbsId.eq(bbsMaster.bbsId))
                .where(bbs.bbsId.bbsId.eq(bbsVO.getBbsId())
                        .and(bbs.bbsId.nttId.eq(bbsVO.getNttId()))
                        .and(bbs.useAt.eq("Y")))
                .fetchOne();

        if (tuple == null) return null;

        Bbs b = tuple.get(bbs);
        UserMaster user =tuple.get(userMaster);
        BbsMaster bm = tuple.get(bbsMaster);

        String userNm = (user != null && user.getUserNm() != null) ? user.getUserNm() : b.getNtcrNm();
        String bbsNm = bm != null && bm.getBbsNm() != null ? bm.getBbsNm() : "";

        return new BoardDTO(
                Objects.requireNonNull(b).getBbsId().getNttId(),
                b.getBbsId().getBbsId(),
                b.getNttNo(),
                b.getNttSj(),
                b.getNttCn(),
                b.getFrstRegisterId(),
                userNm,
                b.getFrstRegistPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                b.getRdcnt(),
                b.getParntscttNo(),
                b.getAnswerAt(),
                b.getAnswerLc(),
                b.getSortOrdr(),
                b.getUseAt(),
                b.getAtchFileId(),
                b.getNtceBgnde(),
                b.getNtceEndde(),
                b.getNtcrId(),
                b.getNtcrNm(),
                b.getSjBoldAt(),
                b.getNoticeAt(),
                b.getSecretAt(),
                0,
                bbsNm
        );
    }

}
