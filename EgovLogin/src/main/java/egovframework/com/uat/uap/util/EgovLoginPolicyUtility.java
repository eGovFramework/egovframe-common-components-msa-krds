package egovframework.com.uat.uap.util;

import egovframework.com.uat.uap.entity.LoginPolicy;
import egovframework.com.uat.uap.service.LoginPolicyVO;
import org.springframework.beans.BeanUtils;

public class EgovLoginPolicyUtility {

    public static LoginPolicyVO entityToVO(LoginPolicy loginPolicy) {
        LoginPolicyVO loginPolicyVO = new LoginPolicyVO();
        BeanUtils.copyProperties(loginPolicy, loginPolicyVO);
        return loginPolicyVO;
    }

    public static LoginPolicy VOToEntity(LoginPolicyVO loginPolicyVO) {
        LoginPolicy loginPolicy = new LoginPolicy();
        BeanUtils.copyProperties(loginPolicyVO, loginPolicy);
        return loginPolicy;
    }

}
