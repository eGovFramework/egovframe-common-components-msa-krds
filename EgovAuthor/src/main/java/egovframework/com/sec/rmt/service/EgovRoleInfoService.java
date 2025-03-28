package egovframework.com.sec.rmt.service;

import org.springframework.data.domain.Page;

public interface EgovRoleInfoService {

    Page<RoleInfoDTO> list(RoleInfoVO roleInfoVO);

    RoleInfoVO detail(RoleInfoVO roleInfoVO);

    RoleInfoVO insert(RoleInfoVO roleInfoVO);

    RoleInfoVO update(RoleInfoVO roleInfoVO);

    void delete(RoleInfoVO roleInfoVO);

}
