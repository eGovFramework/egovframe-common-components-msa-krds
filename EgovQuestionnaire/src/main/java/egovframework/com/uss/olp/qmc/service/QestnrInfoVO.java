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
public class QestnrInfoVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = -1685274635551418593L;

    @EgovNullCheck(message="{comUssOlpQmc.regist.qestnrTmplatTy}{common.required.msg}")
    private String qustnrTmplatId;

    private String qestnrId;

    @EgovNullCheck(message="{comUssOlpQmc.regist.qestnrSj}{common.required.msg}")
    private String qustnrSj;

    @EgovNullCheck(message="{comUssOlpQmc.regist.qestnrPurps}{common.required.msg}")
    private String qustnrPurps;

    @EgovNullCheck(message="{comUssOlpQmc.regist.qestnrWritngGuidanceCn}{common.required.msg}")
    private String qustnrWritingGuidanceCn;

    @EgovNullCheck(message="{comUssOlpQmc.regist.qestnrTrget}{common.required.msg}")
    private String qustnrTrget;

    @EgovNullCheck(message="{comUssOlpQmc.title.startDay}{common.required.msg}")
    private String qustnrBgnde;

    @EgovNullCheck(message="{comUssOlpQmc.title.endDay}{common.required.msg}")
    private String qustnrEndde;

    private String frstRegistPnttm;

    private String frstRegisterId;

    private String lastUpdtPnttm;

    private String lastUpdusrId;

    private String userNm;

}
