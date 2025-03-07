package egovframework.com.cop.cmy.web;

import egovframework.com.cop.cmy.service.CmmnDetailCodeVO;
import egovframework.com.cop.cmy.service.EgovCmmnDetailCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("cmyEgovCmmnDetailCodeController")
@RequestMapping("/cop/cmy")
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
