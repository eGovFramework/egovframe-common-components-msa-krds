package egovframework.com.uss.olp.qim.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QestnrInfoVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = -1685274635551418593L;

    private String qustnrTmplatId;

    private String qestnrId;

    private String qustnrSj;

    private String qustnrPurps;

    private String qustnrWritingGuidanceCn;

    private String qustnrTrget;

    private String qustnrBgnde;

    private String qustnrEndde;

    private String frstRegistPnttm;

    private String frstRegisterId;

    private String lastUpdtPnttm;

    private String lastUpdusrId;

    private String userNm;

}
