package egovframework.com.uss.olp.qqm.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class QestnrInfoId implements Serializable {

    private static final long serialVersionUID = 8039739203022871352L;

    /** 설문템플릿아이디 */
    @Column(name="QUSTNR_TMPLAT_ID")
    private String qustnrTmplatId;

    /** 설문아이디 */
    @Column(name="QESTNR_ID")
    private String qestnrId;

}
