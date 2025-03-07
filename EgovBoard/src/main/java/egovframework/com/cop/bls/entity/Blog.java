package egovframework.com.cop.bls.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name="blsBlog")
@Getter
@Setter
@Table(name="COMTNBLOG")
public class Blog {

    @Id
    @Column(name="BLOG_ID")
    private String blogId;

    @Column(name="BLOG_NM")
    private String blogNm;

    @Column(name="BLOG_INTRCN")
    private String blogIntrcn;

    @Column(name="USE_AT")
    private String useAt;

    @Column(name="REGIST_SE_CODE")
    private String registSeCode;

    @Column(name="TMPLAT_ID")
    private String tmplatId;

    @Column(name="FRST_REGIST_PNTTM")
    private LocalDateTime frstRegistPnttm;

    @Column(name="FRST_REGISTER_ID")
    private String frstRegisterId;

    @Column(name="LAST_UPDT_PNTTM")
    private LocalDateTime lastUpdtPnttm;

    @Column(name="LAST_UPDUSR_ID")
    private String lastUpdusrId;

    @Column(name="BBS_ID")
    private String bbsId;

    @Column(name="BLOG_AT")
    private String blogAt;

}
