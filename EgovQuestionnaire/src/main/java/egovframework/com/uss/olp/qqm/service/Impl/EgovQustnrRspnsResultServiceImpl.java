package egovframework.com.uss.olp.qqm.service.Impl;

import egovframework.com.uss.olp.qqm.repository.EgovQustnrRspnsResultRepository;
import egovframework.com.uss.olp.qqm.service.EgovQustnrRspnsResultService;
import egovframework.com.uss.olp.qqm.service.QustnrQesitmVO;
import egovframework.com.uss.olp.qqm.service.QustnrRspnsResultESStatsDTO;
import egovframework.com.uss.olp.qqm.service.QustnrRspnsResultMCStatsDTO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("qqmEgovQustnrRspnsResultService")
@RequiredArgsConstructor
public class EgovQustnrRspnsResultServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrRspnsResultService {

    private final EgovQustnrRspnsResultRepository repository;

    @Override
    public List<QustnrRspnsResultMCStatsDTO> qustnrRspnsResultMCStats(QustnrQesitmVO qustnrQesitmVO) {
        return repository.qustnrRspnsResultMCStats(qustnrQesitmVO.getQustnrTmplatId(), qustnrQesitmVO.getQestnrId());
    }

    @Override
    public List<QustnrRspnsResultESStatsDTO> qustnrRspnsResultESStats(QustnrQesitmVO qustnrQesitmVO) {
        return repository.qustnrRspnsResultESStats(qustnrQesitmVO.getQustnrTmplatId(), qustnrQesitmVO.getQestnrId());
    }

}
