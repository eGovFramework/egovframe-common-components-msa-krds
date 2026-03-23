package egovframework.com.uat.uia.token;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AuthorizeToken implements Serializable {

    private String username;
    private String tokenKey;
    private String refreshToken;
    private String regDate;
    private String expDate;

    public AuthorizeToken(String username, String tokenKey, String refreshToken, String regDate, String expDate) {
        this.username = username;
        this.tokenKey = tokenKey;
        this.refreshToken = refreshToken;
        this.regDate = regDate;
        this.expDate = expDate;
    }

}
