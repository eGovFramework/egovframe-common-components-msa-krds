package egovframework.com.cop.cmy.util;

import egovframework.com.cop.cmy.entity.*;
import egovframework.com.cop.cmy.service.BbsMasterOptnVO;
import egovframework.com.cop.cmy.service.BbsMasterVO;
import egovframework.com.cop.cmy.service.CommunityUserVO;
import egovframework.com.cop.cmy.service.CommunityVO;
import org.springframework.beans.BeanUtils;

public class EgovCommunityUtility {

    public static CommunityVO cmmntyEntityToVO(Cmmnty cmmnty) {
        CommunityVO communityVO = new CommunityVO();
        BeanUtils.copyProperties(cmmnty, communityVO);
        return communityVO;
    }

    public static Cmmnty communityVOToEntity(CommunityVO communityVO) {
        Cmmnty cmmnty = new Cmmnty();
        BeanUtils.copyProperties(communityVO, cmmnty);
        return cmmnty;
    }

    public static CommunityUserVO cmmntyUserEntityToVO(CmmntyUser cmmntyUser) {
        CommunityUserVO communityUserVO = new CommunityUserVO();
        communityUserVO.setCmmntyId(cmmntyUser.getCmmntyUserId().getCmmntyId());
        communityUserVO.setEmplyrId(cmmntyUser.getCmmntyUserId().getEmplyrId());
        communityUserVO.setMngrAt(cmmntyUser.getMngrAt());
        communityUserVO.setMberSttus(cmmntyUser.getMberSttus());
        communityUserVO.setSbscrbDe(cmmntyUser.getSbscrbDe());
        communityUserVO.setSecsnDe(cmmntyUser.getSecsnDe());
        communityUserVO.setUseAt(cmmntyUser.getUseAt());
        communityUserVO.setFrstRegistPnttm(cmmntyUser.getFrstRegistPnttm());
        communityUserVO.setFrstRegisterId(cmmntyUser.getFrstRegisterId());
        communityUserVO.setLastUpdtPnttm(cmmntyUser.getLastUpdtPnttm());
        communityUserVO.setLastUpdusrId(cmmntyUser.getLastUpdusrId());
        return communityUserVO;
    }

    public static CmmntyUser communityUsereVOToEntity(CommunityUserVO communityUserVO) {
        CmmntyUserId cmmntyUserId = new CmmntyUserId();
        cmmntyUserId.setCmmntyId(communityUserVO.getCmmntyId());
        cmmntyUserId.setEmplyrId(communityUserVO.getEmplyrId());

        CmmntyUser cmmntyUser = new CmmntyUser();
        cmmntyUser.setCmmntyUserId(cmmntyUserId);
        cmmntyUser.setMngrAt(communityUserVO.getMngrAt());
        cmmntyUser.setMberSttus(communityUserVO.getMberSttus());
        cmmntyUser.setSbscrbDe(communityUserVO.getSbscrbDe());
        cmmntyUser.setSecsnDe(communityUserVO.getSecsnDe());
        cmmntyUser.setUseAt(communityUserVO.getUseAt());
        cmmntyUser.setFrstRegistPnttm(communityUserVO.getFrstRegistPnttm());
        cmmntyUser.setFrstRegisterId(communityUserVO.getFrstRegisterId());
        cmmntyUser.setLastUpdtPnttm(communityUserVO.getLastUpdtPnttm());
        cmmntyUser.setLastUpdusrId(communityUserVO.getLastUpdusrId());
        return cmmntyUser;
    }

	public static BbsMasterVO bbsMasterEntityTOVO(BbsMaster bbsMaster) {
        BbsMasterVO bbsMasterVO = new BbsMasterVO();
        BeanUtils.copyProperties(bbsMaster, bbsMasterVO);
        return bbsMasterVO;
    }

    public static BbsMaster bbsMasterVOTOEntity(BbsMasterVO bbsMasterVO) {
        BbsMaster bbsMaster = new BbsMaster();
        BeanUtils.copyProperties(bbsMasterVO, bbsMaster);
        return bbsMaster;
    }

    public static BbsMasterOptn bbsMasterOptnVOEntity(BbsMasterOptnVO bbsMasterOptnVO) {
        BbsMasterOptn bbsMaster = new BbsMasterOptn();
        BeanUtils.copyProperties(bbsMasterOptnVO, bbsMaster);
        return bbsMaster;
    }

}
