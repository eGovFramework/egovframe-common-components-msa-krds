package egovframework.com.uss.olp.qmc.service.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.com.uss.olp.qmc.entity.*;
import egovframework.com.uss.olp.qmc.repository.EgovQustnrRespondInfoRepository;
import egovframework.com.uss.olp.qmc.service.EgovQustnrRespondInfoService;
import egovframework.com.uss.olp.qmc.service.QustnrRespondInfoDTO;
import egovframework.com.uss.olp.qmc.service.QustnrRespondInfoVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("qmcEgovQustnrRespondInfoService")
@RequiredArgsConstructor
public class EgovQustnrRespondInfoServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrRespondInfoService {

    private final EgovQustnrRespondInfoRepository repository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<QustnrRespondInfoDTO> list(QustnrRespondInfoVO qustnrRespondInfoVO) {
        Pageable pageable = PageRequest.of(qustnrRespondInfoVO.getFirstIndex(), qustnrRespondInfoVO.getRecordCountPerPage());
        String qustnrTmplatId = qustnrRespondInfoVO.getQustnrTmplatId();
        String qestnrId = qustnrRespondInfoVO.getQestnrId();

        QQustnrRespondInfo qustnrRespondInfo = QQustnrRespondInfo.qustnrRespondInfo;
        QUserMaster userMaster = QUserMaster.userMaster;
        QQestnrInfo qestnrInfo = QQestnrInfo.qestnrInfo;
        QCmmnDetailCode sexdstnCode = new QCmmnDetailCode("sexdstnCode");
        QCmmnDetailCode occpTyCode = new QCmmnDetailCode("occpTyCode");

        List<Tuple> query = queryFactory
                .select(qustnrRespondInfo,userMaster,qestnrInfo,sexdstnCode,occpTyCode)
                .from(qustnrRespondInfo)
                .leftJoin(userMaster)
                .on(qustnrRespondInfo.frstRegisterId.eq(userMaster.esntlId))
                .leftJoin(qestnrInfo)
                .on(qustnrRespondInfo.qustnrRespondInfoId.qestnrId.eq(qestnrInfo.qestnrInfoId.qestnrId))
                .leftJoin(sexdstnCode).on(
                        qustnrRespondInfo.sexdstnCode.eq(sexdstnCode.cmmnDetailCodeId.code)
                                .and(sexdstnCode.cmmnDetailCodeId.codeId.eq("COM014"))
                )
                .leftJoin(occpTyCode).on(
                        qustnrRespondInfo.occpTyCode.eq(occpTyCode.cmmnDetailCodeId.code)
                                .and(occpTyCode.cmmnDetailCodeId.codeId.eq("COM034"))
                )
                .where(qustnrRespondInfo.qustnrRespondInfoId.qustnrTmplatId.eq(qustnrTmplatId)
                        .and(qustnrRespondInfo.qustnrRespondInfoId.qestnrId.eq(qestnrId)))
                .orderBy(qustnrRespondInfo.frstRegistPnttm.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = Optional.ofNullable(
                queryFactory
                        .select(qustnrRespondInfo.count())
                        .from(qustnrRespondInfo)
                        .leftJoin(userMaster)
                        .on(qustnrRespondInfo.frstRegisterId.eq(userMaster.esntlId))
                        .leftJoin(qestnrInfo)
                        .on(qustnrRespondInfo.qustnrRespondInfoId.qestnrId.eq(qestnrInfo.qestnrInfoId.qestnrId))
                        .leftJoin(sexdstnCode).on(
                                qustnrRespondInfo.sexdstnCode.eq(sexdstnCode.cmmnDetailCodeId.code)
                                        .and(sexdstnCode.cmmnDetailCodeId.codeId.eq("COM014"))
                        )
                        .leftJoin(occpTyCode).on(
                                qustnrRespondInfo.occpTyCode.eq(occpTyCode.cmmnDetailCodeId.code)
                                        .and(occpTyCode.cmmnDetailCodeId.codeId.eq("COM034"))
                        )
                        .where(qustnrRespondInfo.qustnrRespondInfoId.qustnrTmplatId.eq(qustnrTmplatId)
                                .and(qustnrRespondInfo.qustnrRespondInfoId.qestnrId.eq(qestnrId)))
                        .fetchOne()
        ).orElse(0L);

        List<QustnrRespondInfoDTO> content = query.stream().map(tuple -> {

            QustnrRespondInfo qrs = tuple.get(qustnrRespondInfo);
            QestnrInfo qe = tuple.get(qestnrInfo);
            UserMaster user =tuple.get(userMaster);
            CmmnDetailCode sexCode = tuple.get(sexdstnCode);
            CmmnDetailCode occpTypeCode = tuple.get(occpTyCode);

            String qustnrSj = qe != null && qe.getQustnrSj() != null ? qe.getQustnrSj() : "";
            String sexCodeNm = sexCode != null && sexCode.getCodeNm() != null ? sexCode.getCodeNm() : "";
            String occpTyCodeNm = occpTypeCode != null && occpTypeCode.getCodeNm() != null ? occpTypeCode.getCodeNm() : "";
            String userNm = user != null && user.getUserNm() != null ? user.getUserNm() : "";

            return new QustnrRespondInfoDTO(
                    Objects.requireNonNull(qrs).getQustnrRespondInfoId().getQustnrTmplatId(),
                    qrs.getQustnrRespondInfoId().getQestnrId(),
                    qustnrSj,
                    qrs.getQustnrRespondInfoId().getQustnrRespondId(),
                    qrs.getSexdstnCode(),
                    sexCodeNm,
                    qrs.getOccpTyCode(),
                    occpTyCodeNm,
                    qrs.getRespondNm(),
                    qrs.getFrstRegistPnttm().substring(0,10),
                    qrs.getFrstRegisterId(),
                    qrs.getLastUpdtPnttm().substring(0,10),
                    qrs.getLastUpdusrId(),
                    userNm
            );
        }).collect(Collectors.toList());
        return new PageImpl<>(content,pageable,total);
    }
}
