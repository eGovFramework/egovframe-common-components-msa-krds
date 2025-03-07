package egovframework.com.cop.bls.web;

import egovframework.com.cop.bls.service.BlogDTO;
import egovframework.com.cop.bls.service.BlogVO;
import egovframework.com.cop.bls.service.EgovBlogService;
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
import java.util.Map;

@Controller("blsEgovBlogAPIController")
@RequestMapping("/cop/bls")
@RequiredArgsConstructor
public class EgovBlogAPIController {

    @Value("${egov.page.unit}")
    private int pageUnit;

    @Value("${egov.page.size}")
    private int pageSize;

    private final EgovBlogService service;
    private final EgovEnvCryptoServiceImpl egovEnvCryptoService;

    @PostMapping(value="/blogList")
    public ResponseEntity<?> blogList(@ModelAttribute BlogVO blogVO) {
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(blogVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(pageUnit);
        paginationInfo.setPageSize(pageSize);

        blogVO.setFirstIndex(paginationInfo.getCurrentPageNo()-1);
        blogVO.setLastIndex(paginationInfo.getLastRecordIndex());
        blogVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Page<BlogDTO> list = service.list(blogVO);
        paginationInfo.setTotalRecordCount((int) list.getTotalElements());

        EgovPaginationFormat egovPaginationFormat = new EgovPaginationFormat();
        String pagination = egovPaginationFormat.paginationFormat(paginationInfo, "linkPage");

        Map<String, Object> response = new HashMap<>();
        response.put("blogList", list.getContent());
        response.put("pagination", pagination);
        response.put("lineNumber", (blogVO.getPageIndex()-1)*pageSize);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value="/blogDetail")
    public ResponseEntity<?> blogDetail(@ModelAttribute BlogVO blogVO) {
        BlogDTO result = service.detail(blogVO);

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

    @PostMapping(value="/blogInsert")
    public ResponseEntity<?> blogInsert(@Valid @ModelAttribute BlogVO blogVO, BindingResult bindingResult, HttpServletRequest request) throws FdlException {
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
        BlogVO result = service.insert(blogVO, userInfo);

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(result)) {
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value="/blogUpdate")
    public ResponseEntity<?> blogUpdate(@Valid @ModelAttribute BlogVO blogVO, BindingResult bindingResult, HttpServletRequest request) {
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
        BlogVO result = service.update(blogVO, userInfo);

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
