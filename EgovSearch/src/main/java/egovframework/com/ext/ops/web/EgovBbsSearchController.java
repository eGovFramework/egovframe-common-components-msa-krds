package egovframework.com.ext.ops.web;

import egovframework.com.ext.ops.service.BoardVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EgovBbsSearchController {

	@GetMapping(value="/")
    public String index() {
		return this.searchPageView();
	}

	@RequestMapping(value="/ext/ops/searchPageView", method={RequestMethod.GET, RequestMethod.POST})
    public String searchPageView() {
        return "ext/ops/bbsSearch";
    }

	@PostMapping(value="/ext/ops/textSearchResultView")
    public String textSearchResultView(BoardVO boardVO, Model model) {
        model.addAttribute("BoardVO", boardVO);
        return "ext/ops/textSearchResult";
    }

    @PostMapping(value="/ext/ops/vectorSearchResultView")
    public String vectorSearchResultView(BoardVO boardVO, Model model) {
        model.addAttribute("BoardVO", boardVO);
        return "ext/ops/vectorSearchResult";
    }

}
