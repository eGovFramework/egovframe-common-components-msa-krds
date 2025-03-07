package egovframework.com.uss.olp.qrm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name="qrmQestnrInfo")
@Getter
@Setter
@Table(name="COMTNQESTNRINFO")
public class QestnrInfo {

    /** 설문아이디 */
    @EmbeddedId
    private QestnrInfoId qestnrInfoId;

    /** 설문제목 */
    @Column(name="QUSTNR_SJ")
    private String qustnrSj;

    /** 설문목적 */
    @Column(name="QUSTNR_PURPS")
    private String qustnrPurps;

    /** 설문작성안내내용 */
    @Column(name="QUSTNR_WRITNG_GUIDANCE_CN")
    private String qustnrWritingGuidanceCn;

    /** 설문대상 */
    @Column(name="QUSTNR_TRGET")
    private String qustnrTrget;

    /** 설문기간시작일 */
    @Column(name="QUSTNR_BGNDE")
    private String qustnrBgnde;

    /** 설문기간종료일 */
    @Column(name="QUSTNR_ENDDE")
    private String qustnrEndde;

    /** 최초등록시점 */
    @Column(name="FRST_REGIST_PNTTM")
    private LocalDateTime frstRegistPnttm;

    /** 최초등록자아이디 */
    @Column(name="FRST_REGISTER_ID")
    private String frstRegisterId;

    /** 최종수정시점 */
    @Column(name="LAST_UPDT_PNTTM")
    private LocalDateTime lastUpdtPnttm;

    /** 최종수정시점아이디  */
    @Column(name="LAST_UPDUSR_ID")
    private String lastUpdusrId;

}
