package egovframework.com.uss.olp.qmc.service.Impl;

import egovframework.com.uss.olp.qmc.repository.EgovQustnrIemRepository;
import egovframework.com.uss.olp.qmc.service.EgovQustnrIemService;
import egovframework.com.uss.olp.qmc.service.QestnrInfoVO;
import egovframework.com.uss.olp.qmc.service.QustnrIemDTO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("qmcEgovQustnrIemService")
@RequiredArgsConstructor
public class EgovQustnrIemServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrIemService {

    private final EgovQustnrIemRepository repository;

    @Override
    public List<QustnrIemDTO> qustnrIemList(QestnrInfoVO qestnrInfoVO) {
        return repository.qustnrIemList(qestnrInfoVO.getQustnrTmplatId(), qestnrInfoVO.getQestnrId());
    }

}
