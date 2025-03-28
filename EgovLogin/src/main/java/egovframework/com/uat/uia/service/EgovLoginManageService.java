package egovframework.com.uat.uia.service;

public interface EgovLoginManageService {

    LoginDTO actionLogin(LoginVO loginVO);

    LoginPolicyVO loginPolicy(LoginPolicyVO loginPolicyVO);

    LoginIncorrectVO loginIncorrectList(LoginVO loginVO);

    String loginIncorrectProcess(LoginVO loginVO, LoginIncorrectVO loginIncorrectVO, String lockCount);

}
