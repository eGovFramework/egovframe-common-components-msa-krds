package egovframework.com.uss.olp.qqm.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class QustnrIemId implements Serializable {

    private static final long serialVersionUID = -7667341540520295700L;

    /** 설문템플릿아이디 */
    @Column(name="QUSTNR_TMPLAT_ID")
    private String qustnrTmplatId;

    /** 설문아이디 */
    @Column(name="QESTNR_ID")
    private String qestnrId;

    /** 설문문항아이디 */
    @Column(name="QUSTNR_QESITM_ID")
    private String qustnrQesitmId;

    /** 설문항목아이디 */
    @Column(name="QUSTNR_IEM_ID")
    private String qustnrIemId;

}
