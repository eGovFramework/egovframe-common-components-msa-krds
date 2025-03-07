package egovframework.com.sec.rmt.service;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.springframework.data.domain.Page;

public interface EgovRoleInfoService {

    Page<RoleInfoDTO> list(RoleInfoVO roleInfoVO);

    RoleInfoVO detail(RoleInfoVO roleInfoVO);

    RoleInfoVO insert(RoleInfoVO roleInfoVO) throws FdlException;;

    RoleInfoVO update(RoleInfoVO roleInfoVO);

    void delete(RoleInfoVO roleInfoVO);

}
