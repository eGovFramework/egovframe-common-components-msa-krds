package egovframework.com.uss.olp.qmc.service.Impl;

import egovframework.com.uss.olp.qmc.repository.EgovQustnrTmplatRepository;
import egovframework.com.uss.olp.qmc.service.EgovQustnrTmplatService;
import egovframework.com.uss.olp.qmc.service.QustnrTmplatVO;
import egovframework.com.uss.olp.qmc.util.EgovQestnrInfoUtility;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("qmcEgovQustnrTmplatService")
@RequiredArgsConstructor
public class EgovQustnrTmplatServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrTmplatService {

    private final EgovQustnrTmplatRepository repository;

    @Override
    public List<QustnrTmplatVO> list() {
        return repository.findAll().stream().map(EgovQestnrInfoUtility::QustnrTmplatEntityToVO).collect(Collectors.toList());
    }

    @Override
    public byte[] getImage(String qustnrTmplatId) {
        return repository.findById(qustnrTmplatId).get().getQustnrTmplatImageInfo();
    }

}
