package egovframework.com.cop.brd.repository;

import egovframework.com.cop.brd.entity.Comment;
import egovframework.com.cop.brd.entity.CommentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("brdEgovCommentRepository")
public interface EgovCommentRepository extends JpaRepository<Comment, CommentId> {

    Page<Comment> findAllByCommentId_BbsIdAndCommentId_NttIdAndUseAt(String bbsId, Long nttId, String useAt, Pageable pageable);

    List<Comment> findAllByCommentId_BbsIdAndCommentId_NttId(String bbsId, Long nttId);

}
