package egovframework.com.uss.olp.qim.service.Impl;

import egovframework.com.uss.olp.qim.repository.EgovQustnrQesitmRepository;
import egovframework.com.uss.olp.qim.service.EgovQustnrQesitmService;
import egovframework.com.uss.olp.qim.service.QustnrQesitmDTO;
import egovframework.com.uss.olp.qim.service.QustnrQesitmVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("qimEgovQustnrQesitmService")
@RequiredArgsConstructor
public class EgovQustnrQesitmServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrQesitmService {

    private final EgovQustnrQesitmRepository repository;

    @Override
    public Page<QustnrQesitmDTO> list(QustnrQesitmVO qustnrQesitmVO) {
        Pageable pageable = PageRequest.of(qustnrQesitmVO.getFirstIndex(), qustnrQesitmVO.getRecordCountPerPage());
        String searchCondition = qustnrQesitmVO.getSearchCondition();
        String searchKeyword = qustnrQesitmVO.getSearchKeyword();
        String qustnrTmplatId = qustnrQesitmVO.getQustnrTmplatId();
        String qestnrId = qustnrQesitmVO.getQestnrId();
        return repository.qustnrQesitmList(searchCondition, searchKeyword, qustnrTmplatId, qestnrId, pageable);
    }

}
