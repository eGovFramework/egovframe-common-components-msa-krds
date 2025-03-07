package egovframework.com.sym.ccm.cde.web;

import egovframework.com.sym.ccm.cde.service.CmmnDetailCodeVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("cdeEgovCmmnDetailCodeManageController")
@RequestMapping("/sym/ccm/cde")
public class EgovCmmnDetailCodeManageController {

    @GetMapping(value="/index")
    public String index() {
        return this.cmmnDetailCodeListView();
    }

    @RequestMapping(value="/cmmnDetailCodeListView", method={RequestMethod.GET, RequestMethod.POST})
    public String cmmnDetailCodeListView() {
        return "sym/ccm/cde/cmmnDetailCodeList";
    }

    @PostMapping(value="/cmmnDetailCodeDetailView")
    public String cmmnDetailCodeDetailView(CmmnDetailCodeVO cmmnDetailCodeVO, Model model) {
        model.addAttribute("cmmnDetailCodeVO", cmmnDetailCodeVO);
        return "sym/ccm/cde/cmmnDetailCodeDetail";
    }

    @PostMapping(value="/cmmnDetailCodeInsertView")
    public String cmmnDetailCodeInsertView(CmmnDetailCodeVO cmmnDetailCodeVO, Model model) {
        model.addAttribute("cmmnDetailCodeVO", cmmnDetailCodeVO);
        return "sym/ccm/cde/cmmnDetailCodeInsert";
    }

    @PostMapping(value="/cmmnDetailCodeUpdateView")
    public String cmmnDetailCodeUpdateView(CmmnDetailCodeVO cmmnDetailCodeVO, Model model) {
        model.addAttribute("cmmnDetailCodeVO", cmmnDetailCodeVO);
        return "sym/ccm/cde/cmmnDetailCodeUpdate";
    }

}
