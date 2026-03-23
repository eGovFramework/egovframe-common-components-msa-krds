package egovframework.com.cop.bbs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name="bbsBbsMasterOptn")
@Getter
@Setter
@Table(name="COMTNBBSMASTEROPTN")
public class BbsMasterOptn {

    @Id
    @Column(name="BBS_ID")
    private String bbsId;

    @Column(name="ANSWER_AT")
    private String answerAt;

    @Column(name="STSFDG_AT")
    private String stsfdgAt;

    @Column(name="FRST_REGIST_PNTTM")
    private LocalDateTime frstRegistPnttm;

    @Column(name="FRST_REGISTER_ID")
    private String frstRegisterId;

    @Column(name="LAST_UPDT_PNTTM")
    private LocalDateTime lastUpdtPnttm;

    @Column(name="LAST_UPDUSR_ID")
    private String lastUpdusrId;

}
