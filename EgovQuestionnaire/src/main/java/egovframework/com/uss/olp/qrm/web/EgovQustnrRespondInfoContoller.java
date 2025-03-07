package egovframework.com.uss.olp.qrm.web;

import egovframework.com.uss.olp.qrm.service.QustnrRespondInfoVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("qrmEgovQustnrRespondInfoContoller")
@RequestMapping("/uss/olp/qrm")
public class EgovQustnrRespondInfoContoller {

    @GetMapping(value="/index")
    public String index(QustnrRespondInfoVO qustnrRespondInfoVO, Model model) {
        return this.qustnrRespondInfoListView(qustnrRespondInfoVO, model);
    }

    @RequestMapping(value="/qustnrRespondInfoListView", method={RequestMethod.GET, RequestMethod.POST})
    public String qustnrRespondInfoListView(QustnrRespondInfoVO qustnrRespondInfoVO, Model model) {
        model.addAttribute("qustnrRespondInfoVO", qustnrRespondInfoVO);
        return "uss/olp/qrm/qustnrRespondInfoList";
    }

    @PostMapping(value="/qustnrRespondInfoDetailView")
    public String qustnrRespondInfoDetailView(QustnrRespondInfoVO qustnrRespondInfoVO, Model model) {
        model.addAttribute("qustnrRespondInfoVO", qustnrRespondInfoVO);
        return "uss/olp/qrm/qustnrRespondInfoDetail";
    }

    @PostMapping(value="/qustnrRespondInfoInsertView")
    public String qustnrRespondInfoInsertView(QustnrRespondInfoVO qustnrRespondInfoVO, Model model) {
        model.addAttribute("qustnrRespondInfoVO", qustnrRespondInfoVO);
        return "uss/olp/qrm/qustnrRespondInfoInsert";
    }

    @PostMapping(value="/qustnrRespondInfoUpdateView")
    public String qustnrRespondInfoUpdateView(QustnrRespondInfoVO qustnrRespondInfoVO, Model model) {
        model.addAttribute("qustnrRespondInfoVO", qustnrRespondInfoVO);
        return "uss/olp/qrm/qustnrRespondInfoUpdate";
    }

}
