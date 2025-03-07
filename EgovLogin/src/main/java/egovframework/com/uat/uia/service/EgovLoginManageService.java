package egovframework.com.uat.uia.service;

public interface EgovLoginManageService {

    LoginDTO actionLogin(LoginVO loginVO);

    LoginIncorrectVO loginIncorrectList(LoginVO loginVO);

    String loginIncorrectProcess(LoginVO loginVO, LoginIncorrectVO loginIncorrectVO, String lockCount);

}
