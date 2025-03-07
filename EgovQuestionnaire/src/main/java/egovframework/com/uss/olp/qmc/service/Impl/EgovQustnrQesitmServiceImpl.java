package egovframework.com.uss.olp.qmc.service.Impl;

import egovframework.com.uss.olp.qmc.repository.EgovQustnrQesitmRepository;
import egovframework.com.uss.olp.qmc.service.EgovQustnrQesitmService;
import egovframework.com.uss.olp.qmc.service.QestnrInfoVO;
import egovframework.com.uss.olp.qmc.service.QustnrQesitmDTO;
import egovframework.com.uss.olp.qmc.service.QustnrQesitmVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("qmcEgovQustnrQesitmService")
@RequiredArgsConstructor
public class EgovQustnrQesitmServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrQesitmService {

    private final EgovQustnrQesitmRepository repository;

    @Override
    public Page<QustnrQesitmDTO> list(QustnrQesitmVO qustnrQesitmVO) {
        Pageable pageable = PageRequest.of(qustnrQesitmVO.getFirstIndex(), qustnrQesitmVO.getRecordCountPerPage());
        String qustnrTmplatId = qustnrQesitmVO.getQustnrTmplatId();
        String qestnrId = qustnrQesitmVO.getQestnrId();
        return repository.qustnrQesitmList(qustnrTmplatId, qestnrId, pageable);
    }

    @Override
    public List<QustnrQesitmDTO> qustnrQesitmList(QestnrInfoVO qestnrInfoVO) {
        return repository.qustnrQesitmList(qestnrInfoVO.getQustnrTmplatId(), qestnrInfoVO.getQestnrId());
    }

}
