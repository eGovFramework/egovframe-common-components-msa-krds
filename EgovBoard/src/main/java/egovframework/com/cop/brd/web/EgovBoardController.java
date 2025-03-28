package egovframework.com.cop.brd.web;

import egovframework.com.cop.brd.service.BbsVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("brdEgovBoardController")
@RequestMapping("/cop/brd")
public class EgovBoardController {

    @GetMapping(value = "/index")
    public String index(BbsVO bbsVO, Model model) {
        return boardListView(bbsVO, model);
    }

    @RequestMapping(value = "/boardListView", method = {RequestMethod.GET, RequestMethod.POST})
    public String boardListView(BbsVO bbsVO, Model model) {
        model.addAttribute("bbsVO", bbsVO);
        return "cop/brd/boardList";
    }

    @PostMapping(value = "/boardDetailView")
    public String boardDetailView(BbsVO bbsVO, Model model) {
        model.addAttribute("bbsVO", bbsVO);
        return "cop/brd/boardDetail";
    }

    @PostMapping(value = "/boardInsertView")
    public String boardInsertView(BbsVO bbsVO, Model model) {
        model.addAttribute("bbsVO", bbsVO);
        return "cop/brd/boardInsert";
    }

    @PostMapping(value = "/boardUpdateView")
    public String boardUpdateView(BbsVO bbsVO, Model model) {
        model.addAttribute("bbsVO", bbsVO);
        return "cop/brd/boardUpdate";
    }

}
