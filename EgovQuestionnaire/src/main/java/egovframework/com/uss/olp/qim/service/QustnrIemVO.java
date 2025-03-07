package egovframework.com.uss.olp.qim.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.egovframe.rte.ptl.reactive.validation.EgovNullCheck;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QustnrIemVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = -4825963549586612206L;

    /** 설문템플릿 아이디 */
    private String qustnrTmplatId;

    /** 설문 아이디 */
    @EgovNullCheck(message="{comUssOlpQim.regist.qestnrCn}{common.required.msg}")
    private String qestnrId;

    /** 설문 문항 아이디 */
    @EgovNullCheck(message="{comUssOlpQim.regist.qestnrQesitmCn}{common.required.msg}")
    private String qustnrQesitmId;

    /** 설문 항목 아이디 */
    private String qustnrIemId;

    /** 항목순번 */
    @EgovNullCheck(message="{comUssOlpQim.regist.iemSn}{common.required.msg}")
    private String iemSn;

    /** 설문항목아이디 */
    @EgovNullCheck(message="{comUssOlpQim.regist.iemCn}{common.required.msg}")
    private String iemCn;

    /** 기타답변여부 */
    private String etcAnswerAt;

    /** 최초등록자아이디 */
    private LocalDateTime frstRegistPnttm;

    /** 최초등록시점  */
    private String frstRegisterId;

    /** 최종수정시점 */
    private LocalDateTime lastUpdtPnttm;

    /** 최종수정시점아이디  */
    private String lastUpdusrId;

}
