package egovframework.com.uss.olp.qrm.service;

import org.springframework.data.domain.Page;

public interface EgovQustnrRespondInfoService {

    Page<QustnrRespondInfoDTO> list(QustnrRespondInfoVO qustnrRespondInfoVO);

    QustnrRespondInfoDTO detail(QustnrRespondInfoVO qustnrRespondInfoVO);

    QustnrRespondInfoVO insert(QustnrRespondInfoVO qustnrRespondInfoVO);

    QustnrRespondInfoVO update(QustnrRespondInfoVO qustnrRespondInfoVO);

    boolean delete(QustnrRespondInfoVO qustnrRespondInfoVO);

}
