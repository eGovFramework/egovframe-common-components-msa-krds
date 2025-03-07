package egovframework.com.uat.uap.web;

import egovframework.com.uat.uap.service.LoginPolicyVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("uapEgovLoginPolicyController")
@RequestMapping("/uat/uap")
public class EgovLoginPolicyController {

    @GetMapping(value="/index")
    public String index(LoginPolicyVO loginPolicyVO, ModelMap model) {
        return this.loginPolicyListView(loginPolicyVO, model);
    }

    @RequestMapping(value="/loginPolicyListView", method={RequestMethod.GET, RequestMethod.POST})
    public String loginPolicyListView(LoginPolicyVO loginPolicyVO, ModelMap model) {
        model.addAttribute("loginPolicyVO", loginPolicyVO);
        return "uat/uap/loginPolicyList";
    }

    @PostMapping(value="/loginPolicyDetailView")
    public String loginPolicyDetailView(LoginPolicyVO loginPolicyVO, ModelMap model) {
        model.addAttribute("loginPolicyVO", loginPolicyVO);
        return "uat/uap/loginPolicyUpdate";
    }

}
