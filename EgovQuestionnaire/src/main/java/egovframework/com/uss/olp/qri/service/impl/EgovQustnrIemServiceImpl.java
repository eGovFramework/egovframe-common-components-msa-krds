package egovframework.com.uss.olp.qri.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.com.uss.olp.qri.entity.QQustnrIem;
import egovframework.com.uss.olp.qri.entity.QustnrIem;
import egovframework.com.uss.olp.qri.repository.EgovQustnrIemRepository;
import egovframework.com.uss.olp.qri.service.EgovQustnrIemService;
import egovframework.com.uss.olp.qri.service.QustnrIemDTO;
import egovframework.com.uss.olp.qri.service.QustnrRspnsResultVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service("qriEgovQustnrIemService")
@RequiredArgsConstructor
public class EgovQustnrIemServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrIemService {

    private final EgovQustnrIemRepository repository;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<QustnrIemDTO> qustnrIemList(QustnrRspnsResultVO qustnrRspnsResultVO) {

        QQustnrIem qustnrIem = QQustnrIem.qustnrIem;

        List<QustnrIem> results = queryFactory.select(qustnrIem)
                .from(qustnrIem)
                .where(qustnrIem.qustnrIemId.qustnrTmplatId.eq(qustnrRspnsResultVO.getQustnrTmplatId())
                        .and(qustnrIem.qustnrIemId.qestnrId.eq(qustnrRspnsResultVO.getQestnrId())))
                .orderBy(qustnrIem.qustnrIemId.qustnrQesitmId.asc(),qustnrIem.iemSn.asc())
                .fetch();

        List<QustnrIemDTO> content = results.stream().map(qri -> {
            return new QustnrIemDTO(
                    qri.getQustnrIemId().getQustnrTmplatId(),
                    qri.getQustnrIemId().getQestnrId(),
                    qri.getQustnrIemId().getQustnrQesitmId(),
                    qri.getQustnrIemId().getQustnrIemId(),
                    qri.getIemSn(),
                    qri.getIemCn(),
                    qri.getEtcAnswerAt(),
                    qri.getFrstRegistPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    qri.getFrstRegisterId(),
                    qri.getLastUpdtPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    qri.getLastUpdusrId()
            );
        }).collect(Collectors.toList());
        return content;
    }

}
