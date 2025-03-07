package egovframework.com.sec.ram.web;

import egovframework.com.pagination.EgovPaginationFormat;
import egovframework.com.sec.ram.service.AuthorRoleRelatedVO;
import egovframework.com.sec.ram.service.EgovAuthorRoleService;
import egovframework.com.sec.ram.service.RoleInfoDTO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller("ramEgovRoleInfoAPIController")
@RequestMapping("/sec/ram")
@RequiredArgsConstructor
public class EgovRoleInfoAPIController {

    @Value("${egov.page.unit}")
    private int pageUnit;

    @Value("${egov.page.size}")
    private int pageSize;

    private final EgovAuthorRoleService service;

    @PostMapping(value="/roleInfoList")
    public ResponseEntity<?> roleInfoList(@ModelAttribute AuthorRoleRelatedVO authorRoleRelatedVO) {
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(authorRoleRelatedVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(pageUnit);
        paginationInfo.setPageSize(pageSize);

        authorRoleRelatedVO.setFirstIndex(paginationInfo.getCurrentPageNo()-1);
        authorRoleRelatedVO.setLastIndex(paginationInfo.getLastRecordIndex());
        authorRoleRelatedVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Page<RoleInfoDTO> list = service.list(authorRoleRelatedVO);
        paginationInfo.setTotalRecordCount((int) list.getTotalElements());

        EgovPaginationFormat egovPaginationFormat = new EgovPaginationFormat();
        String pagination = egovPaginationFormat.paginationFormat(paginationInfo, "linkPage");

        Map<String, Object> response = new HashMap<>();
        response.put("roleInfoList", list.getContent());
        response.put("pagination", pagination);
        response.put("lineNumber", (authorRoleRelatedVO.getPageIndex()-1)*pageSize);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value="/roleInfoInsert")
    public ResponseEntity<?> roleInfoInsert(
            @RequestParam String authorCodes,
            @RequestParam String roleCodes,
            @RequestParam String regYns,
            @ModelAttribute AuthorRoleRelatedVO authorRoleRelatedVO
    ) {
        String[] strAuthorCodes = authorCodes.split(";");
        String[] strRoleCodes = roleCodes.split(";");
        String[] strRegYns = regYns.split(";");

        boolean result = false;
        for (int i = 0; i < strAuthorCodes.length; i++) {
            authorRoleRelatedVO.setAuthorCode(strAuthorCodes[i]);
            authorRoleRelatedVO.setRoleCode(strRoleCodes[i]);

            if ("Y".equals(strRegYns[i])) {
                result = service.delete(authorRoleRelatedVO);
                service.insert(authorRoleRelatedVO);
            } else {
                result = service.delete(authorRoleRelatedVO);
            }
        }

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(result)) {
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

}
