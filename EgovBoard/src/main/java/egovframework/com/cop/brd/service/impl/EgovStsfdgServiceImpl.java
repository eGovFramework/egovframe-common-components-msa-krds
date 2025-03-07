package egovframework.com.cop.brd.service.impl;

import egovframework.com.cop.brd.repository.EgovStsfdgRepository;
import egovframework.com.cop.brd.service.EgovStsfdgService;
import egovframework.com.cop.brd.service.StsfdgVO;
import egovframework.com.cop.brd.util.EgovBoardUtility;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("brdEgovSatisfactionServiceImpl")
@Slf4j
public class EgovStsfdgServiceImpl extends EgovAbstractServiceImpl implements EgovStsfdgService {

    private final EgovStsfdgRepository egovStsfdgRepository;

    @Qualifier("egovStsfdgNoGnrService")
    private final EgovIdGnrService idgenServiceStsfdgNo;

    public EgovStsfdgServiceImpl(EgovStsfdgRepository egovStsfdgRepository, @Qualifier("egovStsfdgNoGnrService") EgovIdGnrService idgenServiceStsfdgNo) {
        this.egovStsfdgRepository = egovStsfdgRepository;
        this.idgenServiceStsfdgNo = idgenServiceStsfdgNo;
    }

    @Override
    public Map<String, Object> selectStsfdgList(StsfdgVO stsfdgVO) {

        Sort sort = Sort.by(Sort.Direction.DESC, "frstRegistPnttm");
        Pageable pageable = PageRequest.of(stsfdgVO.getFirstIndex(), stsfdgVO.getRecordCountPerPage(), sort);
        Page<StsfdgVO> sList = egovStsfdgRepository.findAllByBbsIdAndNttIdAndUseAt(stsfdgVO.getBbsId(), stsfdgVO.getNttId(), "Y", pageable).map(EgovBoardUtility::StsfdgEntiyToVO);
        List<StsfdgVO> cntList = egovStsfdgRepository.findAllByBbsIdAndNttIdAndUseAt(stsfdgVO.getBbsId(), stsfdgVO.getNttId(), "Y").stream().map(EgovBoardUtility::StsfdgEntiyToVO).collect(Collectors.toList());

        // 만족도 평균값
        double cnts = 0;
        for (StsfdgVO vo : cntList) {
            cnts += vo.getStsfdg();
        }
        double stsfdgAverage = (double) Math.round((cnts / cntList.size()) * 10) / 10;

        log.debug("총점 >> " + stsfdgAverage);

        Map<String, Object> response = new HashMap<>();
        response.put("sList", sList);
        response.put("stsfdgAverage", stsfdgAverage);

        return response;
    }

    @Override
    public int insertStsfdg(StsfdgVO stsfdgVO, Map<String, String> userInfo) throws FdlException {
        log.debug("별점 >> " + stsfdgVO.getStsfdg());
        if (stsfdgVO.getStsfdgNo().isEmpty()) {
            Long id = idgenServiceStsfdgNo.getNextLongId();
            stsfdgVO.setStsfdgNo(String.valueOf(id));
        }

        if (stsfdgVO.getFrstRegistPnttm() == null) {
            stsfdgVO.setFrstRegistPnttm(LocalDateTime.now());
        } else {
            stsfdgVO.setLastUpdusrPnttm(LocalDateTime.now());
        }

        stsfdgVO.setUseAt("Y");
        stsfdgVO.setWrterId(userInfo.get("uniqId"));
        stsfdgVO.setWrterNm(userInfo.get("userName"));
        stsfdgVO.setFrstRegisterId(userInfo.get("uniqId"));

        egovStsfdgRepository.save(EgovBoardUtility.satisfationVOToEntity(stsfdgVO));

        return 0;
    }

    @Override
    public int deleteStsfdg(String StsfdgNo) {
        log.debug("삭제할 번호 >> " + StsfdgNo);
        if (!StsfdgNo.isEmpty()) {
            egovStsfdgRepository.deleteById(StsfdgNo);
        }
        return 1;
    }
}
