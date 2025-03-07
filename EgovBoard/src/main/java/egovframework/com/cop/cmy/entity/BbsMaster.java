package egovframework.com.cop.cmy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name="cmyBbsMaster")
@Getter
@Setter
@Table(name="COMTNBBSMASTER")
public class BbsMaster {

    @Id
    @Column(name="BBS_ID")
    private String bbsId;

    @Column(name="BBS_NM")
    private String bbsNm;

    @Column(name="BBS_INTRCN")
    private String bbsIntrcn;

    @Column(name="BBS_TY_CODE")
    private String bbsTyCode;

    @Column(name="REPLY_POSBL_AT")
    private String replyPosblAt;

    @Column(name="FILE_ATCH_POSBL_AT")
    private String fileAtchPosblAt;

    @Column(name="ATCH_POSBL_FILE_NUMBER")
    private Integer atchPosblFileNumber;

    @Column(name="ATCH_POSBL_FILE_SIZE")
    private Long atchPosblFileSize;

    @Column(name="USE_AT")
    private String useAt;

    @Column(name="TMPLAT_ID")
    private String tmplatId;

    @Column(name="CMMNTY_ID")
    private String cmmntyId;

    @Column(name="FRST_REGISTER_ID")
    private String frstRegisterId;

    @Column(name="FRST_REGIST_PNTTM")
    private LocalDateTime frstRegistPnttm;

    @Column(name="LAST_UPDUSR_ID")
    private String lastUpdusrId;

    @Column(name="LAST_UPDT_PNTTM")
    private LocalDateTime lastUpdtPnttm;

    @Column(name="BLOG_ID")
    private String blogId;

    @Column(name="BLOG_AT")
    private String blogAt;

}
