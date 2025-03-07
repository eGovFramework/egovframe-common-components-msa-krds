package egovframework.com.uss.olp.qqm.service;

import org.springframework.data.domain.Page;

import java.util.List;

public interface EgovQustnrItemService {

    Page<QustnrIemDTO> list(QustnrIemVO qustnrIemVO);

    List<QustnrIemDTO> qustnrIemList(QustnrQesitmVO qustnrQesitmVO);

}
