package egovframework.com.cop.bls.web;

import egovframework.com.cop.bls.service.*;
import egovframework.com.pagination.EgovPaginationFormat;
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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("blsEgovBbsMasterAPIController")
@RequestMapping("/cop/bls")
@RequiredArgsConstructor
public class EgovBbsMasterAPIController {

    @Value("${egov.page.unit}")
    private int pageUnit;

    @Value("${egov.page.size}")
    private int pageSize;

    private final EgovBbsMasterService service;
    private final EgovCmmnDetailCodeService cmmnDetailCodeService;
    private final EgovEnvCryptoServiceImpl egovEnvCryptoService;

    @PostMapping(value="/bbsMasterList")
    public ResponseEntity<?> bbsMasterList(@ModelAttribute BbsMasterVO bbsMasterVO) {
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(bbsMasterVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(pageUnit);
        paginationInfo.setPageSize(pageSize);

        bbsMasterVO.setFirstIndex(paginationInfo.getCurrentPageNo()-1);
        bbsMasterVO.setLastIndex(paginationInfo.getLastRecordIndex());
        bbsMasterVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Page<BbsMasterDTO> list = service.list(bbsMasterVO);
        paginationInfo.setTotalRecordCount((int) list.getTotalElements());

        EgovPaginationFormat egovPaginationFormat = new EgovPaginationFormat();
        String pagination = egovPaginationFormat.paginationFormat(paginationInfo, "linkPage");

        Map<String, Object> response = new HashMap<>();
        response.put("bbsMasterList", list.getContent());
        response.put("pagination", pagination);
        response.put("lineNumber", (bbsMasterVO.getPageIndex()-1)*pageSize);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value="/bbsMasterDetail")
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

    @PostMapping(value="/bbsMasterInsert")
    public ResponseEntity<?> bbsMasterInsert(@Valid @ModelAttribute BbsMasterVO bbsMasterVO, BindingResult bindingResult, HttpServletRequest request) throws FdlException {
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
        BbsMasterVO result = service.insert(bbsMasterVO, userInfo);

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(result)) {
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value="/bbsMasterUpdate")
    public ResponseEntity<?> bbsMasterUpdate(@Valid @ModelAttribute BbsMasterVO bbsMasterVO, BindingResult bindingResult,  HttpServletRequest request) throws FdlException {
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
        BbsMasterVO result = service.update(bbsMasterVO, userInfo);

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(result)) {
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
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
