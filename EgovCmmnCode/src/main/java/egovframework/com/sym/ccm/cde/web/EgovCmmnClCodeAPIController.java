package egovframework.com.sym.ccm.cde.web;

import egovframework.com.sym.ccm.cde.service.CmmnClCodeVO;
import egovframework.com.sym.ccm.cde.service.EgovCmmnClCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("cdeEgovCmmnClCodeAPIController")
@RequestMapping("/sym/ccm/cde")
@RequiredArgsConstructor
public class EgovCmmnClCodeAPIController {

    private final EgovCmmnClCodeService service;

    @PostMapping(value="/cmmnClCodeList")
    public ResponseEntity<?> cmmnClCodeList() {
        List<CmmnClCodeVO> list = service.list();

        Map<String, Object> response = new HashMap<>();
        response.put("cmmnClCodeList", list);

        return ResponseEntity.ok(response);
    }

}
