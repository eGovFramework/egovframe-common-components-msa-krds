package egovframework.com.sym.ccm.cde.service;

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
public class CmmnDetailCodeVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 3810677034875969068L;

    @EgovNullCheck(message="{comSymCcmCde.cmmnDetailCodeVO.codeId}{common.required.msg}")
    private String codeId;

    private String codeIdNm;

    private String code;

    @EgovNullCheck(message="{comSymCcmCde.cmmnDetailCodeVO.codeNm}{common.required.msg}")
    private String codeNm;

    @EgovNullCheck(message="{comSymCcmCde.cmmnDetailCodeVO.codeDc}{common.required.msg}")
    private String codeDc;

    private String useAt;

    private LocalDateTime frstRegistPnttm;

    private String frstRegisterId;

    private LocalDateTime lastUpdtPnttm;

    private String lastUpdusrId;

}
