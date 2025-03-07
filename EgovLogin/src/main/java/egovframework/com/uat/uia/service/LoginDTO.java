package egovframework.com.uat.uia.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    private String id;
    private String name;
    private String password;
    private String ihidNum;
    private String email;
    private String userSe;
    private String orgnztId;
    private String uniqId;
    private String ip;
    private String authorCode;

}
