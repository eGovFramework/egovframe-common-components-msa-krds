package egovframework.com.uss.olp.qmc.service;

import egovframework.com.uss.olp.qim.service.EgovDefaultVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QustnrQesitmVO extends EgovDefaultVO implements Serializable  {

    private static final long serialVersionUID = -585408692549814359L;

    private String qestnrId;

    private String qustnrQesitmId;

    private String qustnrTmplatId;

    private String qestnSn;

    private String qestnTyCode;

    private String qestnCn;

    private String mxmmChoiseCo;

    private String frstRegistPnttm;

    private String frstRegisterId;

    private String lastUpdtPnttm;

    private String lastUpdusrId;

    private String userNm;

}
