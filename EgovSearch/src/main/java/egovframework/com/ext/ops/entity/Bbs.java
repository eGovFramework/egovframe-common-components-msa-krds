package egovframework.com.ext.ops.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name="opsBbs")
@Getter
@Setter
@Table(name="COMTNBBS")
public class Bbs {

    @EmbeddedId
    private BbsId bbsId;

    @Column(name="NTT_NO")
    private String nttNo;

    @Column(name="NTT_SJ")
    private String nttSj;

    @Column(name="NTT_CN")
    private String nttCn;

    @Column(name="ANSWER_AT")
    private String answerAt;

    @Column(name="PARNTSCTT_NO")
    private String parntscttNo;

    @Column(name="ANSWER_LC")
    private String answerLc;

    @Column(name="SORT_ORDR")
    private String sortOrdr;

    @Column(name="RDCNT")
    private String rdcnt;

    @Column(name="USE_AT")
    private String useAt;

    @Column(name="NTCE_BGNDE")
    private String ntceBgnde;

    @Column(name="NTCE_ENDDE")
    private String ntceEndde;

    @Column(name="NTCR_ID")
    private String ntcrId;

    @Column(name="NTCR_NM")
    private String ntcrNm;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="ATCH_FILE_ID")
    private String atchFileId;

    @Column(name="NOTICE_AT")
    private String noticeAt;

    @Column(name="SJ_BOLD_AT")
    private String sjBoldAt;

    @Column(name="SECRET_AT")
    private String secretAt;

    @Column(name="FRST_REGIST_PNTTM")
    private LocalDateTime frstRegistPnttm;

    @Column(name="FRST_REGISTER_ID")
    private String frstRegisterId;

    @Column(name="LAST_UPDT_PNTTM")
    private LocalDateTime lastUpdtPnttm;

    @Column(name="LAST_UPDUSR_ID")
    private String lastUpdusrId;

    @Column(name="BLOG_ID")
    private String blogId;

}
