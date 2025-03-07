package egovframework.com.uss.olp.qmc.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class QustnrRspnsResultId implements Serializable {

    private static final long serialVersionUID = 5772735915165686143L;

    /** 설문응답아이디 */
    @Column(name="QUSTNR_RSPNS_RESULT_ID")
    private String qustnrRspnsResultId;

    /** 설문문항아이디 */
    @Column(name="QUSTNR_QESITM_ID")
    private String qustnrQesitmId;

    /** 설문아이디 */
    @Column(name="QESTNR_ID")
    private String qestnrId;

    /** 설문템플릿아이디 */
    @Column(name="QUSTNR_TMPLAT_ID")
    private String qustnrTmplatId;

}
