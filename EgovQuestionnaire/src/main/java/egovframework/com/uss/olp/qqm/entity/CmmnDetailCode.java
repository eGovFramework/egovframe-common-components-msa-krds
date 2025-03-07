package egovframework.com.uss.olp.qqm.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="qqmCmmnDetailCode")
@Getter
@Setter
@Table(name="COMTCCMMNDETAILCODE")
@Immutable
public class CmmnDetailCode {

    @EmbeddedId
    private CmmnDetailCodeId cmmnDetailCodeId;

    @Transient
    private String codeIdNm;

    @Column(name="CODE_NM")
    private String codeNm;

    @Column(name="CODE_DC")
    private String codeDc;

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
