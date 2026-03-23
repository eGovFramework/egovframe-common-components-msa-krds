package egovframework.com.sym.ccm.ccc.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity(name="cccCmmnClCode")
@Getter
@Setter
@Table(name="COMTCCMMNCLCODE")
public class CmmnClCode {

    @Id
    @Column(name="CL_CODE", length=3)
    private String clCode;

    @Column(name="CL_CODE_NM")
    private String clCodeNm;

    @Column(name="CL_CODE_DC")
    private String clCodeDc;

    @Column(name="USE_AT", length=1)
    private String useAt;

    @Column(name="FRST_REGIST_PNTTM")
    private LocalDateTime frstRegistPnttm;

    @Column(name="FRST_REGISTER_ID")
    private String frstRegisterId;

    @Column(name="LAST_UPDT_PNTTM")
    private LocalDateTime lastUpdtPnttm;

    @Column(name="LAST_UPDUSR_ID")
    private String lastUpdusrId;

}
