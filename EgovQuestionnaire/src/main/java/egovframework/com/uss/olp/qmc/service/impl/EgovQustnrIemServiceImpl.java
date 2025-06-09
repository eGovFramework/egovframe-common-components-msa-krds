package egovframework.com.uss.olp.qmc.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.com.uss.olp.qmc.entity.QQustnrIem;
import egovframework.com.uss.olp.qmc.entity.QustnrIem;
import egovframework.com.uss.olp.qmc.repository.EgovQustnrIemRepository;
import egovframework.com.uss.olp.qmc.service.EgovQustnrIemService;
import egovframework.com.uss.olp.qmc.service.QestnrInfoVO;
import egovframework.com.uss.olp.qmc.service.QustnrIemDTO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service("qmcEgovQustnrIemService")
@RequiredArgsConstructor
public class EgovQustnrIemServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrIemService {

    private final EgovQustnrIemRepository repository;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<QustnrIemDTO> qustnrIemList(QestnrInfoVO qestnrInfoVO) {

        QQustnrIem qustnrIem = QQustnrIem.qustnrIem;

        List<QustnrIem> query =  queryFactory
                .select(qustnrIem)
                .from(qustnrIem)
                .where(qustnrIem.qustnrIemId.qustnrTmplatId.eq(qestnrInfoVO.getQustnrTmplatId())
                        .and(qustnrIem.qustnrIemId.qestnrId.eq(qestnrInfoVO.getQestnrId())))
                .orderBy(qustnrIem.qustnrIemId.qustnrQesitmId.asc(),qustnrIem.iemSn.asc())
                .fetch();

        List<QustnrIemDTO> content = query.stream().map(result -> {
            return new QustnrIemDTO(
                result.getQustnrIemId().getQustnrTmplatId(),
                result.getQustnrIemId().getQestnrId(),
                result.getQustnrIemId().getQustnrQesitmId(),
                result.getQustnrIemId().getQustnrIemId(),
                result.getIemSn(),
                result.getIemCn(),
                result.getEtcAnswerAt(),
                result.getFrstRegistPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                result.getFrstRegisterId(),
                result.getLastUpdtPnttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                result.getLastUpdusrId()
            );
        }).collect(Collectors.toList());

        return content;
    }

}
