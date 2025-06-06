package egovframework.com.cop.bls.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.com.cop.bls.entity.*;
import egovframework.com.cop.bls.repository.EgovBbsMasterOptionRepository;
import egovframework.com.cop.bls.repository.EgovBbsMasterRepository;
import egovframework.com.cop.bls.service.BbsMasterDTO;
import egovframework.com.cop.bls.service.BbsMasterOptnVO;
import egovframework.com.cop.bls.service.BbsMasterVO;
import egovframework.com.cop.bls.service.EgovBbsMasterService;
import egovframework.com.cop.bls.util.EgovBlogUtility;
import org.apache.commons.lang3.RandomStringUtils;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("blsEgovBbsMasterService")
public class EgovBbsMasterServiceImpl extends EgovAbstractServiceImpl implements EgovBbsMasterService {

    private final EgovBbsMasterRepository repository;
    private final EgovBbsMasterOptionRepository optionRepository;
    private final EgovIdGnrService idgenService;
    private final JPAQueryFactory queryFactory;

    public EgovBbsMasterServiceImpl(
            EgovBbsMasterRepository repository,
            EgovBbsMasterOptionRepository optionRepository,
            @Qualifier("egovBBSMstrIdGnrService") EgovIdGnrService idgenService,
            JPAQueryFactory queryFactory
    ) {
        this.repository = repository;
        this.optionRepository = optionRepository;
        this.idgenService = idgenService;
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<BbsMasterDTO> list(BbsMasterVO bbsMasterVO) {
        Pageable pageable = PageRequest.of(bbsMasterVO.getFirstIndex(), bbsMasterVO.getRecordCountPerPage());
        String searchCondition = bbsMasterVO.getSearchCondition();
        String searchKeyword = bbsMasterVO.getSearchKeyword();
        String blogId = bbsMasterVO.getBlogId();

        QBbsMaster bbsMaster = QBbsMaster.bbsMaster;
        QBbsMasterOptn masterOptn = QBbsMasterOptn.bbsMasterOptn;
        QTmplatInfo tmplatInfo = QTmplatInfo.tmplatInfo;
        QUserMaster userMaster = QUserMaster.userMaster;
        QCmmnDetailCode cmmnDetailCode = QCmmnDetailCode.cmmnDetailCode;

        BooleanBuilder where = new BooleanBuilder();
        if ("1".equals(searchCondition) && searchKeyword != null && !searchKeyword.isEmpty()) {
            where.and(bbsMaster.bbsNm.contains(searchKeyword));
        } else if ("2".equals(searchCondition) && searchKeyword != null && !searchKeyword.isEmpty()) {
            where.and(bbsMaster.bbsIntrcn.contains(searchKeyword));
        }

        List<Tuple> results = bbsMasterListQuery()
                .where(bbsMaster.blogId.eq(blogId).and(where))
                .orderBy(masterOptn.frstRegistPnttm.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                queryFactory
                        .select(bbsMaster.count())
                        .from(bbsMaster)
                        .leftJoin(masterOptn)
                        .on(bbsMaster.bbsId.eq(bbsMaster.bbsId))
                        .leftJoin(tmplatInfo)
                        .on(bbsMaster.tmplatId.eq(tmplatInfo.tmplatId))
                        .leftJoin(userMaster)
                        .on(bbsMaster.frstRegisterId.eq(userMaster.esntlId))
                        .leftJoin(cmmnDetailCode)
                        .on(bbsMaster.bbsTyCode.eq(cmmnDetailCode.cmmnDetailCodeId.code)
                                .and(cmmnDetailCode.useAt.eq("Y"))
                                .and(cmmnDetailCode.cmmnDetailCodeId.codeId.eq("COM101")))
                        .where(bbsMaster.blogId.eq(blogId).and(where))
                        .fetchOne()
        ).orElse(0L);

        List<BbsMasterDTO> content = results.stream().map(tuple -> {

            BbsMaster bm = tuple.get(bbsMaster);
            BbsMasterOptn bmop = tuple.get(masterOptn);
            TmplatInfo tmplat = tuple.get(tmplatInfo);
            CmmnDetailCode code = tuple.get(cmmnDetailCode);
            UserMaster user = tuple.get(userMaster);

            String answerAt = bmop != null && bmop.getAnswerAt() != null ? bmop.getAnswerAt() : "";
            String stsfdgAt = bmop != null && bmop.getStsfdgAt() != null ? bmop.getStsfdgAt() : "";
            String tmplatNm = tmplat != null && tmplat.getTmplatNm() != null ? tmplat.getTmplatNm() : "";
            String userNm = user != null && user.getUserNm() != null ? user.getUserNm() : "";
            String codeNm = code != null && code.getCodeNm() != null ? code.getCodeNm() : "";

            return new BbsMasterDTO(
                    Objects.requireNonNull(bm).getBbsId(),
                    bm.getBbsNm(),
                    bm.getBbsIntrcn(),
                    bm.getBbsTyCode(),
                    bm.getReplyPosblAt(),
                    bm.getFileAtchPosblAt(),
                    bm.getAtchPosblFileNumber(),
                    bm.getAtchPosblFileSize(),
                    bm.getUseAt(),
                    bm.getTmplatId(),
                    bm.getCmmntyId(),
                    bm.getFrstRegisterId(),
                    bm.getFrstRegistPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    bm.getLastUpdusrId(),
                    bm.getLastUpdtPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    bm.getBlogId(),
                    bm.getBlogAt(),
                    answerAt,
                    stsfdgAt,
                    tmplatNm,
                    userNm,
                    codeNm
            );
        }).collect(Collectors.toList());
        return new PageImpl<>(content,pageable,total);
    }

    @Override
    public BbsMasterDTO detail(BbsMasterVO bbsMasterVO) {

        QBbsMaster bbsMaster = QBbsMaster.bbsMaster;
        QBbsMasterOptn masterOptn = QBbsMasterOptn.bbsMasterOptn;
        QTmplatInfo tmplatInfo = QTmplatInfo.tmplatInfo;
        QUserMaster userMaster = QUserMaster.userMaster;
        QCmmnDetailCode cmmnDetailCode = QCmmnDetailCode.cmmnDetailCode;

        Tuple tuple = bbsMasterListQuery()
                .where(bbsMaster.bbsId.eq(bbsMasterVO.getBbsId())
                        .and(bbsMaster.blogId.eq(bbsMasterVO.getBlogId())))
                .fetchOne();

        BbsMaster bm = tuple.get(bbsMaster);
        BbsMasterOptn bmop = tuple.get(masterOptn);
        TmplatInfo tmplat = tuple.get(tmplatInfo);
        CmmnDetailCode code = tuple.get(cmmnDetailCode);
        UserMaster user = tuple.get(userMaster);

        String answerAt = bmop != null && bmop.getAnswerAt() != null ? bmop.getAnswerAt() : "";
        String stsfdgAt = bmop != null && bmop.getStsfdgAt() != null ? bmop.getStsfdgAt() : "";
        String tmplatNm = tmplat != null && tmplat.getTmplatNm() != null ? tmplat.getTmplatNm() : "";
        String userNm = user != null && user.getUserNm() != null ? user.getUserNm() : "";
        String codeNm = code != null && code.getCodeNm() != null ? code.getCodeNm() : "";

        return new BbsMasterDTO(
                Objects.requireNonNull(bm).getBbsId(),
                bm.getBbsNm(),
                bm.getBbsIntrcn(),
                bm.getBbsTyCode(),
                bm.getReplyPosblAt(),
                bm.getFileAtchPosblAt(),
                bm.getAtchPosblFileNumber(),
                bm.getAtchPosblFileSize(),
                bm.getUseAt(),
                bm.getTmplatId(),
                bm.getCmmntyId(),
                bm.getFrstRegisterId(),
                bm.getFrstRegistPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                bm.getLastUpdusrId(),
                bm.getLastUpdtPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                bm.getBlogId(),
                bm.getBlogAt(),
                answerAt,
                stsfdgAt,
                tmplatNm,
                userNm,
                codeNm
        );
    }

    @Transactional
    @Override
    public BbsMasterVO insert(BbsMasterVO bbsMasterVO, Map<String, String> userInfo) {
        try {
            String bbsId = idgenService.getNextStringId() + RandomStringUtils.randomAlphabetic(10);

            BbsMasterOptnVO bbsMasterOptnVO = getBbsMasterOptnVO(bbsMasterVO, bbsId, userInfo.get("uniqId"));
            if (!"1".equals(bbsMasterVO.getBbsOption())) {
                optionRepository.save(EgovBlogUtility.bbsMasterOptnVOEntity(bbsMasterOptnVO));
            }

            bbsMasterVO.setBbsId(bbsId);
            bbsMasterVO.setFrstRegistPnttm(LocalDateTime.now());
            bbsMasterVO.setFrstRegisterId(userInfo.get("uniqId"));
            bbsMasterVO.setLastUpdtPnttm(LocalDateTime.now());
            bbsMasterVO.setLastUpdusrId(userInfo.get("uniqId"));
            BbsMaster bbsMaster = repository.save(EgovBlogUtility.bbsMasterVOTOEntity(bbsMasterVO));

            return EgovBlogUtility.bbsMasterEntityTOVO(bbsMaster);
        } catch (Exception ex) {
            leaveaTrace("fail.common.insert");
            return null;
        }
    }

    @Transactional
    @Override
    public BbsMasterVO update(BbsMasterVO bbsMasterVO, Map<String, String> userInfo) {
        if (!"1".equals(bbsMasterVO.getBbsOption())) {
            BbsMasterOptnVO bbsMasterOptnVO = getBbsMasterOptnVO(bbsMasterVO, bbsMasterVO.getBbsId(), userInfo.get("uniqId"));
            optionRepository.findById(bbsMasterOptnVO.getBbsId())
                    .map(result -> {
                        result.setAnswerAt(bbsMasterOptnVO.getAnswerAt());
                        result.setStsfdgAt(bbsMasterOptnVO.getStsfdgAt());
                        result.setLastUpdtPnttm(LocalDateTime.now());
                        result.setLastUpdusrId(userInfo.get("uniqId"));
                        return optionRepository.save(result);
                    })
                    .orElseGet(() -> optionRepository.save(EgovBlogUtility.bbsMasterOptnVOEntity(bbsMasterOptnVO)));
        }

        return repository.findById(bbsMasterVO.getBbsId())
                .map(result -> {
                    result.setBbsNm(bbsMasterVO.getBbsNm());
                    result.setBbsIntrcn(bbsMasterVO.getBbsIntrcn());
                    result.setBbsTyCode(bbsMasterVO.getBbsTyCode());
                    result.setReplyPosblAt(bbsMasterVO.getReplyPosblAt());
                    result.setFileAtchPosblAt(bbsMasterVO.getFileAtchPosblAt());
                    result.setAtchPosblFileNumber(bbsMasterVO.getAtchPosblFileNumber());
                    result.setUseAt(bbsMasterVO.getUseAt());
                    result.setLastUpdtPnttm(LocalDateTime.now());
                    result.setLastUpdusrId(userInfo.get("uniqId"));
                    return repository.save(result);
                })
                .map(EgovBlogUtility::bbsMasterEntityTOVO).orElse(null);
    }

    private BbsMasterOptnVO getBbsMasterOptnVO(BbsMasterVO bbsMasterVO, String bbsId, String uniqId) {
        BbsMasterOptnVO bbsMasterOptnVO = new BbsMasterOptnVO();
        bbsMasterOptnVO.setBbsId(bbsId);
        if ("2".equals(bbsMasterVO.getBbsOption())) {
            bbsMasterOptnVO.setAnswerAt("Y");
            bbsMasterOptnVO.setStsfdgAt("N");
        } else if ("3".equals(bbsMasterVO.getBbsOption())) {
            bbsMasterOptnVO.setAnswerAt("N");
            bbsMasterOptnVO.setStsfdgAt("Y");
        } else {
            bbsMasterOptnVO.setAnswerAt("N");
            bbsMasterOptnVO.setStsfdgAt("N");
        }
        bbsMasterOptnVO.setFrstRegistPnttm(LocalDateTime.now());
        bbsMasterOptnVO.setFrstRegisterId(uniqId);
        bbsMasterOptnVO.setLastUpdtPnttm(LocalDateTime.now());
        bbsMasterOptnVO.setLastUpdusrId(uniqId);
        return bbsMasterOptnVO;
    }

    private JPAQuery<Tuple> bbsMasterListQuery() {
        QBbsMaster bbsMaster = QBbsMaster.bbsMaster;
        QBbsMasterOptn masterOptn = QBbsMasterOptn.bbsMasterOptn;
        QTmplatInfo tmplatInfo = QTmplatInfo.tmplatInfo;
        QUserMaster userMaster = QUserMaster.userMaster;
        QCmmnDetailCode cmmnDetailCode = QCmmnDetailCode.cmmnDetailCode;

        return queryFactory
                .select(bbsMaster, masterOptn, tmplatInfo, userMaster, cmmnDetailCode)
                .from(bbsMaster)
                .leftJoin(masterOptn)
                .on(bbsMaster.bbsId.eq(masterOptn.bbsId))
                .leftJoin(tmplatInfo)
                .on(bbsMaster.tmplatId.eq(tmplatInfo.tmplatId))
                .leftJoin(userMaster)
                .on(bbsMaster.frstRegisterId.eq(userMaster.esntlId))
                .leftJoin(cmmnDetailCode)
                .on(bbsMaster.bbsTyCode.eq(cmmnDetailCode.cmmnDetailCodeId.code)
                        .and(cmmnDetailCode.useAt.eq("Y"))
                        .and(cmmnDetailCode.cmmnDetailCodeId.codeId.eq("COM101")));
    }
}
