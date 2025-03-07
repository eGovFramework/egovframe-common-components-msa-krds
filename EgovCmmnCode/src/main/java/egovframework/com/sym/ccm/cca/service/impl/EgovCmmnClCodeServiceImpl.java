package egovframework.com.sym.ccm.cca.service.impl;

import egovframework.com.sym.ccm.cca.reposiroty.EgovCmmnClCodeRepository;
import egovframework.com.sym.ccm.cca.service.CmmnClCodeVO;
import egovframework.com.sym.ccm.cca.service.EgovCmmnClCodeService;
import egovframework.com.sym.ccm.cca.util.EgovCmmnCodeUtility;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("ccaEgovCmmnClCodeServiceImpl")
@RequiredArgsConstructor
public class EgovCmmnClCodeServiceImpl extends EgovAbstractServiceImpl implements EgovCmmnClCodeService {

    private final EgovCmmnClCodeRepository repository;

    @Override
    public List<CmmnClCodeVO> list() {
        return repository.findAllByUseAtEquals("Y")
                .stream()
                .map(EgovCmmnCodeUtility::CmmnClCodeEntityToVO)
                .collect(Collectors.toList());
    }

}
