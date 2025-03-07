package egovframework.com.uss.olp.qmc.service;

import java.util.List;

public interface EgovQustnrRspnsResultService {

    List<QustnrRspnsResultMCStatsDTO> qustnrRspnsResultMCStats(QestnrInfoVO qestnrInfoVO);

    List<QustnrRspnsResultESStatsDTO> qustnrRspnsResultESStats(QestnrInfoVO qestnrInfoVO);

}
