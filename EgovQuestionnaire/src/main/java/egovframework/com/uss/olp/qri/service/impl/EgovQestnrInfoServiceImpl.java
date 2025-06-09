package egovframework.com.uss.olp.qri.service.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.com.uss.olp.qri.entity.*;
import egovframework.com.uss.olp.qri.repository.EgovQestnrInfoRepository;
import egovframework.com.uss.olp.qri.service.EgovQestnrInfoService;
import egovframework.com.uss.olp.qri.service.QestnrInfoDTO;
import egovframework.com.uss.olp.qri.service.QustnrRspnsResultVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Service("qriEgovQestnrInfoService")
@RequiredArgsConstructor
public class EgovQestnrInfoServiceImpl extends EgovAbstractServiceImpl implements EgovQestnrInfoService {

    private final EgovQestnrInfoRepository repository;
    private final JPAQueryFactory queryFactory;

    @Override
    public QestnrInfoDTO detail(QustnrRspnsResultVO qustnrRspnsResultVO) {

        QQestnrInfo qestnrInfo = QQestnrInfo.qestnrInfo;
        QUserMaster userMaster = QUserMaster.userMaster;
        QQustnrTmplat qustnrTmplat = QQustnrTmplat.qustnrTmplat;
        QCmmnDetailCode cmmnDetailCode = QCmmnDetailCode.cmmnDetailCode;

        Tuple tuple = queryFactory.select(qestnrInfo, userMaster, qustnrTmplat, cmmnDetailCode)
                .from(qestnrInfo)
                .leftJoin(userMaster)
                .on(qestnrInfo.frstRegisterId.eq(userMaster.esntlId))
                .leftJoin(qustnrTmplat)
                .on(qestnrInfo.qestnrInfoId.qustnrTmplatId.eq(qustnrTmplat.qustnrTmplatId))
                .leftJoin(cmmnDetailCode)
                .on(qestnrInfo.qustnrTrget.eq(cmmnDetailCode.cmmnDetailCodeId.code)
                        .and(cmmnDetailCode.cmmnDetailCodeId.codeId.eq("COM034")))
                .where(qestnrInfo.qestnrInfoId.qustnrTmplatId.eq(qustnrRspnsResultVO.getQustnrTmplatId())
                        .and(qestnrInfo.qestnrInfoId.qestnrId.eq(qustnrRspnsResultVO.getQestnrId())))
                .fetchOne();

        QestnrInfo qe = tuple.get(qestnrInfo);
        UserMaster user = tuple.get(userMaster);
        QustnrTmplat qtm = tuple.get(qustnrTmplat);
        CmmnDetailCode code = tuple.get(cmmnDetailCode);

        String userNm = user != null && user.getUserNm() != null ? user.getUserNm() : "";
        String qustnrTmplayTy = qtm != null  && qtm.getQustnrTmplatTy() != null ? qtm.getQustnrTmplatTy(): "";
        String codeNm = code != null && code.getCodeNm() != null ? code.getCodeNm() : "";

        return new QestnrInfoDTO(
                Objects.requireNonNull(qe).getQestnrInfoId().getQustnrTmplatId(),
                qe.getQestnrInfoId().getQestnrId(),
                qe.getQustnrSj(),
                qe.getQustnrPurps(),
                qe.getQustnrWritingGuidanceCn(),
                qe.getQustnrTrget(),
                formatDateString(qe.getQustnrBgnde()),
                formatDateString(qe.getQustnrEndde()),
                qe.getFrstRegistPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                qe.getFrstRegisterId(),
                qe.getLastUpdtPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                qe.getLastUpdusrId(),
                userNm,
                qustnrTmplayTy,
                codeNm
        );
    }

    private String formatDateString(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return "";
        try {
            LocalDate date = LocalDate.parse(dateStr.trim(), DateTimeFormatter.ofPattern("yyyyMMdd"));
            return date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        } catch (DateTimeParseException e) {
            return "";
        }
    }

}
