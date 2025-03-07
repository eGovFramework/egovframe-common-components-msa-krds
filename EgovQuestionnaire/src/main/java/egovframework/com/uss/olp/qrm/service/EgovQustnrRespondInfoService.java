package egovframework.com.uss.olp.qrm.service;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.springframework.data.domain.Page;

public interface EgovQustnrRespondInfoService {

    Page<QustnrRespondInfoDTO> list(QustnrRespondInfoVO qustnrRespondInfoVO);

    QustnrRespondInfoDTO detail(QustnrRespondInfoVO qustnrRespondInfoVO);

    QustnrRespondInfoVO insert(QustnrRespondInfoVO qustnrRespondInfoVO) throws FdlException;

    QustnrRespondInfoVO update(QustnrRespondInfoVO qustnrRespondInfoVO);

    boolean delete(QustnrRespondInfoVO qustnrRespondInfoVO);

}
