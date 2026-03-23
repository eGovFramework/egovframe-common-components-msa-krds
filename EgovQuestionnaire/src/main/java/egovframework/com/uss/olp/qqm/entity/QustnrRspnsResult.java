package egovframework.com.uss.olp.qqm.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity(name="qqmQustnrRspnsResult")
@Getter
@Setter
@Table(name="COMTNQUSTNRRSPNSRESULT")
public class QustnrRspnsResult {

    /** 설문결과아이디 */
    @EmbeddedId
    private QustnrRspnsResultId qustnrRspnsResultId;

    /** 응답자답변내용 */
    @Column(name="RESPOND_ANSWER_CN")
    private String respondAnswerCn;

    /** 기타답변내용 */
    @Column(name="ETC_ANSWER_CN")
    private String etcAnswerCn;

    /** 응답자명 */
    @Column(name="RESPOND_NM")
    private String respondNm;

    /** 최초등록시점  */
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

    /** 설문항목아이디 */
    @Column(name="QUSTNR_IEM_ID")
    private String qustnrIemId;

}
