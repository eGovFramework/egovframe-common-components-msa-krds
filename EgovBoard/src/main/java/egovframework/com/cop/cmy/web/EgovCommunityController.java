package egovframework.com.cop.cmy.web;

import egovframework.com.cop.cmy.service.CommunityVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("cmyEgovCommunityController")
@RequestMapping("/cop/cmy")
public class EgovCommunityController {

    @GetMapping(value="/index")
    public String index(CommunityVO communityVO, Model model) {
        return this.communityListView(communityVO, model);
    }

    @RequestMapping(value="/communityListView", method={RequestMethod.GET, RequestMethod.POST})
    public String communityListView(CommunityVO communityVO, Model model) {
        model.addAttribute("communityVO", communityVO);
        return "cop/cmy/communityList";
    }

    @PostMapping(value="/communityDetailView")
    public String communityDetailView(CommunityVO communityVO, Model model) {
        model.addAttribute("communityVO", communityVO);
        return "cop/cmy/communityDetail";
    }

    @PostMapping(value="/communityInsertView")
    public String communityInsertView(CommunityVO communityVO, Model model) {
        model.addAttribute("communityVO", communityVO);
        return "cop/cmy/communityInsert";
    }

    @PostMapping(value="/communityUpdateView")
    public String communityUpdateView(CommunityVO communityVO, Model model) {
        model.addAttribute("communityVO", communityVO);
        return "cop/cmy/communityUpdate";
    }

}
