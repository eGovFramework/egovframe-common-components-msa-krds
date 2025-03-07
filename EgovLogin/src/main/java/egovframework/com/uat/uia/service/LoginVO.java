package egovframework.com.uat.uia.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = -4749750948688396671L;

    /** 아이디 */
    private String userId;

    /** 이름 */
    private String name;

    /** 주민등록번호 */
    private String ihidNum;

    /** 이메일주소 */
    private String email;

    /** 비밀번호 */
    private String userPw;

    /** 비밀번호 힌트 */
    private String passwordHint;

    /** 비밀번호 정답 */
    private String passwordCnsr;

    /** 사용자구분 */
    private String userSe;

    /** 조직(부서)ID */
    private String orgnztId;

    /** 조직(부서)명 */
    private String orgnztNm;

    /** 고유아이디 */
    private String uniqId;

    /** 로그인 후 이동할 페이지 */
    private String url;

    /** 로그인 후 정보 */
    private String userInfo;

    /** 사용자 IP정보 */
    private String ip;

    /** 사용자 권한정보 */
    private String authorCode;

}
