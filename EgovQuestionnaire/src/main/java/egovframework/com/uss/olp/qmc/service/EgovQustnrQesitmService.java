package egovframework.com.uss.olp.qmc.service;

import org.springframework.data.domain.Page;

import java.util.List;

public interface EgovQustnrQesitmService {

    Page<QustnrQesitmDTO> list(QustnrQesitmVO qustnrQesitmVO);

    List<QustnrQesitmDTO> qustnrQesitmList(QestnrInfoVO qestnrInfoVO);

}
