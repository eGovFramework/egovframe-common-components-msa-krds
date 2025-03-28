package egovframework.com.uat.uap.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginPolicyDTO {

    private String userId;
    private String userNm;
    private String userSe;
    private String ipInfo;
    private String dplctPermAt;
    private String lmttAt;
    private String lastUpdusrId;
    private String lastUpdtPnttm;
    private String regYn;

}
