package egovframework.com.uss.olp.qqm.web;

import egovframework.com.uss.olp.qqm.service.CmmnDetailCodeVO;
import egovframework.com.uss.olp.qqm.service.EgovCmmnDetailCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("qqmEgovCmmnDetailCodeController")
@RequestMapping("/uss/olp/qqm")
@RequiredArgsConstructor
public class EgovCmmnDetailCodeAPIController {

    private final EgovCmmnDetailCodeService service;

    @PostMapping(value="/cmmnDetailCodeList")
    public ResponseEntity<?> cmmnDetailCodeList() {
        List<CmmnDetailCodeVO> cmmnDetailCodeList = service.list("COM018");

        Map<String, Object> response = new HashMap<>();
        response.put("cmmnDetailCodeList", cmmnDetailCodeList);

        return ResponseEntity.ok(response);
    }

}
