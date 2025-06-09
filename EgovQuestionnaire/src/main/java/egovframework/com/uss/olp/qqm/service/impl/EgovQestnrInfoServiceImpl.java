package egovframework.com.uss.olp.qqm.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.com.uss.olp.qqm.entity.*;
import egovframework.com.uss.olp.qqm.repository.EgovQestnrInfoRepository;
import egovframework.com.uss.olp.qqm.service.EgovQestnrInfoService;
import egovframework.com.uss.olp.qqm.service.QestnrInfoDTO;
import egovframework.com.uss.olp.qqm.service.QestnrInfoVO;
import egovframework.com.uss.olp.qqm.service.QustnrQesitmVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("qqmEgovQestnrInfoService")
@RequiredArgsConstructor
public class EgovQestnrInfoServiceImpl extends EgovAbstractServiceImpl implements EgovQestnrInfoService {

    private final EgovQestnrInfoRepository repository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<QestnrInfoDTO> list(QestnrInfoVO qestnrInfoVO) {
        Pageable pageable = PageRequest.of(qestnrInfoVO.getFirstIndex(), qestnrInfoVO.getRecordCountPerPage());
        String searchCondition = qestnrInfoVO.getSearchCondition();
        String searchKeyword = qestnrInfoVO.getSearchKeyword();

        QQestnrInfo qestnrInfo = QQestnrInfo.qestnrInfo;
        QUserMaster userMaster = QUserMaster.userMaster;
        QCmmnDetailCode cmmnDetailCode = QCmmnDetailCode.cmmnDetailCode;

        BooleanBuilder where = new BooleanBuilder();
        if ("1".equals(searchCondition) && searchKeyword != null && !searchKeyword.isEmpty()) {
            where.and(qestnrInfo.qustnrSj.contains(searchKeyword));
        } else if ("2".equals(searchCondition) && searchKeyword != null && !searchKeyword.isEmpty()) {
            where.and(userMaster.userNm.contains(searchKeyword));
        }

        JPAQuery<Tuple> query = qestnrInfoQuery(qestnrInfoVO.getQustnrTmplatId(), qestnrInfoVO.getQestnrId())
                .where(where)
                .orderBy(qestnrInfo.frstRegistPnttm.desc());
        List<Tuple> results = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
        long total = Optional.ofNullable(
                queryFactory
                        .select(qestnrInfo.count())
                        .from(qestnrInfo)
                        .leftJoin(userMaster)
                        .on(qestnrInfo.frstRegisterId.eq(userMaster.esntlId))
                        .leftJoin(cmmnDetailCode)
                        .on(qestnrInfo.qustnrTrget.eq(cmmnDetailCode.cmmnDetailCodeId.code)
                                .and(cmmnDetailCode.cmmnDetailCodeId.codeId.eq("COM034")))
                        .where(where)
                        .fetchOne()
        ).orElse(0L);

        List<QestnrInfoDTO> content = results.stream().map(tuple -> {

            QestnrInfo qe = tuple.get(qestnrInfo);
            UserMaster user = tuple.get(userMaster);
            CmmnDetailCode code = tuple.get(cmmnDetailCode);

            String userNm = user != null && user.getUserNm() != null ? user.getUserNm() : "";
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
                    codeNm
            );
        }).collect(Collectors.toList());
        return new PageImpl<>(content,pageable,total);
    }

    @Override
    public QestnrInfoDTO detail(QustnrQesitmVO qustnrQesitmVO) {
        QQestnrInfo qestnrInfo = QQestnrInfo.qestnrInfo;
        QUserMaster userMaster = QUserMaster.userMaster;
        QCmmnDetailCode cmmnDetailCode = QCmmnDetailCode.cmmnDetailCode;

        Tuple tuple = qestnrInfoQuery(qustnrQesitmVO.getQustnrTmplatId(), qustnrQesitmVO.getQestnrId())
                .where(qestnrInfo.qestnrInfoId.qustnrTmplatId.eq(qustnrQesitmVO.getQustnrTmplatId()))
                .fetchOne();
        if (tuple == null) return null;

        QestnrInfo qe = tuple.get(qestnrInfo);
        UserMaster user = tuple.get(userMaster);
        CmmnDetailCode code = tuple.get(cmmnDetailCode);

        String userNm = user != null && user.getUserNm() != null ? user.getUserNm() : "";
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
                codeNm
        );
    }

    private JPAQuery<Tuple> qestnrInfoQuery(String qustnrTmplatId, String qestnrId){

        QQestnrInfo qestnrInfo = QQestnrInfo.qestnrInfo;
        QUserMaster userMaster = QUserMaster.userMaster;
        QCmmnDetailCode cmmnDetailCode = QCmmnDetailCode.cmmnDetailCode;

        return queryFactory
                .select(qestnrInfo,userMaster,cmmnDetailCode)
                .from(qestnrInfo)
                .leftJoin(userMaster)
                .on(qestnrInfo.frstRegisterId.eq(userMaster.esntlId))
                .leftJoin(cmmnDetailCode)
                .on(qestnrInfo.qustnrTrget.eq(cmmnDetailCode.cmmnDetailCodeId.code)
                        .and(cmmnDetailCode.cmmnDetailCodeId.codeId.eq("COM034")));
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
