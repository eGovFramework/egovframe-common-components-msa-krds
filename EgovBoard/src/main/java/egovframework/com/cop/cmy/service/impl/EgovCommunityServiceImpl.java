package egovframework.com.cop.cmy.service.impl;

import egovframework.com.cop.cmy.entity.Cmmnty;
import egovframework.com.cop.cmy.repository.EgovCommunityRepository;
import egovframework.com.cop.cmy.repository.EgovCommunityUserRepository;
import egovframework.com.cop.cmy.service.CommunityDTO;
import egovframework.com.cop.cmy.service.CommunityUserVO;
import egovframework.com.cop.cmy.service.CommunityVO;
import egovframework.com.cop.cmy.service.EgovCommunityService;
import egovframework.com.cop.cmy.util.EgovCommunityUtility;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service("cmyEgovCommunityServiceImpl")
public class EgovCommunityServiceImpl extends EgovAbstractServiceImpl implements EgovCommunityService {

    private final EgovCommunityRepository repository;
    private final EgovCommunityUserRepository userRepository;
    private final EgovIdGnrService idgenService;

    public EgovCommunityServiceImpl(
            EgovCommunityRepository repository,
            EgovCommunityUserRepository userRepository,
            @Qualifier("egovCmmntyIdGnrService") EgovIdGnrService idgenService
    ) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.idgenService = idgenService;
    }

    @Override
    public Page<CommunityDTO> list(CommunityVO communityVO) {
        Pageable pageable = PageRequest.of(communityVO.getFirstIndex(), communityVO.getRecordCountPerPage());
        String searchCondition = communityVO.getSearchCondition();
        String searchKeyword = communityVO.getSearchKeyword();
        return repository.communityList(searchCondition, searchKeyword, pageable);
    }

    @Override
    public CommunityDTO detail(CommunityVO communityVO) {
        return repository.communityDetail(communityVO.getCmmntyId());
    }

    @Transactional
    @Override
    public CommunityVO insert(CommunityVO communityVO, Map<String, String> userInfo) throws FdlException {
        String cmmntyId = idgenService.getNextStringId();

        communityVO.setCmmntyId(cmmntyId);
        communityVO.setRegistSeCode("REGC02");
        communityVO.setFrstRegistPnttm(LocalDateTime.now());
        communityVO.setFrstRegisterId(userInfo.get("uniqId"));
        communityVO.setLastUpdtPnttm(LocalDateTime.now());
        communityVO.setLastUpdusrId(userInfo.get("uniqId"));
        Cmmnty cmmnty = repository.save(EgovCommunityUtility.communityVOToEntity(communityVO));

        CommunityUserVO communityUserVO = getCommunityUserVO(cmmntyId, userInfo.get("uniqId"));
        userRepository.save(EgovCommunityUtility.communityUsereVOToEntity(communityUserVO));

        return EgovCommunityUtility.cmmntyEntityToVO(cmmnty);
    }

    @Transactional
    @Override
    public CommunityVO update(CommunityVO communityVO, Map<String, String> userInfo) {
        return repository.findById(communityVO.getCmmntyId())
                .map(result -> {
                    result.setCmmntyNm(communityVO.getCmmntyNm());
                    result.setCmmntyIntrcn(communityVO.getCmmntyIntrcn());
                    result.setLastUpdtPnttm(LocalDateTime.now());
                    result.setLastUpdusrId(userInfo.get("uniqId"));
                    return repository.save(result);
                })
                .map(EgovCommunityUtility::cmmntyEntityToVO).orElse(null);
    }

    private CommunityUserVO getCommunityUserVO(String cmmntyId, String uniqId) {
        CommunityUserVO communityUserVO = new CommunityUserVO();
        communityUserVO.setCmmntyId(cmmntyId);
        communityUserVO.setEmplyrId(uniqId);
        communityUserVO.setMngrAt("Y");
        communityUserVO.setMberSttus("P");
        communityUserVO.setSbscrbDe(LocalDateTime.now());
        communityUserVO.setUseAt("Y");
        communityUserVO.setFrstRegistPnttm(LocalDateTime.now());
        communityUserVO.setFrstRegisterId(uniqId);
        communityUserVO.setLastUpdtPnttm(LocalDateTime.now());
        communityUserVO.setLastUpdusrId(uniqId);
        return communityUserVO;
    }

}
