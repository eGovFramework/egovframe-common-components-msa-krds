package egovframework.com.sec.ram.web;

import egovframework.com.sec.ram.service.AuthorInfoVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("ramEgovAuthorInfoController")
@RequestMapping("/sec/ram")
public class EgovAuthorInfoController {

    @GetMapping(value="/index")
    public String index(AuthorInfoVO authorInfoVO, Model model) {
        return this.authorInfoListView(authorInfoVO, model);
    }

    @RequestMapping(value="/authorInfoListView", method={RequestMethod.GET, RequestMethod.POST})
    public String authorInfoListView(AuthorInfoVO authorInfoVO, Model model) {
        model.addAttribute("authorInfoVO", authorInfoVO);
        return "sec/ram/authorInfoList";
    }

    @PostMapping(value="/authorInfoDetailView")
    public String authorInfoDetailView(AuthorInfoVO authorInfoVO, Model model) {
        model.addAttribute("authorInfoVO", authorInfoVO);
        return "sec/ram/authorInfoUpdate";
    }

    @PostMapping(value="/authorInfoInsertView")
    public String authorInfoInsertView(AuthorInfoVO authorInfoVO, Model model) {
        model.addAttribute("authorInfoVO", authorInfoVO);
        return "sec/ram/authorInfoInsert";
    }

    @PostMapping(value="/authorRoleInfoListView")
    public String authorRoleInfoListView(AuthorInfoVO authorInfoVO, Model model) {
        model.addAttribute("authorInfoVO", authorInfoVO);
        return "sec/ram/roleInfoList";
    }

}
