package egovframework.com.uss.olp.qmc.service.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.com.uss.olp.qmc.entity.*;
import egovframework.com.uss.olp.qmc.repository.EgovQustnrQesitmRepository;
import egovframework.com.uss.olp.qmc.service.EgovQustnrQesitmService;
import egovframework.com.uss.olp.qmc.service.QestnrInfoVO;
import egovframework.com.uss.olp.qmc.service.QustnrQesitmDTO;
import egovframework.com.uss.olp.qmc.service.QustnrQesitmVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("qmcEgovQustnrQesitmService")
@RequiredArgsConstructor
public class EgovQustnrQesitmServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrQesitmService {

    private final EgovQustnrQesitmRepository repository;
    private final JPAQueryFactory queryFactory;
    @Override
    public Page<QustnrQesitmDTO> list(QustnrQesitmVO qustnrQesitmVO) {
        Pageable pageable = PageRequest.of(qustnrQesitmVO.getFirstIndex(), qustnrQesitmVO.getRecordCountPerPage());
        String qustnrTmplatId = qustnrQesitmVO.getQustnrTmplatId();
        String qestnrId = qustnrQesitmVO.getQestnrId();

        QQustnrQesitm qustnrQesitm = QQustnrQesitm.qustnrQesitm;
        QQestnrInfo qestnrInfo = QQestnrInfo.qestnrInfo;
        QUserMaster userMaster = QUserMaster.userMaster;
        QCmmnDetailCode cmmnDetailCode = QCmmnDetailCode.cmmnDetailCode;

        JPAQuery<Tuple> query = qustnrQesitmResult(qustnrTmplatId,qestnrId,"qim");
        List<Tuple> results = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
        long total = Optional.ofNullable(
                queryFactory
                        .select(qustnrQesitm.count())
                        .from(qustnrQesitm)
                        .leftJoin(qestnrInfo)
                        .on(qustnrQesitm.qustnrQesitmId.qestnrId.eq(qestnrInfo.qestnrInfoId.qestnrId))
                        .leftJoin(userMaster)
                        .on(qustnrQesitm.frstRegisterId.eq(userMaster.esntlId))
                        .leftJoin(cmmnDetailCode)
                        .on(qustnrQesitm.qestnTyCode.eq(cmmnDetailCode.cmmnDetailCodeId.code)
                                .and(cmmnDetailCode.cmmnDetailCodeId.codeId.eq("COM018")))
                        .where(qustnrQesitm.qustnrQesitmId.qustnrTmplatId.eq(qustnrTmplatId)
                                .and(qustnrQesitm.qustnrQesitmId.qestnrId.eq(qestnrId)))
                        .fetchOne()
        ).orElse(0L);

        List<QustnrQesitmDTO> content = results.stream().map(tuple -> {

            QustnrQesitm qqm =tuple.get(qustnrQesitm);
            QestnrInfo qe = tuple.get(qestnrInfo);
            UserMaster user = tuple.get(userMaster);
            CmmnDetailCode code = tuple.get(cmmnDetailCode);

            String qustnrSj = qe != null && qe.getQustnrSj() != null ? qe.getQustnrSj() : "";
            String codeNm = code != null && code.getCodeNm() != null ? code.getCodeNm() : "";
            String userNm = user != null && user.getUserNm() != null ? user.getUserNm() : "";

            return new QustnrQesitmDTO(
                    Objects.requireNonNull(qqm).getQustnrQesitmId().getQustnrTmplatId(),
                    qqm.getQustnrQesitmId().getQestnrId(),
                    qqm.getQustnrQesitmId().getQustnrQesitmId(),
                    qustnrSj,
                    qqm.getQestnSn(),
                    qqm.getQestnTyCode(),
                    codeNm,
                    qqm.getQestnCn(),
                    qqm.getMxmmChoiseCo(),
                    qqm.getFrstRegistPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    qqm.getFrstRegisterId(),
                    qqm.getLastUpdtPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    qqm.getLastUpdusrId(),
                    userNm
            );

        }).collect(Collectors.toList());
        return new PageImpl<>(content,pageable,total);
    }

