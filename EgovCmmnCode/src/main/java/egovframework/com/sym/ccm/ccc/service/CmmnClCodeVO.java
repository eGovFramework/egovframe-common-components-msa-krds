package egovframework.com.sym.ccm.ccc.service;

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
public class CmmnClCodeVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 1532959585797289738L;

    @EgovNullCheck(message="{comSymCcmCcc.cmmnClCodeVO.clCode}{common.required.msg}")
    private String clCode;

    @EgovNullCheck(message="{comSymCcmCcc.cmmnClCodeVO.clCodeNm}{common.required.msg}")
    private String clCodeNm;

    @EgovNullCheck(message="{comSymCcmCcc.cmmnClCodeVO.clCodeDc}{common.required.msg}")
    private String clCodeDc;

    private String useAt;

    private LocalDateTime frstRegistPnttm;

    private String frstRegisterId;

    private LocalDateTime lastUpdtPnttm;

    private String lastUpdusrId;

}
