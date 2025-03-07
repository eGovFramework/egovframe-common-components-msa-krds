package egovframework.com.uss.olp.qmc.web;

import egovframework.com.uss.olp.qmc.service.EgovQustnrTmplatService;
import egovframework.com.uss.olp.qmc.service.QustnrTmplatVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("qmcEgovQustnrTmplatController")
@RequestMapping("/uss/olp/qmc")
@RequiredArgsConstructor
public class EgovQustnrTmplatAPIController {

    private final EgovQustnrTmplatService service;

    @PostMapping(value="/qustnrTmplatList")
    public ResponseEntity<?> qustnrTmplatList() {
        List<QustnrTmplatVO> list = service.list();

        Map<String, Object> response = new HashMap<>();
        response.put("qustnrTmplatList", list);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value="/qustnrTmplatImage")
    public void qustnrTmplatImage(HttpServletResponse response, @RequestParam("qustnrTmplatId") String qustnrTmplatId) throws IOException {
        byte[] image = service.getImage(qustnrTmplatId);
        response.setContentType("image/png");
        response.setContentLength(image.length);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.getOutputStream().write(image);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

}
