package egovframework.com.uss.olp.qqm.service;

import org.springframework.data.domain.Page;

public interface EgovQestnrInfoService {

    Page<QestnrInfoDTO> list(QestnrInfoVO qestnrInfoVO);

    QestnrInfoDTO detail(QustnrQesitmVO qustnrQesitmVO);

}
