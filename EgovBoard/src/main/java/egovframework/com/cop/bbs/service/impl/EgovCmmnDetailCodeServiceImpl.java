package egovframework.com.cop.bbs.service.impl;

import egovframework.com.cop.bbs.repository.EgovCmmnDetailCodeRepository;
import egovframework.com.cop.bbs.service.CmmnDetailCodeVO;
import egovframework.com.cop.bbs.service.EgovCmmnDetailCodeService;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("bbsEgovCmmnDetailCodeService")
@RequiredArgsConstructor
public class EgovCmmnDetailCodeServiceImpl extends EgovAbstractServiceImpl implements EgovCmmnDetailCodeService {

    private final EgovCmmnDetailCodeRepository repository;

    @Override
    public List<CmmnDetailCodeVO> list() {
        return repository.findByCmmnDetailCodeIdCodeId("COM101")
                .stream()
                .map(cmmnDetailCode -> {
                    CmmnDetailCodeVO cmmnDetailCodeVO = new CmmnDetailCodeVO();
                    cmmnDetailCodeVO.setCode(cmmnDetailCode.getCmmnDetailCodeId().getCode());
                    cmmnDetailCodeVO.setCodeNm(cmmnDetailCode.getCodeNm());
                    return cmmnDetailCodeVO;
                })
                .collect(Collectors.toList());
    }

}
