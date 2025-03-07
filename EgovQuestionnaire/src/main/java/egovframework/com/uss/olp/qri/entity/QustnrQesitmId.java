package egovframework.com.uss.olp.qri.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class QustnrQesitmId implements Serializable {

    private static final long serialVersionUID = 7412414410679951206L;

    /** 설문템플릿 아이디 */
    @Column(name="QUSTNR_TMPLAT_ID")
    private String qustnrTmplatId;

    /** 설문 아이디 */
    @Column(name="QESTNR_ID")
    private String qestnrId;

    /** 설문 문항 아이디 */
    @Column(name="QUSTNR_QESITM_ID")
    private String qustnrQesitmId;

}
