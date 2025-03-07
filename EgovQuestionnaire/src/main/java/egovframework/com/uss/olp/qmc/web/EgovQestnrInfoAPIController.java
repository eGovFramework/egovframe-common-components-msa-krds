package egovframework.com.uss.olp.qmc.web;

import egovframework.com.pagination.EgovPaginationFormat;
import egovframework.com.uss.olp.qmc.service.*;
import lombok.RequiredArgsConstructor;
import org.egovframe.boot.crypto.service.impl.EgovEnvCryptoServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("qmcEgovQestnrInfoAPIController")
@RequestMapping("/uss/olp/qmc")
@RequiredArgsConstructor
public class EgovQestnrInfoAPIController {

    @Value("${egov.page.unit}")
    private int pageUnit;

    @Value("${egov.page.size}")
    private int pageSize;

    private final EgovQestnrInfoService service;
    private final EgovCmmnDetailCodeService cmmnDetailCodeService;
    private final EgovQustnrTmplatService qustnrTmplatService;
    private final EgovQustnrQesitmService qustnrQesitmService;
    private final EgovQustnrIemService qustnrIemService;
    private final EgovQustnrRspnsResultService qustnrRspnsResultService;
    private final EgovEnvCryptoServiceImpl egovEnvCryptoService;

    @PostMapping("/qestnrInfoList")
    public ResponseEntity<?> qestnrInfoList(@ModelAttribute QestnrInfoVO qestnrInfoVO) {
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(qestnrInfoVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(pageUnit);
        paginationInfo.setPageSize(pageSize);

        qestnrInfoVO.setFirstIndex(paginationInfo.getCurrentPageNo()-1);
        qestnrInfoVO.setLastIndex(paginationInfo.getLastRecordIndex());
        qestnrInfoVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Page<QestnrInfoDTO> list = service.list(qestnrInfoVO);
        paginationInfo.setTotalRecordCount((int) list.getTotalElements());

        EgovPaginationFormat egovPaginationFormat = new EgovPaginationFormat();
        String pagination = egovPaginationFormat.paginationFormat(paginationInfo, "linkPage");

        Map<String, Object> response = new HashMap<>();
        response.put("qestnrInfoList", list.getContent());
        response.put("pagination", pagination);
        response.put("lineNumber", (qestnrInfoVO.getPageIndex()-1)*pageSize);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value="/qestnrInfoDetail")
    public ResponseEntity<?> qestnrInfoDetail(@ModelAttribute QestnrInfoVO qestnrInfoVO) {
        QestnrInfoDTO result = service.detail(qestnrInfoVO);
        List<CmmnDetailCodeVO> cmmnDetailCodeList = cmmnDetailCodeService.list();
        List<QustnrTmplatVO> qustnrTmplatList = qustnrTmplatService.list();

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(result)) {
            response.put("status", "success");
            response.put("result", result);
            response.put("cmmnDetailCodeList", cmmnDetailCodeList);
            response.put("qustnrTmplatList", qustnrTmplatList);
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value="/qestnrInfoInsert")
    public ResponseEntity<?> qestnrInfoInsert(@Valid @ModelAttribute QestnrInfoVO qestnrInfoVO, BindingResult bindingResult, HttpServletRequest request) throws FdlException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            Map<String, Object> response = new HashMap<>();
            response.put("status", "valid");
            response.put("errors", errors);
            return ResponseEntity.ok(response);
        }

        Map<String, String> userInfo = extracted(request);
        QestnrInfoVO result = service.insert(qestnrInfoVO, userInfo);

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(result)) {
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value="/qestnrInfoUpdate")
    public ResponseEntity<?> qestnrInfoUpdate(@Valid @ModelAttribute QestnrInfoVO qestnrInfoVO, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            Map<String, Object> response = new HashMap<>();
            response.put("status", "valid");
            response.put("errors", errors);
            return ResponseEntity.ok(response);
        }

        Map<String, String> userInfo = extracted(request);
        QestnrInfoVO result = service.update(qestnrInfoVO, userInfo);

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(result)) {
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value="/qestnrInfoDelete")
    public ResponseEntity<?> qestnrInfoDelete(@ModelAttribute QestnrInfoVO qestnrInfoVO) {
        boolean result = service.delete(qestnrInfoVO);

        Map<String, Object> response = new HashMap<>();
        if (result) {
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/qustnrRspnsResultStats")
    public ResponseEntity<?> qustnrRspnsResultStats(@RequestBody QestnrInfoVO qestnrInfoVO) {
        QestnrInfoDTO qestnrInfo = service.detail(qestnrInfoVO);
        List<QustnrQesitmDTO> qustnrQesitmList = qustnrQesitmService.qustnrQesitmList(qestnrInfoVO);
        List<QustnrIemDTO> qustnrIemList = qustnrIemService.qustnrIemList(qestnrInfoVO);
        List<QustnrRspnsResultMCStatsDTO> qustnrRspnsResultMCStats = qustnrRspnsResultService.qustnrRspnsResultMCStats(qestnrInfoVO);
        List<QustnrRspnsResultESStatsDTO> qustnrRspnsResultESStats = qustnrRspnsResultService.qustnrRspnsResultESStats(qestnrInfoVO);

        Map<String, Object> response = new HashMap<>();
        response.put("qestnrInfo", qestnrInfo);
        response.put("qustnrQesitmList", qustnrQesitmList);
        response.put("qustnrIemList", qustnrIemList);
        response.put("qustnrRspnsResultMCStats", qustnrRspnsResultMCStats);
        response.put("qustnrRspnsResultESStats", qustnrRspnsResultESStats);

        return ResponseEntity.ok(response);
    }

    private Map<String, String> extracted(HttpServletRequest request) {
        Map<String, String> userInfo = new HashMap<>();

        String encryptUserId = request.getHeader("X-USER-ID");
        String encryptUserNm = request.getHeader("X-USER-NM");
        String encryptUniqId = request.getHeader("X-UNIQ-ID");

        userInfo.put("userId", egovEnvCryptoService.decrypt(encryptUserId));
        userInfo.put("userName", egovEnvCryptoService.decrypt(encryptUserNm));
        userInfo.put("uniqId", egovEnvCryptoService.decrypt(encryptUniqId));

        return userInfo;
    }

}
