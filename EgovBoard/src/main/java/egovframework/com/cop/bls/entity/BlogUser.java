package egovframework.com.cop.bls.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name="blsBlogUser")
@Getter
@Setter
@Table(name="COMTNBLOGUSER")
public class BlogUser {

    @EmbeddedId
    private BlogUserId blogUserId;

    @Column(name="MNGR_AT")
    private String mngrAt;

    @Column(name="MBER_STTUS")
    private String mberSttus;

    @Column(name="SBSCRB_DE")
    private LocalDateTime sbscrbDe;

    @Column(name="SECSN_DE")
    private String secsnDe;

    @Column(name="USE_AT")
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
