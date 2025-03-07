package egovframework.com.uss.olp.qmc.service.Impl;

import egovframework.com.uss.olp.qmc.repository.EgovQustnrRespondInfoRepository;
import egovframework.com.uss.olp.qmc.service.EgovQustnrRespondInfoService;
import egovframework.com.uss.olp.qmc.service.QustnrRespondInfoDTO;
import egovframework.com.uss.olp.qmc.service.QustnrRespondInfoVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("qmcEgovQustnrRespondInfoService")
@RequiredArgsConstructor
public class EgovQustnrRespondInfoServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrRespondInfoService {

    private final EgovQustnrRespondInfoRepository repository;

    @Override
    public Page<QustnrRespondInfoDTO> list(QustnrRespondInfoVO qustnrRespondInfoVO) {
        Pageable pageable = PageRequest.of(qustnrRespondInfoVO.getFirstIndex(), qustnrRespondInfoVO.getRecordCountPerPage());
        String qustnrTmplatId = qustnrRespondInfoVO.getQustnrTmplatId();
        String qestnrId = qustnrRespondInfoVO.getQestnrId();
        return repository.qustnrRespondInfoList(qustnrTmplatId, qestnrId, pageable);
    }

}
