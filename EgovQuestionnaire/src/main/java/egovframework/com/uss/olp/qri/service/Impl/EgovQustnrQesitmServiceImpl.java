package egovframework.com.uss.olp.qri.service.Impl;

import egovframework.com.uss.olp.qri.repository.EgovQustnrQesitmRepository;
import egovframework.com.uss.olp.qri.service.EgovQustnrQesitmService;
import egovframework.com.uss.olp.qri.service.QustnrQesitmDTO;
import egovframework.com.uss.olp.qri.service.QustnrQesitmItemDTO;
import egovframework.com.uss.olp.qri.service.QustnrRspnsResultVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("qriEgovQustnrQesitmService")
@RequiredArgsConstructor
public class EgovQustnrQesitmServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrQesitmService {

    private final EgovQustnrQesitmRepository repository;

    @Override
    public List<QustnrQesitmItemDTO> qustnrQesitmItemList(QustnrRspnsResultVO qustnrRspnsResultVO) {
        return repository.qustnrQesitmItemList(qustnrRspnsResultVO.getQustnrTmplatId(), qustnrRspnsResultVO.getQestnrId());
    }

    @Override
    public List<QustnrQesitmDTO> qustnrQesitmList(QustnrRspnsResultVO qustnrRspnsResultVO) {
        return repository.qustnrQesitmList(qustnrRspnsResultVO.getQustnrTmplatId(), qustnrRspnsResultVO.getQestnrId());
    }

}
