package egovframework.com.sym.ccm.cde.web;

import egovframework.com.sym.ccm.cde.service.CmmnCodeVO;
import egovframework.com.sym.ccm.cde.service.EgovCmmnCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("cdeEgovCmmnCodeController")
@RequestMapping("/sym/ccm/cde")
@RequiredArgsConstructor
public class EgovCmmnCodeAPIController {

    private final EgovCmmnCodeService service;

    @PostMapping(value="/cmmnCodeList")
    public ResponseEntity<?> cmmnCodeList(@RequestParam String clCode) {
        List<CmmnCodeVO> list = service.list(clCode);

        Map<String, Object> response = new HashMap<>();
        response.put("cmmnCodeList", list);

        return ResponseEntity.ok(response);
    }

}
