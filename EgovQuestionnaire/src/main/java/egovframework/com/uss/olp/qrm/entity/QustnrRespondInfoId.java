package egovframework.com.uss.olp.qrm.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class QustnrRespondInfoId implements Serializable {

    private static final long serialVersionUID = -3932722110118539587L;

    /** 설문템플릿아이디 */
    @Column(name="QUSTNR_TMPLAT_ID")
    private String qustnrTmplatId;

    /** 설문아이디 */
    @Column(name="QESTNR_ID")
    private String qestnrId;

    /** 설문응답자아이디 */
    @Column(name="QUSTNR_RESPOND_ID")
    private String qustnrRespondId;

}
