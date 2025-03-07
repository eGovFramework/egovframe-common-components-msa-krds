package egovframework.com.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EgovMainManageController {

    @RequestMapping(value="/", method={RequestMethod.GET, RequestMethod.POST})
    public String index() {
        return "index";
    }

}
