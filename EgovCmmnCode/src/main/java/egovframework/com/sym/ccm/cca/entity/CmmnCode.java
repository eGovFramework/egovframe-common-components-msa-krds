package egovframework.com.sym.ccm.cca.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="ccaCmmnCode")
@Getter
@Setter
@Table(name="COMTCCMMNCODE")
public class CmmnCode {

    @Id
    @Column(name="CODE_ID")
    private String codeId;

    @Column(name="CODE_ID_NM")
    private String codeIdNm;

    @Column(name="CODE_ID_DC")
    private String codeIdDc;

    @Column(name="USE_AT", length=1)
    private String useAt;

    @Column(name="CL_CODE", length=3)
    private String clCode;

    @Column(name="FRST_REGIST_PNTTM")
    private LocalDateTime frstRegistPnttm;

    @Column(name="FRST_REGISTER_ID")
    private String frstRegisterId;

    @Column(name="LAST_UPDT_PNTTM")
    private LocalDateTime lastUpdtPnttm;

    @Column(name="LAST_UPDUSR_ID")
    private String lastUpdusrId;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="CL_CODE", insertable=false, updatable=false)
    private CmmnClCode cmmnClCode;

}
