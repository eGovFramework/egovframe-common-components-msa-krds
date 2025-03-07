package egovframework.com.uss.olp.qmc.web;

import egovframework.com.uss.olp.qmc.service.QestnrInfoVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("qmcEgovQestnrInfoController")
@RequestMapping("/uss/olp/qmc")
public class EgovQestnrInfoController {

    @GetMapping(value="/index")
    public String index(QestnrInfoVO qestnrInfoVO, Model model) {
        return this.qestnrInfoListView(qestnrInfoVO, model);
    }

    @RequestMapping(value="/qestnrInfoListView", method={RequestMethod.GET, RequestMethod.POST})
    public String qestnrInfoListView(QestnrInfoVO qestnrInfoVO, Model model) {
        model.addAttribute("qestnrInfoVO", qestnrInfoVO);
        return "uss/olp/qmc/qestnrInfoList";
    }

    @PostMapping(value="/qestnrInfoDetailView")
    public String qestnrInfoDetailView(QestnrInfoVO qestnrInfoVO, Model model) {
        model.addAttribute("qestnrInfoVO", qestnrInfoVO);
        return "uss/olp/qmc/qestnrInfoDetail";
    }

    @PostMapping(value="/qestnrInfoInsertView")
    public String qestnrInfoInsertView(QestnrInfoVO qestnrInfoVO, Model model) {
        model.addAttribute("qestnrInfoVO", qestnrInfoVO);
        return "uss/olp/qmc/qestnrInfoInsert";
    }

    @PostMapping(value="/qestnrInfoUpdateView")
    public String qestnrInfoUpdateView(QestnrInfoVO qestnrInfoVO, Model model) {
        model.addAttribute("qestnrInfoVO", qestnrInfoVO);
        return "uss/olp/qmc/qestnrInfoUpdate";
    }

}
