package egovframework.com.uss.olp.qri.web;

import egovframework.com.uss.olp.qri.service.EgovQustnrQesitmService;
import egovframework.com.uss.olp.qri.service.QustnrQesitmItemDTO;
import egovframework.com.uss.olp.qri.service.QustnrRspnsResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("qriEgovQustnrQesitmController")
@RequestMapping("/uss/olp/qri")
@RequiredArgsConstructor
public class EgovQustnrQesitmAPIController {

    private final EgovQustnrQesitmService service;

    @PostMapping("/qustnrQesitmItemList")
    public ResponseEntity<?> qustnrQesitmItemList(@RequestBody QustnrRspnsResultVO qustnrRspnsResultVO) {
        List<QustnrQesitmItemDTO> qustnrQesitmItemList = service.qustnrQesitmItemList(qustnrRspnsResultVO);

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(qustnrQesitmItemList)) {
            response.put("status", "success");
            response.put("qustnrQesitmItemList", qustnrQesitmItemList);
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

}
