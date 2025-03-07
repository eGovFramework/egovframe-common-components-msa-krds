package egovframework.com.sec.rmt.service;

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
public class RoleInfoVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = -7812185031643112764L;

    private String roleCode;

    @EgovNullCheck(message="{comCopSecRam.regist.rollNm}{common.required.msg}")
    private String roleNm;

    @EgovNullCheck(message="{comCopSecRam.regist.rollPtn}{common.required.msg}")
    private String rolePttrn;

    private String roleDc;

    @EgovNullCheck(message="{comCopSecRam.regist.rollType}{common.required.msg}")
    private String roleTy;

    @EgovNullCheck(message="{comCopSecRam.regist.rollSort}{common.required.msg}")
    private String roleSort;

    private String roleCreatDe;

}
