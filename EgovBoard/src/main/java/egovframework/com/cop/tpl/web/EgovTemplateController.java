package egovframework.com.cop.tpl.web;

import egovframework.com.cop.tpl.service.TemplateVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("tplEgovTemplateController")
@RequestMapping("/cop/tpl")
public class EgovTemplateController {

    @GetMapping(value="/index")
    public String index(TemplateVO templateVO, ModelMap model) throws Exception {
        return this.selectTemplateView(templateVO, model);
    }

    @RequestMapping(value="/templateListView", method={RequestMethod.GET, RequestMethod.POST})
    public String selectTemplateView(TemplateVO templateVO, ModelMap model) {
        model.addAttribute("templateVO", templateVO);
        return "cop/tpl/templateList";
    }

    @PostMapping(value="/templateDetailView")
    public String detailTemplateView(TemplateVO templateVO, ModelMap model) {
        model.addAttribute("templateVO", templateVO);
        return "cop/tpl/templateUpdate";
    }

    @PostMapping(value="/templateInsertView")
    public String insertTemplateView(TemplateVO templateVO, ModelMap model) {
        model.addAttribute("templateVO", templateVO);
        return "cop/tpl/templateInsert";
    }

}
