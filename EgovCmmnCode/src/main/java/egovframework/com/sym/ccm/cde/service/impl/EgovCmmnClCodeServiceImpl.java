package egovframework.com.sym.ccm.cde.service.impl;

import egovframework.com.sym.ccm.cde.repository.EgovCmmnClCodeRepository;
import egovframework.com.sym.ccm.cde.service.CmmnClCodeVO;
import egovframework.com.sym.ccm.cde.service.EgovCmmnClCodeService;
import egovframework.com.sym.ccm.cde.util.EgovCmmnDetailCodeUtility;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("cdeEgovCmmnClCodeServiceImpl")
@RequiredArgsConstructor
public class EgovCmmnClCodeServiceImpl extends EgovAbstractServiceImpl implements EgovCmmnClCodeService {

    private final EgovCmmnClCodeRepository repository;

    @Override
    public List<CmmnClCodeVO> list() {
        return repository.findAll()
                .stream()
                .map(EgovCmmnDetailCodeUtility::clCodeEntityTOVO)
                .collect(Collectors.toList());
    }

}
