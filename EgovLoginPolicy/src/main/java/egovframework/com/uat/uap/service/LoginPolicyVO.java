package egovframework.com.uat.uap.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.egovframe.rte.ptl.reactive.validation.EgovIPCheck;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginPolicyVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = -2036717150000401961L;

    private String employerId;

    @EgovIPCheck(message="{comUatUap.loginPolicyRegist.validate.info.invalidForm}")
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
