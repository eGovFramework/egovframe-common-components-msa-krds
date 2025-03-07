package egovframework.com.sec.ram.service;

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
public class AuthorInfoVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = -1404402387316704156L;

    private String originalAuthorCode;

    @EgovNullCheck(message="{comCopSecRam.regist.authorCode}{common.required.msg}")
    private String authorCode;

    @EgovNullCheck(message="{comCopSecRam.regist.authorNm}{common.required.msg}")
    private String authorNm;

    private String authorDc;

    private String authorCreatDe;

}
