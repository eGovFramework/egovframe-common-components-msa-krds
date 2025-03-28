package egovframework.com.uss.olp.qtm.service;

import org.springframework.data.domain.Page;

import java.util.Map;

public interface EgovQustnrTmplatService {

    Page<QustnrTmplatDTO> list(QustnrTmplatVO qustnrTmplatVO);

    QustnrTmplatDTO detail(QustnrTmplatVO qustnrTmplatVO);

    QustnrTmplatVO insert(QustnrTmplatVO qustnrTmplatVO, Map<String, String> userInfo);

    QustnrTmplatVO update(QustnrTmplatVO qustnrTmplatVO, Map<String, String> userInfo);

    boolean delete(QustnrTmplatVO qustnrTmplatVO);

    byte[] getImage(String qustnrTmplatId);

}
