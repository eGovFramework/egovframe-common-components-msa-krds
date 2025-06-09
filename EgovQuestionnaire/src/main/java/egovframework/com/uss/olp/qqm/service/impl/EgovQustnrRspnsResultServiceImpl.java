package egovframework.com.uss.olp.qqm.service.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.com.uss.olp.qqm.entity.QQustnrIem;
import egovframework.com.uss.olp.qqm.entity.QQustnrRspnsResult;
import egovframework.com.uss.olp.qqm.entity.QustnrRspnsResult;
import egovframework.com.uss.olp.qqm.repository.EgovQustnrRspnsResultRepository;
import egovframework.com.uss.olp.qqm.service.EgovQustnrRspnsResultService;
import egovframework.com.uss.olp.qqm.service.QustnrQesitmVO;
import egovframework.com.uss.olp.qqm.service.QustnrRspnsResultESStatsDTO;
import egovframework.com.uss.olp.qqm.service.QustnrRspnsResultMCStatsDTO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("qqmEgovQustnrRspnsResultService")
@RequiredArgsConstructor
public class EgovQustnrRspnsResultServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrRspnsResultService {

    private final EgovQustnrRspnsResultRepository repository;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<QustnrRspnsResultMCStatsDTO> qustnrRspnsResultMCStats(QustnrQesitmVO qustnrQesitmVO) {

        QQustnrIem qustnrIem = QQustnrIem.qustnrIem;
        QQustnrRspnsResult qustnrRspnsResult = QQustnrRspnsResult.qustnrRspnsResult;

        NumberExpression<Long> iemCount = qustnrRspnsResult.qustnrIemId.count();
        NumberTemplate<Long> percentageExpr = Expressions.numberTemplate(Long.class,
                "CASE WHEN ({1}) = 0 THEN 0 ELSE ROUND((100.0 * {0}) / ({1})) END",
                qustnrRspnsResult.qustnrIemId.count(),
                JPAExpressions.select(qustnrRspnsResult.count())
                        .from(qustnrRspnsResult)
                        .where(qustnrRspnsResult.qustnrRspnsResultId.qustnrQesitmId.eq(qustnrIem.qustnrIemId.qustnrQesitmId))
        );

        return queryFactory
                .select(Projections.constructor(
                        QustnrRspnsResultMCStatsDTO.class,
                        qustnrIem.qustnrIemId.qustnrTmplatId,
                        qustnrIem.qustnrIemId.qestnrId,
                        qustnrIem.qustnrIemId.qustnrQesitmId,
                        qustnrIem.qustnrIemId.qustnrIemId,
                        qustnrIem.iemCn,
                        iemCount,
                        percentageExpr
                ))
                .from(qustnrIem)
                .leftJoin(qustnrRspnsResult)
                .on(qustnrIem.qustnrIemId.qustnrIemId.eq(qustnrRspnsResult.qustnrIemId))
                .where(qustnrIem.qustnrIemId.qustnrTmplatId.eq(qustnrQesitmVO.getQustnrTmplatId())
                        .and(qustnrIem.qustnrIemId.qestnrId.eq(qustnrQesitmVO.getQestnrId())))
                .groupBy(qustnrIem.qustnrIemId.qustnrTmplatId,
                        qustnrIem.qustnrIemId.qestnrId,
                        qustnrIem.qustnrIemId.qustnrQesitmId,
                        qustnrIem.qustnrIemId.qustnrIemId,
                        qustnrRspnsResult.qustnrRspnsResultId.qustnrQesitmId,
                        qustnrIem.iemCn)
                .fetch();
    }

    @Override
    public List<QustnrRspnsResultESStatsDTO> qustnrRspnsResultESStats(QustnrQesitmVO qustnrQesitmVO) {

        QQustnrRspnsResult qustnrRspnsResult = QQustnrRspnsResult.qustnrRspnsResult;

        List<QustnrRspnsResult> results = queryFactory
                .select(qustnrRspnsResult)
                .from(qustnrRspnsResult)
                .where(qustnrRspnsResult.qustnrRspnsResultId.qustnrTmplatId.eq(qustnrQesitmVO.getQustnrTmplatId())
                        .and(qustnrRspnsResult.qustnrRspnsResultId.qestnrId.eq(qustnrQesitmVO.getQestnrId()))
                        .and(qustnrRspnsResult.qustnrRspnsResultId.isNull().or(qustnrRspnsResult.qustnrIemId.eq(""))))
                .fetch();

        List<QustnrRspnsResultESStatsDTO> content = results.stream().map(qrr -> {
            return new QustnrRspnsResultESStatsDTO(
                    qrr.getQustnrRspnsResultId().getQustnrTmplatId(),
                    qrr.getQustnrRspnsResultId().getQestnrId(),
                    qrr.getQustnrRspnsResultId().getQustnrQesitmId(),
                    qrr.getQustnrIemId(),
                    qrr.getRespondAnswerCn(),
                    qrr.getEtcAnswerCn(),
                    qrr.getRespondNm()
            );
        }).collect(Collectors.toList());
        return content;
    }

}
