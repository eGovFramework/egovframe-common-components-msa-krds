package egovframework.com.uss.olp.qri.web;

import egovframework.com.uss.olp.qri.service.QustnrRspnsResultVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("qriEgovQustnrRspnsResultController")
@RequestMapping("/uss/olp/qri")
public class EgovQustnrRspnsResultController {

    @GetMapping(value="/index")
    public String index(QustnrRspnsResultVO qustnrRspnsResultVO, Model model) {
        return this.qustnrRspnsResultListView(qustnrRspnsResultVO, model);
    }

    @RequestMapping(value="/qustnrRspnsResultListView", method={RequestMethod.GET, RequestMethod.POST})
    public String qustnrRspnsResultListView(QustnrRspnsResultVO qustnrRspnsResultVO, Model model) {
        model.addAttribute("qustnrRspnsResultVO", qustnrRspnsResultVO);
        return "uss/olp/qri/qustnrRspnsResultList";
    }

    @PostMapping(value="/qustnrRspnsResultInsertView")
    public String qustnrRspnsResultInsertView(QustnrRspnsResultVO qustnrRspnsResultVO, Model model) {
        model.addAttribute("qustnrRspnsResultVO", qustnrRspnsResultVO);
        return "uss/olp/qri/qustnrRspnsResultInsert";
    }

}
