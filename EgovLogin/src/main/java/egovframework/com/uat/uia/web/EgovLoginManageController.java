package egovframework.com.uat.uia.web;

import egovframework.com.uat.uia.service.LoginVO;
import egovframework.com.uat.uia.util.EgovJwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller("uiaEgovLoginManageController")
@RequestMapping("/uat/uia")
@RequiredArgsConstructor
public class EgovLoginManageController {

    private final EgovJwtProvider jwtProvider;

    @GetMapping(value="/index")
    public String login(LoginVO loginVO, Model model, HttpServletRequest request) throws Exception {
        return this.loginView(loginVO, model, request);
    }

    @RequestMapping(value="/loginView", method={RequestMethod.GET, RequestMethod.POST})
    public String loginView(LoginVO loginVO, Model model, HttpServletRequest request) {
        String accessToken = jwtProvider.getCookie(request, "accessToken");
        System.out.println("##### EgovLoginManageController loginView >>> " + accessToken);
        if (ObjectUtils.isEmpty(accessToken)) {
            loginVO = new LoginVO();
            model.addAttribute("loginVO", loginVO);
            return "uat/uia/login";
        } else {
            String userId = jwtProvider.decrypt(jwtProvider.extractUserId(accessToken));
            String userNm = jwtProvider.decrypt(jwtProvider.extractUserNm(accessToken));
            loginVO.setUserInfo(userNm + "(" + userId + ")");
            model.addAttribute("loginVO", loginVO);
            return "uat/uia/content";
        }
    }

    @RequestMapping(value="/loginForbidden", method={RequestMethod.GET, RequestMethod.POST})
    public String loginForbidden(@RequestParam(value = "pathCode", required = false, defaultValue = "1") String pathCode, Model model) {
        model.addAttribute("pathCode", pathCode);
        return "uat/uia/forbidden";
    }

}
