package egovframework.com.sec.rmt.web;

import egovframework.com.sec.rmt.service.RoleInfoVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("rmtEgovRoleManageController")
@RequestMapping("/sec/rmt")
public class EgovRoleInfoController {

    @GetMapping(value="/index")
    public String index() {
        return this.roleInfoListView();
    }

    @RequestMapping(value="/roleInfoListView", method={RequestMethod.GET, RequestMethod.POST})
    public String roleInfoListView() {
        return "sec/rmt/roleInfoList";
    }

    @PostMapping(value="/roleInfoDetailView")
    public String roleInfoDetailView(RoleInfoVO roleInfoVO, Model model) {
        model.addAttribute("roleInfoVO", roleInfoVO);
        return "sec/rmt/roleInfoUpdate";
    }

    @PostMapping(value="/roleInfoInsertView")
    public String roleInfoInsertView(RoleInfoVO roleInfoVO, Model model) {
        model.addAttribute("roleInfoVO", roleInfoVO);
        return "sec/rmt/roleInfoInsert";
    }

}
