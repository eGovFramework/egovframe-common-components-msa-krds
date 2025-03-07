package egovframework.com.cop.brd.service;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface EgovCommentService {

    Page<CommentVO> selectArticleCommentList(CommentVO commentVO);

    void insertArticleComment(CommentVO commentVO, Map<String, String> userInfo) throws FdlException;

    void deleteArticleComment(CommentVO commentVO);

}
