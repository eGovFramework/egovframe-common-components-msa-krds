package egovframework.com.sec.gmt.web;

import egovframework.com.pagination.EgovPaginationFormat;
import egovframework.com.sec.gmt.service.AuthorGroupInfoVO;
import egovframework.com.sec.gmt.service.EgovAuthorGroupInfoService;
import lombok.RequiredArgsConstructor;
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

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller("gmtEgovAuthorGroupInfoAPIController")
@RequestMapping("/sec/gmt")
@RequiredArgsConstructor
public class EgovAuthorGroupInfoAPIController {

    @Value("${egov.page.unit}")
    private int pageUnit;

    @Value("${egov.page.size}")
    private int pageSize;

    private final EgovAuthorGroupInfoService service;

    @PostMapping(value="/authorGroupInfoList")
    public ResponseEntity<?> authorGroupInfoList(@ModelAttribute AuthorGroupInfoVO authorGroupInfoVO) {
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(authorGroupInfoVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(pageUnit);
        paginationInfo.setPageSize(pageSize);

        authorGroupInfoVO.setFirstIndex(paginationInfo.getCurrentPageNo()-1);
        authorGroupInfoVO.setLastIndex(paginationInfo.getLastRecordIndex());
        authorGroupInfoVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Page<AuthorGroupInfoVO> list = service.list(authorGroupInfoVO);
        paginationInfo.setTotalRecordCount((int) list.getTotalElements());

        EgovPaginationFormat egovPaginationFormat = new EgovPaginationFormat();
        String pagination = egovPaginationFormat.paginationFormat(paginationInfo, "linkPage");

        Map<String, Object> response = new HashMap<>();
        response.put("authorGroupInfoList", list.getContent());
        response.put("pagination", pagination);
        response.put("lineNumber", (authorGroupInfoVO.getPageIndex()-1)*pageSize);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value="/authorGroupInfoDetail")
    public ResponseEntity<?> authorGroupInfoDetail(@ModelAttribute AuthorGroupInfoVO authorGroupInfoVO) {
        AuthorGroupInfoVO result = service.detail(authorGroupInfoVO);

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

    @PostMapping(value="/authorGroupInfoInsert")
    public ResponseEntity<?> authorGroupInfoInsert(@Valid @ModelAttribute AuthorGroupInfoVO authorGroupInfoVO, BindingResult bindingResult) throws FdlException {
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

        AuthorGroupInfoVO result = service.insert(authorGroupInfoVO);

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(result)) {
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value="/authorGroupInfoUpdate")
    public ResponseEntity<?> authorGroupInfoUpdate(@Valid @ModelAttribute AuthorGroupInfoVO authorGroupInfoVO, BindingResult bindingResult) {
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

        AuthorGroupInfoVO result = service.update(authorGroupInfoVO);

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(result)) {
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value="/authorGroupInfoDelete")
    public ResponseEntity<?> authorGroupInfoDelete(@ModelAttribute AuthorGroupInfoVO authorGroupInfoVO) {
        boolean result = service.delete(authorGroupInfoVO);

        Map<String, Object> response = new HashMap<>();
        if (result) {
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

}
