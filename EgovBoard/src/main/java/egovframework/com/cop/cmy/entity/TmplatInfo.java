package egovframework.com.cop.cmy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name="cmyTmplatInfo")
@Getter
@Setter
@Table(name="COMTNTMPLATINFO")
public class TmplatInfo {

    @Id
    @Column(name="TMPLAT_ID")
    private String tmplatId;

    @Column(name="TMPLAT_NM")
    private String tmplatNm;

    @Column(name="TMPLAT_COURS")
    private String tmplatCours;

    @Column(name="USE_AT")
    private String useAt;

    @Column(name="TMPLAT_SE_CODE")
    private String tmplatSeCode;

    @Column(name="FRST_REGISTER_ID")
    private String frstRegisterId;

    @Column(name="FRST_REGIST_PNTTM")
    private LocalDateTime frstRegisterPnttm;

    @Column(name="LAST_UPDUSR_ID")
    private String lastUpdusrId;

    @Column(name="LAST_UPDT_PNTTM")
    private LocalDateTime lastUpdtPnttm;

}
