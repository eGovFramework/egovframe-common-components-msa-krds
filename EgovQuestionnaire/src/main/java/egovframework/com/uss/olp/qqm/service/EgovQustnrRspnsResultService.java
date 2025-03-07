package egovframework.com.uss.olp.qqm.service;

import java.util.List;

public interface EgovQustnrRspnsResultService {

    List<QustnrRspnsResultMCStatsDTO> qustnrRspnsResultMCStats(QustnrQesitmVO qustnrQesitmVO);

    List<QustnrRspnsResultESStatsDTO> qustnrRspnsResultESStats(QustnrQesitmVO qustnrQesitmVO);

}
