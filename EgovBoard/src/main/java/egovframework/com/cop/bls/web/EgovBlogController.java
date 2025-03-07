package egovframework.com.cop.bls.web;

import egovframework.com.cop.bls.service.BlogVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("blsEgovBlogController")
@RequestMapping("/cop/bls")
public class EgovBlogController {

    @GetMapping(value="/index")
    public String index(BlogVO blogVO, Model model) {
        return this.blogListView(blogVO, model);
    }

    @RequestMapping(value="/blogListView", method={RequestMethod.GET, RequestMethod.POST})
    public String blogListView(BlogVO blogVO, Model model) {
        model.addAttribute("blogVO", blogVO);
        return "cop/bls/blogList";
    }

    @PostMapping(value="/blogDetailView")
    public String blogDetailView(BlogVO blogVO, Model model) {
        model.addAttribute("blogVO", blogVO);
        return "cop/bls/blogDetail";
    }

    @PostMapping(value="/blogInsertView")
    public String blogInsertView(BlogVO blogVO, Model model) {
        model.addAttribute("blogVO", blogVO);
        return "cop/bls/blogInsert";
    }

    @PostMapping(value="/blogUpdateView")
    public String blogUpdateView(BlogVO blogVO, Model model) {
        model.addAttribute("blogVO", blogVO);
        return "cop/bls/blogUpdate";
    }

}
