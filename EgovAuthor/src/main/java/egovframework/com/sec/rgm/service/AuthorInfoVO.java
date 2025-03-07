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
public class AuthorInfoVO implements Serializable {

    private static final long serialVersionUID = 8741950120752839486L;

    private String authorCode;

    private String authorNm;

    private String authorDc;

    private String authorCreatDe;

}
