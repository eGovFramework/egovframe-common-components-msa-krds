package egovframework.com.uss.olp.qim.service;

import org.springframework.data.domain.Page;

public interface EgovQestnrInfoService {

    Page<QestnrInfoDTO> list(QestnrInfoVO qestnrInfoVO);

}
