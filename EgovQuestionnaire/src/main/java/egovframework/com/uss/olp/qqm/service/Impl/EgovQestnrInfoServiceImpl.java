package egovframework.com.uss.olp.qqm.service.Impl;

import egovframework.com.uss.olp.qqm.repository.EgovQestnrInfoRepository;
import egovframework.com.uss.olp.qqm.service.EgovQestnrInfoService;
import egovframework.com.uss.olp.qqm.service.QestnrInfoDTO;
import egovframework.com.uss.olp.qqm.service.QestnrInfoVO;
import egovframework.com.uss.olp.qqm.service.QustnrQesitmVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("qqmEgovQestnrInfoService")
@RequiredArgsConstructor
public class EgovQestnrInfoServiceImpl extends EgovAbstractServiceImpl implements EgovQestnrInfoService {

    private final EgovQestnrInfoRepository repository;

    @Override
    public Page<QestnrInfoDTO> list(QestnrInfoVO qestnrInfoVO) {
        Pageable pageable = PageRequest.of(qestnrInfoVO.getFirstIndex(), qestnrInfoVO.getRecordCountPerPage());
        String searchCondition = qestnrInfoVO.getSearchCondition();
        String searchKeyword = qestnrInfoVO.getSearchKeyword();
        return repository.qestnrInfoList(searchCondition, searchKeyword, pageable);
    }

    @Override
    public QestnrInfoDTO detail(QustnrQesitmVO qustnrQesitmVO) {
        return repository.qestnrInfoDetail(qustnrQesitmVO.getQustnrTmplatId(), qustnrQesitmVO.getQestnrId());
    }

}
