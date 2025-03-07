package egovframework.com.uss.olp.qtm.service;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.Map;

public interface EgovQustnrTmplatService {

    Page<QustnrTmplatDTO> list(QustnrTmplatVO qustnrTmplatVO);

    QustnrTmplatDTO detail(QustnrTmplatVO qustnrTmplatVO);

    QustnrTmplatVO insert(QustnrTmplatVO qustnrTmplatVO, Map<String, String> userInfo) throws FdlException, IOException;

    QustnrTmplatVO update(QustnrTmplatVO qustnrTmplatVO, Map<String, String> userInfo) throws FdlException, IOException;

    boolean delete(QustnrTmplatVO qustnrTmplatVO);

    byte[] getImage(String qustnrTmplatId);

}
