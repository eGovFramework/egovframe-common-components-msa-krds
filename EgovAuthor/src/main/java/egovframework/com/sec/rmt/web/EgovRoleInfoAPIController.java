package egovframework.com.sec.rmt.web;

import egovframework.com.pagination.EgovPaginationFormat;
import egovframework.com.sec.rmt.service.*;
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
import java.util.List;
import java.util.Map;

@Controller("rmtEgovRoleManageAPIController")
@RequestMapping("/sec/rmt")
@RequiredArgsConstructor
public class EgovRoleInfoAPIController {

    @Value("${egov.page.unit}")
    private int pageUnit;

    @Value("${egov.page.size}")
    private int pageSize;

    private final EgovRoleInfoService service;
    private final EgovCmmnDetailCodeService cmmnDetailCodeService;

    @PostMapping(value="/roleInfoList")
    public ResponseEntity<?> roleInfoList(@ModelAttribute RoleInfoVO roleInfoVO) {
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(roleInfoVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(pageUnit);
        paginationInfo.setPageSize(pageSize);

        roleInfoVO.setFirstIndex(paginationInfo.getCurrentPageNo()-1);
        roleInfoVO.setLastIndex(paginationInfo.getLastRecordIndex());
        roleInfoVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Page<RoleInfoDTO> list = service.list(roleInfoVO);
        paginationInfo.setTotalRecordCount((int) list.getTotalElements());

        EgovPaginationFormat egovPaginationFormat = new EgovPaginationFormat();
        String pagination = egovPaginationFormat.paginationFormat(paginationInfo, "linkPage");

        Map<String, Object> response = new HashMap<>();
        response.put("roleInfoList", list.getContent());
        response.put("pagination", pagination);
        response.put("lineNumber", (roleInfoVO.getPageIndex()-1)*pageSize);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value="/roleInfoDetail")
    public ResponseEntity<?> roleInfoDetail(@ModelAttribute RoleInfoVO roleInfoVO) {
        RoleInfoVO result = service.detail(roleInfoVO);
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

    @PostMapping(value="/roleInfoInsert")
    public ResponseEntity<?> roleInfoInsert(@Valid @ModelAttribute RoleInfoVO roleInfoVO, BindingResult bindingResult) throws FdlException {
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

        RoleInfoVO result = service.insert(roleInfoVO);

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(result)) {
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value="/roleInfoUpdate")
    public ResponseEntity<?> roleInfoUpdate(@Valid @ModelAttribute RoleInfoVO roleInfoVO, BindingResult bindingResult) {
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

        RoleInfoVO result = service.update(roleInfoVO);

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(result)) {
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value="/roleInfoDelete")
    public ResponseEntity<?> roleInfoDelete(@ModelAttribute RoleInfoVO roleInfoVO) {
        service.delete(roleInfoVO);
        return ResponseEntity.ok("success");
    }

}
