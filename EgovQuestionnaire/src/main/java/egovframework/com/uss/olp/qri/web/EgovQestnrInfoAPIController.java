package egovframework.com.uss.olp.qri.web;

import egovframework.com.uss.olp.qri.service.EgovQestnrInfoService;
import egovframework.com.uss.olp.qri.service.QestnrInfoDTO;
import egovframework.com.uss.olp.qri.service.QustnrRspnsResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller("qriEgovQestnrInfoAPIController")
@RequestMapping("/uss/olp/qri/")
@RequiredArgsConstructor
public class EgovQestnrInfoAPIController {

    private final EgovQestnrInfoService service;

    @PostMapping(value="/qestnrInfoDetail")
    public ResponseEntity<?> qestnrInfoDetail(@ModelAttribute QustnrRspnsResultVO qustnrRspnsResultVO) {
        QestnrInfoDTO result = service.detail(qustnrRspnsResultVO);

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(result)) {
            response.put("status", "success");
            response.put("result", result);
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

}
