package egovframework.com.sec.ram.util;

import egovframework.com.sec.ram.entity.AuthorInfo;
import egovframework.com.sec.ram.entity.AuthorRoleRelated;
import egovframework.com.sec.ram.entity.AuthorRoleRelatedId;
import egovframework.com.sec.ram.service.AuthorInfoVO;
import egovframework.com.sec.ram.service.AuthorRoleRelatedVO;
import org.springframework.beans.BeanUtils;

public class EgovAuthorInfoUtility {

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

    public static AuthorRoleRelatedVO authorRoleRelatedEntityToVO(AuthorRoleRelated authorRoleRelated) {
        AuthorRoleRelatedVO authorRoleRelatedVO = new AuthorRoleRelatedVO();
        authorRoleRelatedVO.setAuthorCode(authorRoleRelated.getAuthorRoleRelatedId().getAuthorCode());
        authorRoleRelatedVO.setRoleCode(authorRoleRelated.getAuthorRoleRelatedId().getRoleCode());
        authorRoleRelatedVO.setCreatDt(authorRoleRelated.getCreatDt());
        return authorRoleRelatedVO;
    }

    public static AuthorRoleRelated authorRoleRelatedVOToEntity(AuthorRoleRelatedVO authorRoleRelatedVO) {
        AuthorRoleRelatedId authorRoleRelatedId = new AuthorRoleRelatedId();
        authorRoleRelatedId.setAuthorCode(authorRoleRelatedVO.getAuthorCode());
        authorRoleRelatedId.setRoleCode(authorRoleRelatedVO.getRoleCode());

        AuthorRoleRelated authorRoleRelated = new AuthorRoleRelated();
        authorRoleRelated.setAuthorRoleRelatedId(authorRoleRelatedId);
        authorRoleRelated.setCreatDt(authorRoleRelatedVO.getCreatDt());
        return authorRoleRelated;
    }

}
