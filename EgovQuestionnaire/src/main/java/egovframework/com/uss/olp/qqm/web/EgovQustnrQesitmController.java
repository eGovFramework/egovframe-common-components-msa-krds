package egovframework.com.uss.olp.qqm.web;

import egovframework.com.uss.olp.qqm.service.QustnrQesitmVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("qqmEgovQustnrQesitmController")
@RequestMapping("/uss/olp/qqm")
public class EgovQustnrQesitmController {

    @GetMapping(value="/index")
    public String index(QustnrQesitmVO qustnrQesitmVO, Model model) {
        return this.qustnrQesitmListView(qustnrQesitmVO, model);
    }

    @RequestMapping(value="/qustnrQesitmListView", method={RequestMethod.GET, RequestMethod.POST})
    public String qustnrQesitmListView(QustnrQesitmVO qustnrQesitmVO, Model model) {
        model.addAttribute("qustnrQesitmVO", qustnrQesitmVO);
        return "uss/olp/qqm/qustnrQesitmList";
    }

    @PostMapping(value="/qustnrQesitmDetailView")
    public String qustnrQesitmDetailView(QustnrQesitmVO qustnrQesitmVO, Model model) {
        model.addAttribute("qustnrQesitmVO", qustnrQesitmVO);
        return "uss/olp/qqm/qustnrQesitmDetail";
    }

    @PostMapping(value="/qustnrQesitmInsertView")
    public String qustnrQesitmInsertView(QustnrQesitmVO qustnrQesitmVO, Model model) {
        model.addAttribute("qustnrQesitmVO", qustnrQesitmVO);
        return "uss/olp/qqm/qustnrQesitmInsert";
    }

    @PostMapping(value="/qustnrQesitmUpdateView")
    public String qustnrQesitmUpdateView(QustnrQesitmVO qustnrQesitmVO, Model model) {
        model.addAttribute("qustnrQesitmVO", qustnrQesitmVO);
        return "uss/olp/qqm/qustnrQesitmUpdate";
    }

}
