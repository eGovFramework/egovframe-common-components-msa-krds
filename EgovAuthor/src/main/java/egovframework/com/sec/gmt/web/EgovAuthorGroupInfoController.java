package egovframework.com.sec.gmt.web;

import egovframework.com.sec.gmt.service.AuthorGroupInfoVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("gmtEgovAuthorGroupInfoController")
@RequestMapping("/sec/gmt")
public class EgovAuthorGroupInfoController {

    @GetMapping(value="/index")
    public String index() {
        return this.authorGroupInfoListView();
    }

    @RequestMapping(value="/authorGroupInfoListView", method={RequestMethod.GET, RequestMethod.POST})
    public String authorGroupInfoListView() {
        return "sec/gmt/authorGroupInfoList";
    }

    @PostMapping(value="/authorGroupInfoDetailView")
    public String authorGroupInfoDetailView(AuthorGroupInfoVO authorGroupInfoVO, Model model) {
        model.addAttribute("authorGroupInfoVO", authorGroupInfoVO);
        return "sec/gmt/authorGroupInfoUpdate";
    }

    @PostMapping(value="/authorGroupInfoInsertView")
    public String authorGroupInfoInsertView(AuthorGroupInfoVO authorGroupInfoVO, Model model) {
        model.addAttribute("authorGroupInfoVO", authorGroupInfoVO);
        return "sec/gmt/authorGroupInfoInsert";
    }

}
