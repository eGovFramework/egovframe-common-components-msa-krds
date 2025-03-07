package egovframework.com.uss.olp.qqm.service.Impl;

import egovframework.com.uss.olp.qqm.repository.EgovCmmnDetailCodeRepository;
import egovframework.com.uss.olp.qqm.service.CmmnDetailCodeVO;
import egovframework.com.uss.olp.qqm.service.EgovCmmnDetailCodeService;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("qqmEgovCmmnDetailCodeService")
@RequiredArgsConstructor
public class EgovCmmnDetailCodeServiceImpl extends EgovAbstractServiceImpl implements EgovCmmnDetailCodeService {

    private final EgovCmmnDetailCodeRepository repository;

    @Override
    public List<CmmnDetailCodeVO> list(String codeId) {
        return repository.findByCmmnDetailCodeIdCodeId(codeId)
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
