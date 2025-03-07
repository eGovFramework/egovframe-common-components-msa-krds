package egovframework.com.cop.brd.service.impl;

import egovframework.com.cop.brd.repository.EgovBbsMasterOptnRepository;
import egovframework.com.cop.brd.repository.EgovBbsMasterRepository;
import egovframework.com.cop.brd.service.BbsMasterDTO;
import egovframework.com.cop.brd.service.BbsMasterOptnVO;
import egovframework.com.cop.brd.service.BbsMasterVO;
import egovframework.com.cop.brd.service.EgovBbsMasterService;
import egovframework.com.cop.brd.util.EgovBoardUtility;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

@Service("brdEgovBbsMasterService")
@RequiredArgsConstructor
public class EgovBbsMasterServiceImpl extends EgovAbstractServiceImpl implements EgovBbsMasterService {

    private final EgovBbsMasterRepository repository;
    private final EgovBbsMasterOptnRepository optnRepository;

    @Override
    public BbsMasterDTO detail(BbsMasterVO bbsMasterVO) {
        return repository.bbsMasterDetail(bbsMasterVO.getBbsId());
    }

    @Override
    public BbsMasterOptnVO selectBBSMasterOptn(String bbsId) {
        if (optnRepository.findById(bbsId).isPresent()) {
            return EgovBoardUtility.bbsmasteroptnEntityToVO(optnRepository.findById(bbsId).get());
        } else {
            return null;
        }
    }

}
