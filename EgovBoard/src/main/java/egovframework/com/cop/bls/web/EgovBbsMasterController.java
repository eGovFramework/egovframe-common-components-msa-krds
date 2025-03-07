package egovframework.com.cop.bls.web;

import egovframework.com.cop.bls.service.BbsMasterVO;
import egovframework.com.cop.bls.service.BlogVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("blsEgovBbsMasterController")
@RequestMapping("/cop/bls")
public class EgovBbsMasterController {

    @PostMapping(value="/blogBoardList")
    public String blogBoardList(BlogVO blogVO, BbsMasterVO bbsMasterVO, Model model) {
        bbsMasterVO.setBlogId(blogVO.getBlogId());
        bbsMasterVO.setBlogNm(blogVO.getBlogNm());
        model.addAttribute("bbsMasterVO", bbsMasterVO);
        return "cop/bls/bbsMasterList";
    }

    @RequestMapping(value="/bbsMasterListView", method={RequestMethod.GET, RequestMethod.POST})
    public String bbsMasterListView(BbsMasterVO bbsMasterVO, Model model) {
        model.addAttribute("bbsMasterVO", bbsMasterVO);
        return "cop/bls/bbsMasterList";
    }

    @PostMapping(value="/bbsMasterDetailView")
    public String bbsMasterDetailView(BbsMasterVO bbsMasterVO, Model model) {
        model.addAttribute("bbsMasterVO", bbsMasterVO);
        return "cop/bls/bbsMasterDetail";
    }

    @PostMapping(value="/bbsMasterInsertView")
    public String bbsMasterInsertView(BbsMasterVO bbsMasterVO, Model model) {
        model.addAttribute("bbsMasterVO", bbsMasterVO);
        return "cop/bls/bbsMasterInsert";
    }

    @PostMapping(value="/bbsMasterUpdateView")
    public String bbsMasterUpdateView(BbsMasterVO bbsMasterVO, Model model) {
        model.addAttribute("bbsMasterVO", bbsMasterVO);
        return "cop/bls/bbsMasterUpdate";
    }

}
