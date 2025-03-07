package egovframework.com.uss.olp.qqm.service.Impl;

import egovframework.com.uss.olp.qqm.repository.EgovQustnrIemRepository;
import egovframework.com.uss.olp.qqm.service.EgovQustnrItemService;
import egovframework.com.uss.olp.qqm.service.QustnrIemDTO;
import egovframework.com.uss.olp.qqm.service.QustnrIemVO;
import egovframework.com.uss.olp.qqm.service.QustnrQesitmVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("qqmEgovQustnrItemService")
@RequiredArgsConstructor
public class EgovQustnrItemServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrItemService {

    private final EgovQustnrIemRepository repository;

    @Override
    public Page<QustnrIemDTO> list(QustnrIemVO qustnrIemVO) {
        Pageable pageable = PageRequest.of(qustnrIemVO.getFirstIndex(), qustnrIemVO.getRecordCountPerPage());
        String qustnrTmplatId = qustnrIemVO.getQustnrTmplatId();
        String qestnrId = qustnrIemVO.getQestnrId();
        String qustnrQesitmId = qustnrIemVO.getQustnrQesitmId();
        return repository.qustnrIemList(qustnrTmplatId, qestnrId, qustnrQesitmId, pageable);
    }

    @Override
    public List<QustnrIemDTO> qustnrIemList(QustnrQesitmVO qustnrQesitmVO) {
        return repository.qustnrIemList(qustnrQesitmVO.getQustnrTmplatId(), qustnrQesitmVO.getQestnrId());
    }

}
