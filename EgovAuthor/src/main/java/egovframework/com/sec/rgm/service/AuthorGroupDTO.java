package egovframework.com.sec.rgm.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthorGroupDTO {

    private String userId;
    private String userNm;
    private String groupId;
    private String mberTyCode;
    private String mberTyNm;
    private String authorCode;
    private String regYn;
    private String esntlId;

}
