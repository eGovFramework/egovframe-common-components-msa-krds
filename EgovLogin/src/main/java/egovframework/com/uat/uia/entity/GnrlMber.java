package egovframework.com.uat.uia.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name="uiaGnrlMber")
@Getter
@Setter
@Table(name="COMTNGNRLMBER")
public class GnrlMber implements CommonEntity {

    @Id
    @Column(name="MBER_ID")
    private String mberId;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="PASSWORD_HINT")
    private String passwordHint;

    @Column(name="PASSWORD_CNSR")
    private String passwordCnsr;

    @Column(name="IHIDNUM")
    private String ihidNum;

    @Column(name="MBER_NM")
    private String mberNm;

    @Column(name="ZIP")
    private String zip;

    @Column(name="AREA_NO")
    private String areaNo;

    @Column(name="MBER_STTUS")
    private String mberStus;

    @Column(name="DETAIL_ADRES")
    private String detailAdres;

    @Column(name="END_TELNO")
    private String endTelno;

    @Column(name="MBTLNUM")
    private String mbtlNum;

    @Column(name="GROUP_ID")
    private String groupId;

    @Column(name="MBER_FXNUM")
    private String mberFxNum;

    @Column(name="MBER_EMAIL_ADRES")
    private String mberEmailAdres;

    @Column(name="MIDDLE_TELNO")
    private String middleTelno;

    @Column(name="SBSCRB_DE")
    private String sbscrbDe;

    @Column(name="SEXDSTN_CODE")
    private String sexdstnCode;

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
