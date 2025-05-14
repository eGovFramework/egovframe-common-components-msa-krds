package egovframework.com.uss.olp.qqm.service.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.com.uss.olp.qqm.entity.*;
import egovframework.com.uss.olp.qqm.service.EgovQustnrItemService;
import egovframework.com.uss.olp.qqm.service.QustnrIemDTO;
import egovframework.com.uss.olp.qqm.service.QustnrIemVO;
import egovframework.com.uss.olp.qqm.service.QustnrQesitmVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("qqmEgovQustnrItemService")
@RequiredArgsConstructor
public class EgovQustnrItemServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrItemService {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<QustnrIemDTO> list(QustnrIemVO qustnrIemVO) {
        Pageable pageable = PageRequest.of(qustnrIemVO.getFirstIndex(), qustnrIemVO.getRecordCountPerPage());
        String qustnrTmplatId = qustnrIemVO.getQustnrTmplatId();
        String qestnrId = qustnrIemVO.getQestnrId();
        String qustnrQesitmId = qustnrIemVO.getQustnrQesitmId();

        QQustnrIem qustnrIem = QQustnrIem.qustnrIem;
        QUserMaster userMaster = QUserMaster.userMaster;
        QQustnrQesitm qustnrQesitm = QQustnrQesitm.qustnrQesitm;
        QQestnrInfo qestnrInfo = QQestnrInfo.qestnrInfo;

        List<Tuple> results = qustnrIemQuery()
                .where(qustnrIem.qustnrIemId.qustnrTmplatId.eq(qustnrTmplatId),
                        qustnrIem.qustnrIemId.qestnrId.eq(qestnrId),
                        qustnrIem.qustnrIemId.qustnrQesitmId.eq(qustnrQesitmId))
                .orderBy(qustnrIem.qustnrIemId.qestnrId.desc(),
                        qustnrIem.qustnrIemId.qustnrQesitmId.desc(),
                        qustnrIem.qustnrIemId.qustnrIemId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                queryFactory
                        .select(qustnrIem.count())
                        .from(qustnrIem)
                        .leftJoin(userMaster)
                        .on(qustnrIem.frstRegisterId.eq(userMaster.esntlId))
                        .leftJoin(qustnrQesitm)
                        .on(qustnrIem.qustnrIemId.qustnrQesitmId.eq(qustnrQesitm.qustnrQesitmId.qustnrQesitmId))
                        .leftJoin(qestnrInfo)
                        .on(qustnrIem.qustnrIemId.qestnrId.eq(qestnrInfo.qestnrInfoId.qestnrId))
                        .where(qustnrIem.qustnrIemId.qustnrTmplatId.eq(qustnrTmplatId),
                                qustnrIem.qustnrIemId.qestnrId.eq(qestnrId),
                                qustnrIem.qustnrIemId.qustnrQesitmId.eq(qustnrQesitmId))
                        .fetchOne()
        ).orElse(0L);

        List<QustnrIemDTO> content = results.stream().map(tuple -> {

            QustnrIem qim = tuple.get(qustnrIem);
            QestnrInfo qe = tuple.get(qestnrInfo);
            QustnrQesitm qqm = tuple.get(qustnrQesitm);
            UserMaster user = tuple.get(userMaster);

            String qustnrSj = qe != null && qe.getQustnrSj() != null ? qe.getQustnrSj() : "";
            String qestnCn = qqm != null && qqm.getQestnCn() != null ? qqm.getQestnCn() : "";
            String userNm = user != null && user.getUserNm() != null ? user.getUserNm() : "";

            return new QustnrIemDTO(
                    Objects.requireNonNull(qim).getQustnrIemId().getQustnrTmplatId(),
                    qim.getQustnrIemId().getQestnrId(),
                    qustnrSj,
                    qim.getQustnrIemId().getQustnrQesitmId(),
                    qestnCn,
                    qim.getQustnrIemId().getQustnrIemId(),
                    qim.getIemSn(),
                    qim.getIemCn(),
                    qim.getEtcAnswerAt(),
                    qim.getFrstRegistPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    qim.getFrstRegisterId(),
                    qim.getLastUpdtPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    qim.getLastUpdusrId(),
                    userNm
            );
        }).collect(Collectors.toList());
        return new PageImpl<>(content,pageable,total);
    }

    @Override
    public List<QustnrIemDTO> qustnrIemList(QustnrQesitmVO qustnrQesitmVO) {

        QQustnrIem qustnrIem = QQustnrIem.qustnrIem;
        QUserMaster userMaster = QUserMaster.userMaster;
        QQustnrQesitm qustnrQesitm = QQustnrQesitm.qustnrQesitm;
        QQestnrInfo qestnrInfo = QQestnrInfo.qestnrInfo;

        List<Tuple> results = qustnrIemQuery()
                .where(qustnrIem.qustnrIemId.qustnrTmplatId.eq(qustnrQesitmVO.getQustnrTmplatId())
                        .and(qustnrIem.qustnrIemId.qestnrId.eq(qustnrQesitmVO.getQestnrId())))
                .orderBy(qustnrIem.qustnrIemId.qustnrQesitmId.asc(),
                        qustnrIem.iemSn.asc())
                .fetch();

        List<QustnrIemDTO> content = results.stream().map(tuple -> {

            QustnrIem qim = tuple.get(qustnrIem);
            QestnrInfo qe = tuple.get(qestnrInfo);
            QustnrQesitm qqm = tuple.get(qustnrQesitm);
            UserMaster user = tuple.get(userMaster);

            String qustnrSj = qe != null && qe.getQustnrSj() != null ? qe.getQustnrSj() : "";
            String qestnCn = qqm != null && qqm.getQestnCn() != null ? qqm.getQestnCn() : "";
            String userNm = user != null && user.getUserNm() != null ? user.getUserNm() : "";


            return new QustnrIemDTO(
                    Objects.requireNonNull(qim).getQustnrIemId().getQustnrTmplatId(),
                    qim.getQustnrIemId().getQestnrId(),
                    qustnrSj,
                    qim.getQustnrIemId().getQustnrQesitmId(),
                    qestnCn,
                    qim.getQustnrIemId().getQustnrIemId(),
                    qim.getIemSn(),
                    qim.getIemCn(),
                    qim.getEtcAnswerAt(),
                    qim.getFrstRegistPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    qim.getFrstRegisterId(),
                    qim.getLastUpdtPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    qim.getLastUpdusrId(),
                    userNm
            );
        }).collect(Collectors.toList());

        return content;
    }

    private JPAQuery<Tuple> qustnrIemQuery(){
        QQustnrIem qustnrIem = QQustnrIem.qustnrIem;
        QUserMaster userMaster = QUserMaster.userMaster;
        QQustnrQesitm qustnrQesitm = QQustnrQesitm.qustnrQesitm;
        QQestnrInfo qestnrInfo = QQestnrInfo.qestnrInfo;

        return queryFactory
                .select(qustnrIem,userMaster,qustnrQesitm,qestnrInfo)
                .from(qustnrIem)
                .leftJoin(userMaster)
                .on(qustnrIem.frstRegisterId.eq(userMaster.esntlId))
                .leftJoin(qustnrQesitm)
                .on(qustnrIem.qustnrIemId.qustnrQesitmId.eq(qustnrQesitm.qustnrQesitmId.qustnrQesitmId))
                .leftJoin(qestnrInfo)
                .on(qustnrIem.qustnrIemId.qestnrId.eq(qestnrInfo.qestnrInfoId.qestnrId));
    }

}
