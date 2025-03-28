package egovframework.com.sec.gmt.util;

import egovframework.com.sec.gmt.entity.AuthorGroupInfo;
import egovframework.com.sec.gmt.service.AuthorGroupInfoVO;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

@UtilityClass
public class EgovAuthorGroupInfoUtility {

    public static AuthorGroupInfoVO entityToVO(AuthorGroupInfo authorGroupInfo) {
        AuthorGroupInfoVO authorGroupInfoVO = new AuthorGroupInfoVO();
        BeanUtils.copyProperties(authorGroupInfo, authorGroupInfoVO);
        return authorGroupInfoVO;
    }

    public static AuthorGroupInfo vOToEntity(AuthorGroupInfoVO authorGroupInfoVO) {
        AuthorGroupInfo authorGroupInfo = new AuthorGroupInfo();
        BeanUtils.copyProperties(authorGroupInfoVO, authorGroupInfo);
        return authorGroupInfo;
    }

}
