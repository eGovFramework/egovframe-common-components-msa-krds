package egovframework.com.uss.olp.qmc.service;

import org.springframework.data.domain.Page;

public interface EgovQustnrRespondInfoService {

    Page<QustnrRespondInfoDTO> list(QustnrRespondInfoVO qustnrRespondInfoVO);

}
