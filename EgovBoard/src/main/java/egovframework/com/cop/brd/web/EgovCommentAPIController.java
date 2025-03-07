package egovframework.com.cop.brd.web;

import egovframework.com.cop.brd.service.CommentVO;
import egovframework.com.cop.brd.service.StsfdgVO;
import egovframework.com.cop.brd.service.impl.EgovCommentServiceImpl;
import egovframework.com.cop.brd.service.impl.EgovStsfdgServiceImpl;
import egovframework.com.pagination.EgovPaginationFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.boot.crypto.service.impl.EgovEnvCryptoServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller("brdEgovCommentAPIController")
@RequestMapping("/cop/brd")
@RequiredArgsConstructor
@Slf4j
public class EgovCommentAPIController {

    @Value("${egov.page.unit}")
    private int pageUnit;

    @Value("${egov.page.size}")
    private int pageSize;

    private final EgovCommentServiceImpl articleCommentService;
    private final EgovStsfdgServiceImpl bbsStsfdgService;
    private final EgovEnvCryptoServiceImpl egovEnvCryptoService;

    @PostMapping("/selectCommentList")
    public ResponseEntity<?> selectCommentList(@RequestBody CommentVO commentVO) {
        pageUnit = 3;
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(commentVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(pageUnit);
        paginationInfo.setPageSize(pageSize);

        commentVO.setFirstIndex(paginationInfo.getCurrentPageNo() - 1);
        commentVO.setLastIndex(paginationInfo.getLastRecordIndex());
        commentVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Page<CommentVO> response = articleCommentService.selectArticleCommentList(commentVO);
        paginationInfo.setTotalRecordCount((int) response.getTotalElements());
        EgovPaginationFormat egovPaginationFormat = new EgovPaginationFormat();
        egovPaginationFormat.paginationFormat(paginationInfo, "linkPage");

        Map<String, Object> result = new HashMap<>();
        result.put("response", response);

        EgovPaginationFormat paginationFormat = new EgovPaginationFormat();
        String paginationHtml = paginationFormat.paginationFormat(paginationInfo, "Comment_linkPage");

        result.put("pagination", paginationHtml);
        result.put("lineNumber", (commentVO.getPageIndex() - 1) * commentVO.getPageSize());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/insertComment")
    public ResponseEntity<?> insertComment(@Valid @RequestBody CommentVO commentVO, BindingResult bindingResult, HttpServletRequest request) throws FdlException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        log.debug("댓글 >> " + commentVO.getAnswer());

        Map<String, String> userInfo = extracted(request);
        if (!commentVO.getAnswer().isEmpty()) {
            articleCommentService.insertArticleComment(commentVO, userInfo);
        }

        return ResponseEntity.ok().body("댓글이 등록되었습니다.");
    }

    @PostMapping("/deleteComment")
    public ResponseEntity<?> deleteComment(@Valid @RequestBody CommentVO commentVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        articleCommentService.deleteArticleComment(commentVO);

        return ResponseEntity.ok().body("댓글이 삭제되었습니다.");
    }

    @PostMapping("/selectStsfdgList")
    public ResponseEntity<?> selectStsfdgList(@RequestBody StsfdgVO stsfdgVO) {
        pageUnit = 3;
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(stsfdgVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(pageUnit);
        paginationInfo.setPageSize(pageSize);

        stsfdgVO.setFirstIndex(paginationInfo.getCurrentPageNo() - 1);
        stsfdgVO.setLastIndex(paginationInfo.getLastRecordIndex());
        stsfdgVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Map<String, Object> map = bbsStsfdgService.selectStsfdgList(stsfdgVO);
        Page<StsfdgVO> response = (Page<StsfdgVO>) map.get("sList");
        paginationInfo.setTotalRecordCount((int) response.getTotalElements());
        EgovPaginationFormat egovPaginationFormat = new EgovPaginationFormat();
        egovPaginationFormat.paginationFormat(paginationInfo, "linkPage");

        Map<String, Object> result = new HashMap<>();
        result.put("response", response);
        result.put("satisAvr", map.get("stsfdgAverage"));

        EgovPaginationFormat paginationFormat = new EgovPaginationFormat();
        String paginationHtml = paginationFormat.paginationFormat(paginationInfo, "Satisfaction_linkPage");

        result.put("pagination", paginationHtml);
        result.put("lineNumber", (stsfdgVO.getPageIndex() - 1) * stsfdgVO.getPageSize());


        return ResponseEntity.ok(result);
    }

    @PostMapping("/insertStsfdg")
    public ResponseEntity<?> insertStsfdg(@Valid @RequestBody StsfdgVO stsfdgVO, BindingResult bindingResult, HttpServletRequest request) throws FdlException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        } else {
            if (stsfdgVO.getStsfdg() >= 0) {
                Map<String, String> userInfo = extracted(request);
                bbsStsfdgService.insertStsfdg(stsfdgVO, userInfo);
            }
        }

        return ResponseEntity.ok().body("등록이 완료되었습니다.");
    }

    @Transactional
    @PostMapping("/deleteStsfdg")
    public ResponseEntity<?> deleteStsfdg(@RequestBody StsfdgVO stsfdgVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        } else {
            if (!stsfdgVO.getStsfdgNo().isEmpty()) {
                bbsStsfdgService.deleteStsfdg(stsfdgVO.getStsfdgNo());
            }
        }
        return ResponseEntity.ok().body("삭제되었습니다.");
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
