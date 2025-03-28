package egovframework.com.uat.uia.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginPolicyVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 5746286563871786337L;

    private String employerId;

    private String ipInfo;

    private String dplctPermAt;

    private String lmttAt;

    private String frstRegisterId;

    private LocalDateTime frstRegisterPnttm;

    private String lastUpdusrId;

    private LocalDateTime lastUpdtPnttm;

    private String userNm;

    private String regYn;

}
