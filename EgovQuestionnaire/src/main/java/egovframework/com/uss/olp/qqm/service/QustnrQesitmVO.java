package egovframework.com.uss.olp.qqm.service;

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
public class QustnrQesitmVO extends EgovDefaultVO implements Serializable  {

    private static final long serialVersionUID = -6759632124299100642L;

    private String qustnrTmplatId;

    @EgovNullCheck(message="{comUssOlpQmc.regist.qestnrSj}{common.required.msg}")
    private String qestnrId;

    private String qustnrQesitmId;

    @EgovNullCheck(message="{comUssOlpQqm.regist.qestnSn}{common.required.msg}")
    private String qestnSn;

    @EgovNullCheck(message = "{comUssOlpQqm.regist.qestnTyCode}{common.required.msg}")
    private String qestnTyCode;

    @EgovNullCheck(message = "{comUssOlpQqm.regist.qestnCn}{common.required.msg}")
    private String qestnCn;

    @EgovNullCheck(message = "{comUssOlpQqm.regist.mxmmChoiseCo}{common.required.msg}")
    private String mxmmChoiseCo;

    private String frstRegistPnttm;

    private String frstRegisterId;

    private String lastUpdtPnttm;

    private String lastUpdusrId;

    private String userNm;

}
