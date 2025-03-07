package egovframework.com.uat.uap.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name="uapLoginPolicy")
@Getter
@Setter
@Table(name="COMTNLOGINPOLICY")
public class LoginPolicy {

    @Id
    @Column(name="EMPLYR_ID")
    private String employerId;

    @Column(name="IP_INFO")
    private String ipInfo;

    @Column(name="DPLCT_PERM_AT")
    private String dplctPermAt;

    @Column(name="LMTT_AT")
    private String lmttAt;

    @Column(name="FRST_REGISTER_ID")
    private String frstRegisterId;

    @Column(name="FRST_REGIST_PNTTM")
    private LocalDateTime frstRegisterPnttm;

    @Column(name="LAST_UPDUSR_ID")
    private String lastUpdusrId;

    @Column(name="LAST_UPDT_PNTTM")
    private LocalDateTime lastUpdtPnttm;

}
