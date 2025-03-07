package egovframework.com.sec.rgm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("rgmEgovAuthorGroupController")
@RequestMapping("/sec/rgm")
public class EgovAuthorGroupController {

    @GetMapping(value="/index")
    public String index() {
        return this.authorGroupListView();
    }

    @PostMapping(value="/authorGroupListView")
    public String authorGroupListView() {
        return "sec/rgm/authorGroupList";
    }

}
