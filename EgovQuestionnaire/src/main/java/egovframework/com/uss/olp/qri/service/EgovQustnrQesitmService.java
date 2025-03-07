package egovframework.com.uss.olp.qri.service;

import java.util.List;

public interface EgovQustnrQesitmService {

    List<QustnrQesitmItemDTO> qustnrQesitmItemList(QustnrRspnsResultVO qustnrRspnsResultVO);

    List<QustnrQesitmDTO> qustnrQesitmList(QustnrRspnsResultVO qustnrRspnsResultVO);

}
