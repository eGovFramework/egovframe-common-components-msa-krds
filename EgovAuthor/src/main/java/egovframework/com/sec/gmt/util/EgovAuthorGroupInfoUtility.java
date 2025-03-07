package egovframework.com.sec.gmt.util;

import egovframework.com.sec.gmt.entity.AuthorGroupInfo;
import egovframework.com.sec.gmt.service.AuthorGroupInfoVO;
import org.springframework.beans.BeanUtils;

public class EgovAuthorGroupInfoUtility {

    public static AuthorGroupInfoVO entityToVO(AuthorGroupInfo authorGroupInfo) {
        AuthorGroupInfoVO authorGroupInfoVO = new AuthorGroupInfoVO();
        BeanUtils.copyProperties(authorGroupInfo, authorGroupInfoVO);
        return authorGroupInfoVO;
    }

    public static AuthorGroupInfo VOToEntity(AuthorGroupInfoVO authorGroupInfoVO) {
        AuthorGroupInfo authorGroupInfo = new AuthorGroupInfo();
        BeanUtils.copyProperties(authorGroupInfoVO, authorGroupInfo);
        return authorGroupInfo;
    }

}
