package egovframework.com.uss.olp.qri.service.Impl;

import egovframework.com.uss.olp.qri.repository.EgovQestnrInfoRepository;
import egovframework.com.uss.olp.qri.service.EgovQestnrInfoService;
import egovframework.com.uss.olp.qri.service.QestnrInfoDTO;
import egovframework.com.uss.olp.qri.service.QustnrRspnsResultVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

@Service("qriEgovQestnrInfoService")
@RequiredArgsConstructor
public class EgovQestnrInfoServiceImpl extends EgovAbstractServiceImpl implements EgovQestnrInfoService {

    private final EgovQestnrInfoRepository repository;

    @Override
    public QestnrInfoDTO detail(QustnrRspnsResultVO qustnrRspnsResultVO) {
        return repository.qestnrInfoDetail(qustnrRspnsResultVO.getQustnrTmplatId(), qustnrRspnsResultVO.getQestnrId());
    }

}
