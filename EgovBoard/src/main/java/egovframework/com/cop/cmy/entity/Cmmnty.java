package egovframework.com.cop.cmy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name="cmyCmmnty")
@Getter
@Setter
@Table(name="COMTNCMMNTY")
public class Cmmnty {

    @Id
    @Column(name="CMMNTY_ID")
    private String cmmntyId;

    @Column(name="CMMNTY_NM")
    private String cmmntyNm;

    @Column(name="CMMNTY_INTRCN")
    private String cmmntyIntrcn;

    @Column(name="USE_AT")
    private String useAt;

    @Column(name="REGIST_SE_CODE")
    private String registSeCode;

    @Column(name="TMPLAT_ID")
    private String tmplatId;

    @Column(name="FRST_REGIST_PNTTM")
    private LocalDateTime frstRegistPnttm;

    @Column(name="FRST_REGISTER_ID")
    private String frstRegisterId;

    @Column(name="LAST_UPDT_PNTTM")
    private LocalDateTime lastUpdtPnttm;

    @Column(name="LAST_UPDUSR_ID")
    private String lastUpdusrId;

}
