package egovframework.com.uat.uia.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name="uiaEntrprsMber")
@Getter
@Setter
@Table(name="COMTNENTRPRSMBER")
public class EntrprsMber implements CommonEntity {

    @Id
    @Column(name="ENTRPRS_MBER_ID")
    private String entrprsMberId;

    @Column(name="ENTRPRS_SE_CODE")
    private String entrprsSeCode;

    @Column(name="BIZRNO")
    private String bizrno;

    @Column(name="JURIRNO")
    private String jurirno;

    @Column(name="CMPNY_NM")
    private String cmpnyNm;

    @Column(name="CXFC")
    private String cxfc;

    @Column(name="ZIP")
    private String zip;

    @Column(name="ADRES")
    private String adres;

    @Column(name="ENTRPRS_MIDDLE_TELNO")
    private String entrprsMiddleTelno;

    @Column(name="FXNUM")
    private String fxnum;

    @Column(name="INDUTY_CODE")
    private String indutyCode;

    @Column(name="APPLCNT_NM")
    private String applcntNm;

    @Column(name="APPLCNT_IHIDNUM")
    private String applcntIhidnum;

    @Column(name="SBSCRB_DE")
    private String sbscrbDe;

    @Column(name="ENTRPRS_MBER_STTUS")
    private String entrprsMberStus;

    @Column(name="ENTRPRS_MBER_PASSWORD")
    private String entrprsMberPassword;

    @Column(name="ENTRPRS_MBER_PASSWORD_HINT")
    private String entrprsMberPasswordHint;

    @Column(name="ENTRPRS_MBER_PASSWORD_CNSR")
    private String entrprsMberPasswordCnsr;

    @Column(name="GROUP_ID")
    private String groupId;

    @Column(name="DETAIL_ADRES")
    private String detailAdres;

    @Column(name="ENTRPRS_END_TELNO")
    private String entrprsEndTelno;

    @Column(name="AREA_NO")
    private String areaNo;

    @Column(name="APPLCNT_EMAIL_ADRES")
    private String applcntEmailAdres;

    @Column(name="ESNTL_ID")
    private String esntlId;

    @Column(name="LOCK_AT")
    private String lockAt;

    @Column(name="LOCK_CNT")
    private Integer lockCnt;

    @Column(name="LOCK_LAST_PNTTM")
    private LocalDateTime lockLastPnttm;

    @Column(name="CHG_PWD_LAST_PNTTM")
    private LocalDateTime chgPwdLastPnttm;

}
