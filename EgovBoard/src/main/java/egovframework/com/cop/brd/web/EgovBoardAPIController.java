package egovframework.com.cop.brd.web;

import egovframework.com.cop.brd.service.BbsVO;
import egovframework.com.cop.brd.service.BoardDTO;
import egovframework.com.cop.brd.service.EgovBoardService;
import egovframework.com.pagination.EgovPaginationFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.egovframe.boot.crypto.service.impl.EgovEnvCryptoServiceImpl;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("brdEgovBoardAPIController")
@RequestMapping("/cop/brd")
@RequiredArgsConstructor
@Slf4j
public class EgovBoardAPIController {

    @Value("${egov.page.unit}")
    private int pageUnit;

    @Value("${egov.page.size}")
    private int pageSize;

    private final EgovBoardService service;
    private final EgovEnvCryptoServiceImpl egovEnvCryptoService;

    @PostMapping(value = "/boardList")
    public ResponseEntity<?> boardList(@ModelAttribute BbsVO bbsVO, HttpServletRequest request) {

        bbsVO.setPageIndex((bbsVO.getPageIndex()));
        bbsVO.setFirstIndex(bbsVO.getPageIndex() - 1);
        bbsVO.setPageUnit(pageUnit);

        List<BoardDTO> noticeList = service.noticeList(bbsVO);
        int noticeCnt = noticeList.size();
        if (noticeCnt != 0) {
            bbsVO.setPageUnit(pageUnit - noticeCnt);
        }

        Map<String, String> userInfo = extracted(request);
        Map<String, Object> response = service.list(bbsVO);

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(bbsVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(pageUnit - noticeCnt);
        paginationInfo.setPageSize(pageSize);
        paginationInfo.setTotalRecordCount(((Long) response.get("totalElements")).intValue());

        EgovPaginationFormat egovPaginationFormat = new EgovPaginationFormat();
        String pagination = egovPaginationFormat.paginationFormat(paginationInfo, "linkPage");

        response.put("noticeList", noticeList);
        response.put("pagination", pagination);
        response.put("userId", userInfo.get("uniqId"));

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/boardDetail")
    public ResponseEntity<?> boardDetail(@ModelAttribute BbsVO bbsVO, HttpServletRequest request) {
        Map<String, String> userInfo = extracted(request);
        BoardDTO result = service.detail(bbsVO, userInfo);

        String uniqKey = egovEnvCryptoService.decrypt(request.getHeader("X-CODE-ID"));
        String encodeAtchFileId = egovEnvCryptoService.encrypt(result.getAtchFileId() + "|" + uniqKey);
        result.setAtchFileId(encodeAtchFileId);

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(result)) {
            response.put("status", "success");
            response.put("result", result);
            response.put("userId", userInfo.get("uniqId"));
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value = "/boardInsert")
    public ResponseEntity<?> boardInsert(
            @Valid @ModelAttribute BbsVO bbsVO,
            MultipartHttpServletRequest multiRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) throws Exception {
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

        List<MultipartFile> files = multiRequest.getFiles("fileList");
        log.debug("##### boardInsert >>> {}", files.size());

        Map<String, String> userInfo = extracted(request);
        BbsVO result = service.insert(bbsVO, files, userInfo);

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(result)) {
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value = "/boardUpdate")
    public ResponseEntity<?> boardUpdate(
            @Valid @ModelAttribute BbsVO bbsVO,
            MultipartHttpServletRequest multiRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) throws Exception {
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

        List<MultipartFile> files = multiRequest.getFiles("fileList");
        log.debug("##### boardInsert >>> {}", files.size());

        Map<String, String> userInfo = extracted(request);

        String decodeId = egovEnvCryptoService.decrypt(bbsVO.getAtchFileId());
        String decodeFileId = StringUtils.substringBefore(decodeId,"|");
        bbsVO.setAtchFileId(decodeFileId);

        BbsVO result = service.update(bbsVO, files, userInfo);

        Map<String, Object> response = new HashMap<>();
        if (!ObjectUtils.isEmpty(result)) {
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value = "/deleteBoard")
    public ResponseEntity<?> deleteBoard(@RequestBody BbsVO bbsVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        if (true) {
            service.delete(bbsVO);
        }

        return ResponseEntity.ok().body("게시글이 삭제되었습니다.");
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
