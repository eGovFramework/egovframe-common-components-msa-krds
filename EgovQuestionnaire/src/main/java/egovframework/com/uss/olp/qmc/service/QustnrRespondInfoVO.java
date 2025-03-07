package egovframework.com.uss.olp.qmc.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.egovframe.rte.ptl.reactive.validation.EgovNullCheck;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QustnrRespondInfoVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 1037598807703081447L;

    private String qustnrTmplatId;

    @EgovNullCheck(message = "{comUssOlpQrm.regist.qestnrCn}{common.required.msg}")
    private String qestnrId;

    private String qustnrRespondId;

    @EgovNullCheck(message = "{comUssOlpQrm.regist.sexdstnCode}{common.required.msg}")
    private String sexdstnCode;

    @EgovNullCheck(message = "{comUssOlpQrm.regist.occpTyCode}{common.required.msg}")
    private String occpTyCode;

    @EgovNullCheck(message = "{comUssOlpQrm.regist.respondNm}{common.required.msg}")
    private String respondNm;

    private String brthdy;

    private String areaNo;

    private String middleTelno;

    private String endTelno;

    private String frstRegistPnttm;

    private String frstRegisterId;

    private String lastUpdtPnttm;

    private String lastUpdusrId;

}
