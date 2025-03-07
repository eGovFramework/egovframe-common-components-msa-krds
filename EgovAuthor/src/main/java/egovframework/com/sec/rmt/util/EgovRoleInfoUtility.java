package egovframework.com.sec.rmt.util;

import egovframework.com.sec.rmt.entity.RoleInfo;
import egovframework.com.sec.rmt.service.RoleInfoVO;
import org.springframework.beans.BeanUtils;

public class EgovRoleInfoUtility {

    public static RoleInfoVO roleEntityToVO(RoleInfo roleInfo) {
        RoleInfoVO roleInfoVO = new RoleInfoVO();
        BeanUtils.copyProperties(roleInfo, roleInfoVO);
        return roleInfoVO;
    }

    public static RoleInfo roleVOToEntity(RoleInfoVO roleInfoVO) {
        RoleInfo roleInfo = new RoleInfo();
        BeanUtils.copyProperties(roleInfoVO, roleInfo);
        return roleInfo;
    }

}
