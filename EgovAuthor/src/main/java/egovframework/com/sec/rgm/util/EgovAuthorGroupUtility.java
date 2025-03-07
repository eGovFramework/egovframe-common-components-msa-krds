package egovframework.com.sec.rgm.util;

import egovframework.com.sec.rgm.entity.AuthorGroupInfo;
import egovframework.com.sec.rgm.entity.AuthorInfo;
import egovframework.com.sec.rgm.entity.Emplyrscrtyestbs;
import egovframework.com.sec.rgm.service.AuthorGroupInfoVO;
import egovframework.com.sec.rgm.service.AuthorGroupVO;
import egovframework.com.sec.rgm.service.AuthorInfoVO;
import org.springframework.beans.BeanUtils;

public class EgovAuthorGroupUtility {

    public static AuthorGroupVO authorGroupEntityToVO(Emplyrscrtyestbs emplyrscrtyestbs) {
        AuthorGroupVO authorGroupVO = new AuthorGroupVO();
        BeanUtils.copyProperties(emplyrscrtyestbs, authorGroupVO);
        return authorGroupVO;
    }

    public static Emplyrscrtyestbs authorGroupVOToEntity(AuthorGroupVO authorGroupVO) {
        Emplyrscrtyestbs emplyrscrtyestbs = new Emplyrscrtyestbs();
        BeanUtils.copyProperties(authorGroupVO, emplyrscrtyestbs);
        return emplyrscrtyestbs;
    }

    public static AuthorInfoVO authorInfoEntityToVO(AuthorInfo authorInfo) {
        AuthorInfoVO authorInfoVO = new AuthorInfoVO();
        BeanUtils.copyProperties(authorInfo, authorInfoVO);
        return authorInfoVO;
    }

    public static AuthorInfo authorInfoVOToEntity(AuthorInfoVO authorInfoVO) {
        AuthorInfo authorInfo = new AuthorInfo();
        BeanUtils.copyProperties(authorInfoVO, authorInfo);
        return authorInfo;
    }

    public static AuthorGroupInfoVO authorGroupInfoEntityToVO(AuthorGroupInfo authorGroupInfo) {
        AuthorGroupInfoVO authorGroupInfoVO = new AuthorGroupInfoVO();
        BeanUtils.copyProperties(authorGroupInfo, authorGroupInfoVO);
        return authorGroupInfoVO;
    }

    public static AuthorGroupInfo authorGroupInfoVOToEntity(AuthorGroupInfoVO authorGroupInfoVO) {
        AuthorGroupInfo authorGroupInfo = new AuthorGroupInfo();
        BeanUtils.copyProperties(authorGroupInfoVO, authorGroupInfo);
        return authorGroupInfo;
    }

}
