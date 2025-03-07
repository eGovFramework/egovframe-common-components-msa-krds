package egovframework.com.uss.olp.qri.service.Impl;

import egovframework.com.uss.olp.qri.repository.EgovQustnrIemRepository;
import egovframework.com.uss.olp.qri.service.EgovQustnrIemService;
import egovframework.com.uss.olp.qri.service.QustnrIemDTO;
import egovframework.com.uss.olp.qri.service.QustnrRspnsResultVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("qriEgovQustnrIemService")
@RequiredArgsConstructor
public class EgovQustnrIemServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrIemService {

    private final EgovQustnrIemRepository repository;

    @Override
    public List<QustnrIemDTO> qustnrIemList(QustnrRspnsResultVO qustnrRspnsResultVO) {
        return repository.qustnrIemList(qustnrRspnsResultVO.getQustnrTmplatId(), qustnrRspnsResultVO.getQestnrId());
    }

}
