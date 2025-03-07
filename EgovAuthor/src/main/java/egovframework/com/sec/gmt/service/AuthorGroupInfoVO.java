package egovframework.com.sec.gmt.service;

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
public class AuthorGroupInfoVO extends EgovDefaultVO implements Serializable  {

    private static final long serialVersionUID = 7931618692232704921L;

    private String groupId;

    @EgovNullCheck(message="{comCopSecGmt.list.groupNm}{common.required.msg}")
    private String groupNm;

    private String groupCreatDe;

    private String groupDc;

}
