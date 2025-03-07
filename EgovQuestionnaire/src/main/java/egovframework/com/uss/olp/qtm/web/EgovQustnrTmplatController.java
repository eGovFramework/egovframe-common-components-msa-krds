package egovframework.com.uss.olp.qtm.web;

import egovframework.com.uss.olp.qtm.service.QustnrTmplatVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("qtmEgovQustnrTmplatController")
@RequestMapping("/uss/olp/qtm")
public class EgovQustnrTmplatController {

    @GetMapping(value="/index")
    public String index(QustnrTmplatVO qustnrTmplatVO, Model model) {
        return this.qustnrTmplatListView(qustnrTmplatVO, model);
    }

    @RequestMapping(value="/qustnrTmplatListView", method={RequestMethod.GET, RequestMethod.POST})
    public String qustnrTmplatListView(QustnrTmplatVO qustnrTmplatVO, Model model) {
        model.addAttribute("qustnrTmplatVO", qustnrTmplatVO);
        return "uss/olp/qtm/qustnrTmplatList";
    }

    @PostMapping(value="/qustnrTmplatDetailView")
    public String qustnrTmplatDetailView(QustnrTmplatVO qustnrTmplatVO, Model model) {
        model.addAttribute("qustnrTmplatVO", qustnrTmplatVO);
        return "uss/olp/qtm/qustnrTmplatDetail";
    }

    @PostMapping(value="/qustnrTmplatInsertView")
    public String qustnrTmplatInsertView(QustnrTmplatVO qustnrTmplatVO, Model model) {
        qustnrTmplatVO.setQustnrTmplatPathNm("/egovframework/com/uss/olp/qri/template/template");
        model.addAttribute("qustnrTmplatVO", qustnrTmplatVO);
        return "uss/olp/qtm/qustnrTmplatInsert";
    }

    @PostMapping(value="/qustnrTmplatUpdateView")
    public String qustnrTmplatUpdateView(QustnrTmplatVO qustnrTmplatVO, Model model) {
        model.addAttribute("qustnrTmplatVO", qustnrTmplatVO);
        return "uss/olp/qtm/qustnrTmplatUpdate";
    }

}
