package egovframework.com.uss.olp.qmc.service.Impl;

import egovframework.com.uss.olp.qmc.repository.EgovQustnrRspnsResultRepository;
import egovframework.com.uss.olp.qmc.service.EgovQustnrRspnsResultService;
import egovframework.com.uss.olp.qmc.service.QestnrInfoVO;
import egovframework.com.uss.olp.qmc.service.QustnrRspnsResultESStatsDTO;
import egovframework.com.uss.olp.qmc.service.QustnrRspnsResultMCStatsDTO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("qmcEgovQustnrRspnsResultService")
@RequiredArgsConstructor
public class EgovQustnrRspnsResultServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrRspnsResultService {

    private final EgovQustnrRspnsResultRepository repository;

    @Override
    public List<QustnrRspnsResultMCStatsDTO> qustnrRspnsResultMCStats(QestnrInfoVO qestnrInfoVO) {
        return repository.qustnrRspnsResultMCStats(qestnrInfoVO.getQustnrTmplatId(), qestnrInfoVO.getQestnrId());
    }

    @Override
    public List<QustnrRspnsResultESStatsDTO> qustnrRspnsResultESStats(QestnrInfoVO qestnrInfoVO) {
        return repository.qustnrRspnsResultESStats(qestnrInfoVO.getQustnrTmplatId(), qestnrInfoVO.getQestnrId());
    }

}
