package egovframework.com.uss.olp.qim.service;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface EgovQustnrItemService {

    Page<QustnrIemDTO> list(QustnrIemVO qustnrIemVO);

    QustnrIemDTO detail(QustnrIemVO qustnrIemVO);

    QustnrIemVO insert(QustnrIemVO qustnrIemVO, Map<String, String> userInfo) throws FdlException;

    QustnrIemVO update(QustnrIemVO qustnrIemVO, Map<String, String> userInfo);

    boolean delete(QustnrIemVO qustnrIemVO);

}
