package egovframework.com.cop.bbs.web;

import egovframework.com.cop.bbs.service.BbsMasterVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("bbsEgovBbsMasterController")
@RequestMapping("/cop/bbs")
public class EgovBbsMasterController {

    @GetMapping(value="/index")
    public String index(BbsMasterVO bbsMasterVO, Model model) {
        return bbsMasterListView(bbsMasterVO, model);
    }

    @RequestMapping(value="/bbsMasterListView", method={RequestMethod.GET, RequestMethod.POST})
    public String bbsMasterListView(BbsMasterVO bbsMasterVO, Model model) {
        model.addAttribute("bbsMasterVO", bbsMasterVO);
        return "cop/bbs/bbsMasterList";
    }

    @PostMapping(value="/bbsMasterDetailView")
    public String bbsMasterDetailView(BbsMasterVO bbsMasterVO, Model model) {
        model.addAttribute("bbsMasterVO", bbsMasterVO);
        return "cop/bbs/bbsMasterDetail";
    }

    @PostMapping(value="/bbsMasterInsertView")
    public String bbsMasterInsertView(BbsMasterVO bbsMasterVO, Model model) {
        model.addAttribute("bbsMasterVO", bbsMasterVO);
        return "cop/bbs/bbsMasterInsert";
    }

    @PostMapping(value="/bbsMasterUpdateView")
    public String bbsMasterUpdateView(BbsMasterVO bbsMasterVO, Model model) {
        model.addAttribute("bbsMasterVO", bbsMasterVO);
        return "cop/bbs/bbsMasterUpdate";
    }

}
