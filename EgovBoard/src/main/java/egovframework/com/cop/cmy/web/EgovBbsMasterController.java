package egovframework.com.cop.cmy.web;

import egovframework.com.cop.cmy.service.BbsMasterVO;
import egovframework.com.cop.cmy.service.CommunityVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("cmyEgovBbsMasterController")
@RequestMapping("/cop/cmy")
public class EgovBbsMasterController {

    @PostMapping(value="/communityBoardList")
    public String communityBoardList(CommunityVO communityVO, BbsMasterVO bbsMasterVO, Model model) {
        bbsMasterVO.setCmmntyId(communityVO.getCmmntyId());
        bbsMasterVO.setCmmntyNm(communityVO.getCmmntyNm());
        model.addAttribute("bbsMasterVO", bbsMasterVO);
        return "cop/cmy/bbsMasterList";
    }

    @RequestMapping(value="/bbsMasterListView", method={RequestMethod.GET, RequestMethod.POST})
    public String bbsMasterListView(BbsMasterVO bbsMasterVO, Model model) {
        model.addAttribute("bbsMasterVO", bbsMasterVO);
        return "cop/cmy/bbsMasterList";
    }

    @PostMapping(value="/bbsMasterDetailView")
    public String bbsMasterDetailView(BbsMasterVO bbsMasterVO, Model model) {
        model.addAttribute("bbsMasterVO", bbsMasterVO);
        return "cop/cmy/bbsMasterDetail";
    }

    @PostMapping(value="/bbsMasterInsertView")
    public String bbsMasterInsertView(BbsMasterVO bbsMasterVO, Model model) {
        model.addAttribute("bbsMasterVO", bbsMasterVO);
        return "cop/cmy/bbsMasterInsert";
    }

    @PostMapping(value="/bbsMasterUpdateView")
    public String bbsMasterUpdateView(BbsMasterVO bbsMasterVO, Model model) {
        model.addAttribute("bbsMasterVO", bbsMasterVO);
        return "cop/cmy/bbsMasterUpdate";
    }

}
