package egovframework.com.uss.olp.qmc.service.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.com.uss.olp.qmc.entity.QQustnrIem;
import egovframework.com.uss.olp.qmc.entity.QQustnrRspnsResult;
import egovframework.com.uss.olp.qmc.entity.QustnrRspnsResult;
import egovframework.com.uss.olp.qmc.repository.EgovQustnrRspnsResultRepository;
import egovframework.com.uss.olp.qmc.service.EgovQustnrRspnsResultService;
import egovframework.com.uss.olp.qmc.service.QestnrInfoVO;
import egovframework.com.uss.olp.qmc.service.QustnrRspnsResultESStatsDTO;
import egovframework.com.uss.olp.qmc.service.QustnrRspnsResultMCStatsDTO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository("qmcEgovQustnrRspnsResultService")
@RequiredArgsConstructor
public class EgovQustnrRspnsResultServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrRspnsResultService {

    private final EgovQustnrRspnsResultRepository repository;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<QustnrRspnsResultMCStatsDTO> qustnrRspnsResultMCStats(QestnrInfoVO qestnrInfoVO) {

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
                .where(qustnrIem.qustnrIemId.qustnrTmplatId.eq(qestnrInfoVO.getQustnrTmplatId())
                        .and(qustnrIem.qustnrIemId.qestnrId.eq(qestnrInfoVO.getQestnrId())))
                .groupBy(qustnrIem.qustnrIemId.qustnrTmplatId,
                        qustnrIem.qustnrIemId.qestnrId,
                        qustnrIem.qustnrIemId.qustnrQesitmId,
                        qustnrIem.qustnrIemId.qustnrIemId,
                        qustnrRspnsResult.qustnrRspnsResultId.qustnrQesitmId,
                        qustnrIem.iemCn)
                .fetch();
    }

    @Override
    public List<QustnrRspnsResultESStatsDTO> qustnrRspnsResultESStats(QestnrInfoVO qestnrInfoVO) {

        QQustnrRspnsResult qustnrRspnsResult = QQustnrRspnsResult.qustnrRspnsResult;

        List<QustnrRspnsResult> results = queryFactory
                .select(qustnrRspnsResult)
                .from(qustnrRspnsResult)
                .where(qustnrRspnsResult.qustnrRspnsResultId.qustnrTmplatId.eq(qestnrInfoVO.getQustnrTmplatId())
                        .and(qustnrRspnsResult.qustnrRspnsResultId.qestnrId.eq(qestnrInfoVO.getQestnrId()))
                        .and(qustnrRspnsResult.qustnrIemId.isNull().or(qustnrRspnsResult.qustnrIemId.eq(""))))
                .fetch();

        List<QustnrRspnsResultESStatsDTO> content = results.stream().map(qrs -> {
            return new QustnrRspnsResultESStatsDTO(
                    qrs.getQustnrRspnsResultId().getQustnrTmplatId(),
                    qrs.getQustnrRspnsResultId().getQestnrId(),
                    qrs.getQustnrRspnsResultId().getQustnrQesitmId(),
                    qrs.getQustnrIemId(),
                    qrs.getRespondAnswerCn(),
                    qrs.getEtcAnswerCn(),
                    qrs.getRespondNm()
            );
        }).collect(Collectors.toList());

        return content;
    }

}
