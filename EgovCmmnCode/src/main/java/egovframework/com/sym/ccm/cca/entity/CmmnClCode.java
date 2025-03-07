package egovframework.com.sym.ccm.cca.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name="ccaCmmnClCode")
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

    @OneToMany(mappedBy="cmmnClCode", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<CmmnCode> cmmnCode;

}
