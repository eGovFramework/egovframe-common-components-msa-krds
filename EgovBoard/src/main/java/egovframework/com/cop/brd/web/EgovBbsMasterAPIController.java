package egovframework.com.cop.brd.web;

import egovframework.com.cop.bbs.service.CmmnDetailCodeVO;
import egovframework.com.cop.bbs.service.EgovCmmnDetailCodeService;
import egovframework.com.cop.brd.service.BbsMasterDTO;
import egovframework.com.cop.brd.service.BbsMasterOptnVO;
import egovframework.com.cop.brd.service.BbsMasterVO;
import egovframework.com.cop.brd.service.EgovBbsMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("brdEgovBbsMasterAPIController")
@RequestMapping("/cop/brd")
@RequiredArgsConstructor
public class EgovBbsMasterAPIController {

    private final EgovBbsMasterService service;
    private final EgovCmmnDetailCodeService cmmnDetailCodeService;

    @PostMapping(value = "/bbsMasterDetail")
    public ResponseEntity<?> bbsMasterDetail(@ModelAttribute BbsMasterVO bbsMasterVO) {
        BbsMasterDTO result = service.detail(bbsMasterVO);
        List<CmmnDetailCodeVO> list = cmmnDetailCodeService.list();

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(result)) {
            response.put("status", "success");
            response.put("result", result);
            response.put("cmmnDetailCodeList", list);
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/selectBBSMasterOptn")
    public ResponseEntity<?> selectBBSMasterOptn(String bbsId) {
        System.out.println("게시판 아이디  >> " + bbsId);
        BbsMasterOptnVO bbsMasterOptnVO = service.selectBBSMasterOptn(bbsId);
        System.out.println("컨트롤러 >> " + bbsMasterOptnVO.getBbsId());

        if (bbsMasterOptnVO == null) {
            return ResponseEntity.noContent().build(); // HTTP 204 No Content 응답
        } else {
            System.out.println("성공 >> " + bbsMasterOptnVO.getBbsId());
            return ResponseEntity.ok(bbsMasterOptnVO);
        }
    }

}
