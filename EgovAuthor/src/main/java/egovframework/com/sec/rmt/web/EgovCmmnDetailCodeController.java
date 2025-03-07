package egovframework.com.sec.rmt.web;

import egovframework.com.sec.rmt.service.CmmnDetailCodeVO;
import egovframework.com.sec.rmt.service.EgovCmmnDetailCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("rmtEgovCmmnDetailCodeController")
@RequestMapping("/sec/rmt")
@RequiredArgsConstructor
public class EgovCmmnDetailCodeController {

    private final EgovCmmnDetailCodeService service;

    @PostMapping(value="/cmmnDetailCodeList")
    public ResponseEntity<?> cmmnDetailCodeList() {
        List<CmmnDetailCodeVO> list = service.list();

        Map<String, Object> response = new HashMap<>();
        response.put("cmmnDetailCodeList", list);

        return ResponseEntity.ok(response);
    }

}
