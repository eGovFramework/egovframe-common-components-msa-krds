package egovframework.com.sym.ccm.cca.web;

import egovframework.com.sym.ccm.cca.service.CmmnCodeVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("ccaEgovCmmnCodeManageController")
@RequestMapping("/sym/ccm/cca")
public class EgovCmmnCodeManageController {

    @GetMapping(value="/index")
    public String index() {
        return this.cmmnCodeListView();
    }

    @RequestMapping(value="/cmmnCodeListView", method={RequestMethod.GET, RequestMethod.POST})
    public String cmmnCodeListView() {
        return "sym/ccm/cca/cmmnCodeList";
    }

    @PostMapping(value="/cmmnCodeDetailView")
    public String cmmnCodeDetailView(CmmnCodeVO cmmnCodeVO, Model model) {
        model.addAttribute("cmmnCodeVO", cmmnCodeVO);
        return "sym/ccm/cca/cmmnCodeDetail";
    }

    @PostMapping(value="/cmmnCodeInsertView")
    public String cmmnCodeInsertView(CmmnCodeVO cmmnCodeVO, Model model) {
        model.addAttribute("cmmnCodeVO", cmmnCodeVO);
        return "sym/ccm/cca/cmmnCodeInsert";
    }

    @PostMapping(value="/cmmnCodeUpdateView")
    public String cmmnCodeUpdateView(CmmnCodeVO cmmnCodeVO, Model model) {
        model.addAttribute("cmmnCodeVO", cmmnCodeVO);
        return "sym/ccm/cca/cmmnCodeUpdate";
    }

}
