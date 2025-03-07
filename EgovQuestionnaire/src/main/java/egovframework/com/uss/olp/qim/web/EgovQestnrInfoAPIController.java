package egovframework.com.uss.olp.qim.web;

import egovframework.com.pagination.EgovPaginationFormat;
import egovframework.com.uss.olp.qim.service.EgovQestnrInfoService;
import egovframework.com.uss.olp.qim.service.QestnrInfoDTO;
import egovframework.com.uss.olp.qim.service.QestnrInfoVO;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller("qimEgovQestnrInfoController")
@RequestMapping("/uss/olp/qim")
@RequiredArgsConstructor
public class EgovQestnrInfoAPIController {

    private final EgovQestnrInfoService service;

    @PostMapping(value="/qestnrInfoList")
    public ResponseEntity<?> qestnrInfoList(@RequestBody QestnrInfoVO qestnrInfoVO) {
        int pageUnit = 5;
        int pageSize = 5;

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
        String pagination = egovPaginationFormat.paginationFormat(paginationInfo, "qestnrInfoListLinkPage");

        Map<String, Object> response = new HashMap<>();
        response.put("qestnrInfoList", list.getContent());
        response.put("pagination", pagination);
        response.put("lineNumber", (qestnrInfoVO.getPageIndex()-1)*pageSize);

        return ResponseEntity.ok(response);
    }

}
