package egovframework.com.sec.rgm.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorGroupInfoVO extends EgovDefaultVO implements Serializable  {

    private static final long serialVersionUID = 7931618692232704921L;

    private String groupId;

    private String groupNm;

    private String groupCreatDe;

    private String groupDc;

}
