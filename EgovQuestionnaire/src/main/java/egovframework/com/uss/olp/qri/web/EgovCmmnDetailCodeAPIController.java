package egovframework.com.uss.olp.qri.web;

import egovframework.com.uss.olp.qri.service.CmmnDetailCodeVO;
import egovframework.com.uss.olp.qri.service.EgovCmmnDetailCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("qriEgovCmmnDetailCodeController")
@RequestMapping("/uss/olp/qri")
@RequiredArgsConstructor
public class EgovCmmnDetailCodeAPIController {

    private final EgovCmmnDetailCodeService service;

    @PostMapping(value="/cmmnDetailCodeList")
    public ResponseEntity<?> cmmnDetailCodeList() {
        List<CmmnDetailCodeVO> sexdstnList = service.list("COM014");
        List<CmmnDetailCodeVO> occpTyList = service.list("COM034");

        Map<String, Object> response = new HashMap<>();
        response.put("sexdstnList", sexdstnList);
        response.put("occpTyList", occpTyList);

        return ResponseEntity.ok(response);
    }

}
