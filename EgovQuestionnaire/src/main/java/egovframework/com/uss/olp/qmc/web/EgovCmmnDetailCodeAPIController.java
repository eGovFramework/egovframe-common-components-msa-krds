package egovframework.com.uss.olp.qmc.web;

import egovframework.com.uss.olp.qmc.service.CmmnDetailCodeVO;
import egovframework.com.uss.olp.qmc.service.EgovCmmnDetailCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("qmcEgovCmmnDetailCodeController")
@RequestMapping("/uss/olp/qmc")
@RequiredArgsConstructor
public class EgovCmmnDetailCodeAPIController {

    private final EgovCmmnDetailCodeService service;

    @PostMapping(value="/cmmnDetailCodeList")
    public ResponseEntity<?> cmmnDetailCodeList() {
        List<CmmnDetailCodeVO> list = service.list();

        Map<String, Object> response = new HashMap<>();
        response.put("cmmnDetailCodeList", list);

        return ResponseEntity.ok(response);
    }

}
