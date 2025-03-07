package egovframework.com.sym.ccm.cde.service.impl;

import egovframework.com.sym.ccm.cde.repository.EgovCmmnCodeRepository;
import egovframework.com.sym.ccm.cde.service.CmmnCodeVO;
import egovframework.com.sym.ccm.cde.service.EgovCmmnCodeService;
import egovframework.com.sym.ccm.cde.util.EgovCmmnDetailCodeUtility;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("cdeEgovCmmnCodeServiceImpl")
@RequiredArgsConstructor
public class EgovCmmnCodeServiceImpl extends EgovAbstractServiceImpl implements EgovCmmnCodeService {

    private final EgovCmmnCodeRepository repository;

    @Override
    public List<CmmnCodeVO> list(String clCode) {
        return repository.findAllByClCode(clCode)
                .stream()
                .map(EgovCmmnDetailCodeUtility::codeEntityToVO)
                .collect(Collectors.toList());
    }

}
