package egovframework.com.uss.olp.qim.service;

import org.springframework.data.domain.Page;

public interface EgovQustnrQesitmService {

    Page<QustnrQesitmDTO> list(QustnrQesitmVO qustnrQesitmVO);

}
