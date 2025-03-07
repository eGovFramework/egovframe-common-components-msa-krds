package egovframework.com.sym.ccm.ccc.web;

import egovframework.com.sym.ccm.ccc.service.CmmnClCodeVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("cccEgovCmmnClCodeManageController")
@RequestMapping("/sym/ccm/ccc")
public class EgovCmmnClCodeManageController {

    @GetMapping(value="/index")
    public String index() {
        return this.cmmnClCodeListView();
    }

    @RequestMapping(value="/cmmnClCodeListView", method={RequestMethod.GET, RequestMethod.POST})
    public String cmmnClCodeListView() {
        return "sym/ccm/ccc/cmmnClCodeList";
    }

    @PostMapping(value="/cmmnClCodeDetailView")
    public String cmmnClCodeDetailView(CmmnClCodeVO cmmnClCodeVO, Model model) {
        model.addAttribute("cmmnClCodeVO", cmmnClCodeVO);
        return "sym/ccm/ccc/cmmnClCodeDetail";
    }

    @PostMapping(value="/cmmnClCodeInsertView")
    public String cmmnClCodeInsertView(CmmnClCodeVO cmmnClCodeVO, Model model) {
        model.addAttribute("cmmnClCodeVO", cmmnClCodeVO);
        return "sym/ccm/ccc/cmmnClCodeInsert";
    }

    @PostMapping(value="/cmmnClCodeUpdateView")
    public String cmmnClCodeUpdateView(CmmnClCodeVO cmmnClCodeVO, Model model) {
        model.addAttribute("cmmnClCodeVO", cmmnClCodeVO);
        return "sym/ccm/ccc/cmmnClCodeUpdate";
    }

}
