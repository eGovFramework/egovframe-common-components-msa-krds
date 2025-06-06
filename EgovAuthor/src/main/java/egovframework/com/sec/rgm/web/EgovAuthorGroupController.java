package egovframework.com.sec.rgm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("rgmEgovAuthorGroupController")
@RequestMapping("/sec/rgm")
public class EgovAuthorGroupController {

    @GetMapping(value="/index")
    public String index() {
        return this.authorGroupListView();
    }

    @RequestMapping(value="/authorGroupListView", method={RequestMethod.GET, RequestMethod.POST})
    public String authorGroupListView() {
        return "sec/rgm/authorGroupList";
    }

}
