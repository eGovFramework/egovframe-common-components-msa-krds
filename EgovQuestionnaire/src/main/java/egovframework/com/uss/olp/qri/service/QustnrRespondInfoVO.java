package egovframework.com.uss.olp.qri.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QustnrRespondInfoVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 1037598807703081447L;

    private String qustnrTmplatId;

    private String qestnrId;

    private String qustnrRespondId;

    private String sexdstnCode;

    private String occpTyCode;

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
