package egovframework.com.sec.ram.web;

import egovframework.com.pagination.EgovPaginationFormat;
import egovframework.com.sec.ram.service.AuthorInfoVO;
import egovframework.com.sec.ram.service.EgovAuthorManageService;
import lombok.RequiredArgsConstructor;
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

@Controller("ramEgovAuthorInfoAPIController")
@RequestMapping("/sec/ram")
@RequiredArgsConstructor
public class EgovAuthorInfoAPIController {

    @Value("${egov.page.unit}")
    private int pageUnit;

    @Value("${egov.page.size}")
    private int pageSize;

    private final EgovAuthorManageService service;

    @PostMapping(value="/authorInfoList")
    public ResponseEntity<?> authorInfoList(@ModelAttribute AuthorInfoVO authorInfoVO) {
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(authorInfoVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(pageUnit);
        paginationInfo.setPageSize(pageSize);

        authorInfoVO.setFirstIndex(paginationInfo.getCurrentPageNo()-1);
        authorInfoVO.setLastIndex(paginationInfo.getLastRecordIndex());
        authorInfoVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Page<AuthorInfoVO> list = service.list(authorInfoVO);
        paginationInfo.setTotalRecordCount((int) list.getTotalElements());

        EgovPaginationFormat egovPaginationFormat = new EgovPaginationFormat();
        String pagination = egovPaginationFormat.paginationFormat(paginationInfo, "linkPage");

        Map<String, Object> response = new HashMap<>();
        response.put("authorInfoList", list.getContent());
        response.put("pagination", pagination);
        response.put("lineNumber", (authorInfoVO.getPageIndex()-1)*pageSize);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value="/authorInfoDetail")
    public ResponseEntity<?> authorInfoDetail(@ModelAttribute AuthorInfoVO authorInfoVO) {
        AuthorInfoVO result = service.detail(authorInfoVO);

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

    @PostMapping(value="/authorInfoInsert")
    public ResponseEntity<?> authorInfoInsert(@Valid @ModelAttribute AuthorInfoVO authorInfoVO, BindingResult bindingResult) {
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

        AuthorInfoVO result = service.insert(authorInfoVO);

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(result)) {
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value="/authorInfoUpdate")
    public ResponseEntity<?> authorInfoUpdate(@Valid @ModelAttribute AuthorInfoVO authorInfoVO, BindingResult bindingResult) {
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

        AuthorInfoVO result = service.update(authorInfoVO);

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(result)) {
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value="/authorInfoDelete")
    public ResponseEntity<?> authorInfoDelete(@ModelAttribute AuthorInfoVO authorInfoVO) {
        boolean result = service.delete(authorInfoVO);

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