    @Override
    public List<QustnrQesitmDTO> qustnrQesitmList(QestnrInfoVO qestnrInfoVO) {

        String qustnrTmplatId = qestnrInfoVO.getQustnrTmplatId();
        String qestnrId = qestnrInfoVO.getQestnrId();

        QQustnrQesitm qustnrQesitm = QQustnrQesitm.qustnrQesitm;
        QQestnrInfo qestnrInfo = QQestnrInfo.qestnrInfo;
        QUserMaster userMaster = QUserMaster.userMaster;
        QCmmnDetailCode cmmnDetailCode = QCmmnDetailCode.cmmnDetailCode;

        JPAQuery<Tuple> query = qustnrQesitmResult(qustnrTmplatId,qestnrId,"qe");
        List<Tuple> results = query.fetch();
        List<QustnrQesitmDTO> content = results.stream().map(tuple -> {

            QustnrQesitm qqm =tuple.get(qustnrQesitm);
            QestnrInfo qe = tuple.get(qestnrInfo);
            UserMaster user = tuple.get(userMaster);
            CmmnDetailCode code = tuple.get(cmmnDetailCode);

            String qustnrSj = qe != null && qe.getQustnrSj() != null ? qe.getQustnrSj() : "";
            String codeNm = code != null && code.getCodeNm() != null ? code.getCodeNm() : "";
            String userNm = user != null && user.getUserNm() != null ? user.getUserNm() : "";

            return new QustnrQesitmDTO(
                    Objects.requireNonNull(qqm).getQustnrQesitmId().getQustnrTmplatId(),
                    qqm.getQustnrQesitmId().getQestnrId(),
                    qqm.getQustnrQesitmId().getQustnrQesitmId(),
                    qustnrSj,
                    qqm.getQestnSn(),
                    qqm.getQestnTyCode(),
                    codeNm,
                    qqm.getQestnCn(),
                    qqm.getMxmmChoiseCo(),
                    qqm.getFrstRegistPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    qqm.getFrstRegisterId(),
                    qqm.getLastUpdtPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    qqm.getLastUpdusrId(),
                    userNm
            );

        }).collect(Collectors.toList());
        return content;
    }

    private JPAQuery<Tuple> qustnrQesitmResult(String qustnrTmplatId, String qestnrId, String vo){

        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        QQustnrQesitm qustnrQesitm = QQustnrQesitm.qustnrQesitm;
        QQestnrInfo qestnrInfo = QQestnrInfo.qestnrInfo;
        QUserMaster userMaster = QUserMaster.userMaster;
        QCmmnDetailCode cmmnDetailCode = QCmmnDetailCode.cmmnDetailCode;

        if ("qim".equals(vo)) {
            orderSpecifiers.add(qustnrQesitm.qustnrQesitmId.qestnrId.desc());
            orderSpecifiers.add(qustnrQesitm.qustnrQesitmId.qustnrQesitmId.desc());
        } else if ("qe".equals(vo)) {
            orderSpecifiers.add(qustnrQesitm.qestnSn.asc());
        }

        return queryFactory
                .select(qustnrQesitm, qestnrInfo,userMaster,cmmnDetailCode)
                .from(qustnrQesitm)
                .leftJoin(qestnrInfo)
                .on(qustnrQesitm.qustnrQesitmId.qestnrId.eq(qestnrInfo.qestnrInfoId.qestnrId))
                .leftJoin(userMaster)
                .on(qustnrQesitm.frstRegisterId.eq(userMaster.esntlId))
                .leftJoin(cmmnDetailCode)
                .on(qustnrQesitm.qestnTyCode.eq(cmmnDetailCode.cmmnDetailCodeId.code)
                        .and(cmmnDetailCode.cmmnDetailCodeId.codeId.eq("COM018")))
                .where(qustnrQesitm.qustnrQesitmId.qustnrTmplatId.eq(qustnrTmplatId)
                        .and(qustnrQesitm.qustnrQesitmId.qestnrId.eq(qestnrId)))
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]));
    }
}
