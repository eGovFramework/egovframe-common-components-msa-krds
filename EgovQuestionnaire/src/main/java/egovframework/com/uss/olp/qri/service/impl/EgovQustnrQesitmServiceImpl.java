package egovframework.com.uss.olp.qri.service.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.com.uss.olp.qri.entity.QQustnrIem;
import egovframework.com.uss.olp.qri.entity.QQustnrQesitm;
import egovframework.com.uss.olp.qri.entity.QustnrIem;
import egovframework.com.uss.olp.qri.entity.QustnrQesitm;
import egovframework.com.uss.olp.qri.repository.EgovQustnrQesitmRepository;
import egovframework.com.uss.olp.qri.service.EgovQustnrQesitmService;
import egovframework.com.uss.olp.qri.service.QustnrQesitmDTO;
import egovframework.com.uss.olp.qri.service.QustnrQesitmItemDTO;
import egovframework.com.uss.olp.qri.service.QustnrRspnsResultVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("qriEgovQustnrQesitmService")
@RequiredArgsConstructor
public class EgovQustnrQesitmServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrQesitmService {

    private final EgovQustnrQesitmRepository repository;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<QustnrQesitmItemDTO> qustnrQesitmItemList(QustnrRspnsResultVO qustnrRspnsResultVO) {

        QQustnrQesitm qustnrQesitm = QQustnrQesitm.qustnrQesitm;
        QQustnrIem qustnrIem = QQustnrIem.qustnrIem;

        List<Tuple> results = queryFactory.select(qustnrQesitm,qustnrIem)
                .from(qustnrQesitm)
                .leftJoin(qustnrIem)
                .on(qustnrQesitm.qustnrQesitmId.qustnrTmplatId.eq(qustnrIem.qustnrIemId.qustnrTmplatId)
                        .and(qustnrQesitm.qustnrQesitmId.qestnrId.eq(qustnrIem.qustnrIemId.qestnrId)
                        .and(qustnrQesitm.qustnrQesitmId.qustnrQesitmId.eq(qustnrIem.qustnrIemId.qustnrQesitmId))))
                .where(qustnrQesitm.qustnrQesitmId.qustnrTmplatId.eq(qustnrRspnsResultVO.getQustnrTmplatId())
                        .and(qustnrQesitm.qustnrQesitmId.qestnrId.eq(qustnrRspnsResultVO.getQestnrId())))
                .groupBy(qustnrQesitm.qustnrQesitmId.qustnrTmplatId,
                        qustnrQesitm.qustnrQesitmId.qestnrId,
                        qustnrQesitm.qustnrQesitmId.qustnrQesitmId,
                        qustnrIem.qustnrIemId.qustnrIemId,
                        qustnrQesitm.qestnSn,
                        qustnrQesitm.qestnTyCode,
                        qustnrQesitm.qestnCn,
                        qustnrQesitm.mxmmChoiseCo,
                        qustnrIem.iemSn,
                        qustnrIem.iemCn)
                .orderBy(qustnrQesitm.qestnSn.asc(),qustnrIem.iemSn.asc())
                .fetch();

        List<QustnrQesitmItemDTO> content = results.stream().map(tuple -> {
            QustnrQesitm qqm = tuple.get(qustnrQesitm);
            QustnrIem qri = tuple.get(qustnrIem);

            String qustnrIemId = null;
            String iemSn = null;
            String iemCn = null;

            if (qri != null && qri.getQustnrIemId() != null) {
                qustnrIemId = qri.getQustnrIemId().getQustnrIemId();
                iemSn = qri.getIemSn();
                iemCn = qri.getIemCn();
            }

            return new QustnrQesitmItemDTO(
                    Objects.requireNonNull(qqm).getQustnrQesitmId().getQustnrTmplatId(),
                    qqm.getQustnrQesitmId().getQestnrId(),
                    qqm.getQustnrQesitmId().getQustnrQesitmId(),
                    qustnrIemId,
                    qqm.getQestnSn(),
                    qqm.getQestnTyCode(),
                    qqm.getQestnCn(),
                    qqm.getMxmmChoiseCo(),
                    iemSn,
                    iemCn
            );
        }).collect(Collectors.toList());

        return content;
    }

    @Override
    public List<QustnrQesitmDTO> qustnrQesitmList(QustnrRspnsResultVO qustnrRspnsResultVO) {

        QQustnrQesitm qustnrQesitm = QQustnrQesitm.qustnrQesitm;

        List<QustnrQesitm> results = queryFactory.select(qustnrQesitm)
                .from(qustnrQesitm)
                .where(qustnrQesitm.qustnrQesitmId.qustnrTmplatId.eq(qustnrRspnsResultVO.getQustnrTmplatId())
                        .and(qustnrQesitm.qustnrQesitmId.qestnrId.eq(qustnrRspnsResultVO.getQestnrId())))
                .orderBy(qustnrQesitm.qestnSn.asc())
                .fetch();

        List<QustnrQesitmDTO> content = results.stream().map(qqm -> {
            return new QustnrQesitmDTO(
                qqm.getQustnrQesitmId().getQustnrTmplatId(),
                qqm.getQustnrQesitmId().getQestnrId(),
                qqm.getQustnrQesitmId().getQustnrQesitmId(),
                qqm.getQestnTyCode(),
                qqm.getQestnSn(),
                qqm.getQestnCn(),
                qqm.getMxmmChoiseCo(),
                qqm.getFrstRegistPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                qqm.getFrstRegisterId(),
                qqm.getLastUpdtPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                qqm.getLastUpdusrId()
            );
        }).collect(Collectors.toList());;
        return content;
    }

}
