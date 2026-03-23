package egovframework.com.cop.brd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "brdComment")
@Getter
@Setter
@Table(name = "COMTNCOMMENT")
public class Comment {

    @EmbeddedId
    private CommentId commentId;

    @Column(name = "WRTER_ID")
    private String wrterId;

    @Column(name = "WRTER_NM")
    private String wrterNm;

    @Column(name = "ANSWER")
    private String answer;

    @Column(name = "USE_AT")
    private String useAt;

    @Column(name = "FRST_REGIST_PNTTM")
    private LocalDateTime frstRegistPnttm;

    @Column(name = "FRST_REGISTER_ID")
    private String frstRegisterId;

    @Column(name = "LAST_UPDT_PNTTM")
    private LocalDateTime lastUpdtPnttm;

    @Column(name = "LAST_UPDUSR_ID")
    private String lastUpdusrId;

    @Column(name = "PASSWORD")
    private String password;

}
