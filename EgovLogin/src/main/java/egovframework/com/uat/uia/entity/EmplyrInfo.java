package egovframework.com.uat.uia.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name="uiaEmplyrInfo")
@Getter
@Setter
@Table(name="COMTNEMPLYRINFO")
public class EmplyrInfo implements CommonEntity {

    @Id
    @Column(name="EMPLYR_ID")
    private String emplyrId;

    @Column(name="ORGNZT_ID")
    private String orgnztId;

    @Column(name="USER_NM")
    private String userNm;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="EMPL_NO")
    private String empNo;

    @Column(name="IHIDNUM")
    private String ihidNum;

    @Column(name="SEXDSTN_CODE")
    private String sexdstnCode;

    @Column(name="BRTHDY")
    private String brthDy;

    @Column(name="FXNUM")
    private String fxNum;

    @Column(name="HOUSE_ADRES")
    private String houseAdres;

    @Column(name="PASSWORD_HINT")
    private String passwordHint;

    @Column(name="PASSWORD_CNSR")
    private String passwordCnsr;

    @Column(name="HOUSE_END_TELNO")
    private String houseEndTelno;

    @Column(name="AREA_NO")
    private String areaNo;

    @Column(name="DETAIL_ADRES")
    private String detailAdres;

    @Column(name="ZIP")
    private String zip;

    @Column(name="OFFM_TELNO")
    private String offmTelno;

    @Column(name="MBTLNUM")
    private String mbtlNum;

    @Column(name="EMAIL_ADRES")
    private String emailAdres;

    @Column(name="OFCPS_NM")
    private String ofcpsNm;

    @Column(name="HOUSE_MIDDLE_TELNO")
    private String houseMiddleTelno;

    @Column(name="GROUP_ID")
    private String groupId;

    @Column(name="PSTINST_CODE")
    private String pstinstCode;

    @Column(name="EMPLYR_STTUS_CODE")
    private String emplyrStusCode;

    @Column(name="ESNTL_ID")
    private String esntlId;

    @Column(name="CRTFC_DN_VALUE")
    private String crtfcDnValue;

    @Column(name="SBSCRB_DE")
    private String sbscrbDe;

    @Column(name="LOCK_AT")
    private String lockAt;

    @Column(name="LOCK_CNT")
    private Integer lockCnt;

    @Column(name="LOCK_LAST_PNTTM")
    private LocalDateTime lockLastPnttm;

    @Column(name="CHG_PWD_LAST_PNTTM")
    private LocalDateTime chgPwdLastPnttm;

}
