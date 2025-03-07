package egovframework.com.uss.olp.qri.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name="qriQustnrIem")
@Getter
@Setter
@Table(name="COMTNQUSTNRIEM")
@Immutable
public class QustnrIem {

    /** 설문항목아이디 */
    @EmbeddedId
    private QustnrIemId qustnrIemId;

    /** 항목순번 */
    @Column(name="IEM_SN")
    private String iemSn;

    /** 항목내용 */
    @Column(name="IEM_CN")
    private String iemCn;

    /** 기타답변여부 */
    @Column(name="ETC_ANSWER_AT")
    private String etcAnswerAt;

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
