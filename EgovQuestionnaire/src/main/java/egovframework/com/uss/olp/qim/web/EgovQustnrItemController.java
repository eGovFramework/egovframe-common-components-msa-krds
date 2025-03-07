package egovframework.com.uss.olp.qim.web;

import egovframework.com.uss.olp.qim.service.QustnrIemVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("qimEgovQustnrItemController")
@RequestMapping("/uss/olp/qim")
public class EgovQustnrItemController {

    @GetMapping(value="/index")
    public String index(QustnrIemVO qustnrIemVO, Model model) {
        return this.qusntrItemListView(qustnrIemVO, model);
    }

    @RequestMapping(value="/qusntrItemListView", method={RequestMethod.GET, RequestMethod.POST})
    public String qusntrItemListView(QustnrIemVO qustnrIemVO, Model model) {
        model.addAttribute("qustnrIemVO", qustnrIemVO);
        return "uss/olp/qim/qustnrItemList";
    }

    @PostMapping(value="/qustnrItemDetailView")
    public String qustnrItemDetailView(QustnrIemVO qustnrIemVO, Model model) {
        model.addAttribute("qustnrIemVO", qustnrIemVO);
        return "uss/olp/qim/qustnrItemDetail";
    }

    @PostMapping(value="/qustnrItemInsertView")
    public String qustnrItemInsertView(QustnrIemVO qustnrIemVO, Model model) {
        model.addAttribute("qustnrIemVO", qustnrIemVO);
        return "uss/olp/qim/qustnrItemInsert";
    }

    @PostMapping(value="/qustnrItemUpdateView")
    public String qustnrItemUpdateView(QustnrIemVO qustnrIemVO, Model model) {
        model.addAttribute("qustnrIemVO", qustnrIemVO);
        return "uss/olp/qim/qustnrItemUpdate";
    }

}
