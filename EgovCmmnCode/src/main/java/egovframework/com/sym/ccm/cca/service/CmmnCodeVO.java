package egovframework.com.sym.ccm.cca.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.egovframe.rte.ptl.reactive.validation.EgovNullCheck;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CmmnCodeVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 2480298485617494982L;

    @EgovNullCheck(message="{comSymCcmCca.cmmnCodeVO.codeId}{common.required.msg}")
    private String codeId;

    @EgovNullCheck(message="{comSymCcmCca.cmmnCodeVO.codeIdNm}{common.required.msg}")
    private String codeIdNm;

    @EgovNullCheck(message="{comSymCcmCca.cmmnCodeVO.codeIdDc}{common.required.msg}")
    private String codeIdDc;

    private String useAt;

    @EgovNullCheck(message="{comSymCcmCca.cmmnCodeVO.clCode}{common.required.msg}")
    private String clCode;

    private String clCodeNm;

    private LocalDateTime frstRegistPnttm;

    private String frstRegisterId;

    private LocalDateTime lastUpdtPnttm;

    private String lastUpdusrId;

}
