package egovframework.com.sec.ram.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoleInfoDTO {

    private String roleCode;
    private String roleNm;
    private String rolePttrn;
    private String roleDc;
    private String roleTy;
    private String roleSort;
    private String regYn;
    private String creatDt;

}
