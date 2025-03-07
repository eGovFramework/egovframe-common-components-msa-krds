package egovframework.com.cop.brd.repository;

import egovframework.com.cop.brd.entity.Bbs;
import egovframework.com.cop.brd.entity.BbsId;
import egovframework.com.cop.brd.service.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository("brdEgovBoardRepository")
public interface EgovBoardRepository extends JpaRepository<Bbs, BbsId> {

    @Query("SELECT new egovframework.com.cop.brd.service.BoardDTO( " +
            "a.bbsId.nttId, " +
            "a.bbsId.bbsId, " +
            "a.nttNo," +
            "a.nttSj, " +
            "a.nttCn, " +
            "a.frstRegisterId, " +
            "COALESCE(b.userNm, a.ntcrNm), " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.rdcnt, " +
            "a.parntscttNo, " +
            "a.answerAt, " +
            "a.answerLc, " +
            "a.sortOrdr, " +
            "a.useAt, " +
            "a.atchFileId, " +
            "a.ntceBgnde, " +
            "a.ntceEndde, " +
            "a.ntcrId, " +
            "a.ntcrNm, " +
            "a.sjBoldAt, " +
            "a.noticeAt, " +
            "a.secretAt, " +
            "0, " +
            "c.bbsNm " +
            ") " +
            "FROM brdBbs a " +
            "LEFT OUTER JOIN brdUserMaster b " +
            "ON a.frstRegisterId = b.esnlId " +
            "LEFT OUTER JOIN brdBbsMaster c " +
            "ON a.bbsId.bbsId = c.bbsId " +
            "LEFT OUTER JOIN brdComment d " +
            "ON a.bbsId.nttId = d.commentId.nttId " +
            "AND a.bbsId.bbsId = d.commentId.bbsId " +
            "WHERE a.bbsId.bbsId = :bbsId " +
            "AND a.useAt = 'Y' AND a.noticeAt = null " +
            "AND ( " +
            "(:searchCondition = '1' AND (:searchKeyword IS NULL OR a.nttSj LIKE %:searchKeyword%)) OR " +
            "(:searchCondition = '2' AND (:searchKeyword IS NULL OR a.nttCn LIKE %:searchKeyword%)) OR " +
            "(:searchCondition = '3' AND (:searchKeyword IS NULL OR b.userNm LIKE %:searchKeyword%)) " +
            ") " +
            "GROUP BY a.bbsId.nttId, a.nttSj, b.userNm, a.ntcrNm, a.frstRegistPnttm, a.rdcnt, a.parntscttNo, a.answerAt, a.answerLc, a.useAt, a.atchFileId, a.bbsId.bbsId, a.ntceBgnde, a.ntceEndde, a.sjBoldAt, a.noticeAt, a.secretAt " +
            "ORDER BY a.sortOrdr DESC,a.parntscttNo ASC,a.answerLc ASC, a.nttNo ASC, a.frstRegistPnttm DESC "
    )
    Page<BoardDTO> boardList(
            @Param("searchCondition") String searchCondition,
            @Param("searchKeyword") String searchKeyword,
            @Param("bbsId") String bbsId,
            Pageable pageable);

    @Query("SELECT new egovframework.com.cop.brd.service.BoardDTO( " +
            "a.bbsId.nttId, " +
            "a.bbsId.bbsId, " +
            "a.nttNo," +
            "a.nttSj, " +
            "a.nttCn, " +
            "a.frstRegisterId, " +
            "COALESCE(b.userNm, a.ntcrNm), " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.rdcnt, " +
            "a.parntscttNo, " +
            "a.answerAt, " +
            "a.answerLc, " +
            "a.sortOrdr, " +
            "a.useAt, " +
            "a.atchFileId, " +
            "a.ntceBgnde, " +
            "a.ntceEndde, " +
            "a.ntcrId, " +
            "a.ntcrNm, " +
            "a.sjBoldAt, " +
            "a.noticeAt, " +
            "a.secretAt, " +
            "0, " +
            "c.bbsNm " +
            ") " +
            "FROM brdBbs a " +
            "LEFT OUTER JOIN brdUserMaster b " +
            "ON a.frstRegisterId = b.esnlId " +
            "LEFT OUTER JOIN brdBbsMaster c " +
            "ON a.bbsId.bbsId = c.bbsId " +
            "LEFT OUTER JOIN brdComment d " +
            "ON a.bbsId.nttId = d.commentId.nttId " +
            "AND a.bbsId.bbsId = d.commentId.bbsId " +
            "WHERE a.bbsId.bbsId = :bbsId AND a.noticeAt='Y'" +
            "AND a.useAt = 'Y' " +
            "AND ( " +
            "(:searchCondition = '1' AND (:searchKeyword IS NULL OR a.nttSj LIKE %:searchKeyword%)) OR " +
            "(:searchCondition = '2' AND (:searchKeyword IS NULL OR a.nttCn LIKE %:searchKeyword%)) OR " +
            "(:searchCondition = '3' AND (:searchKeyword IS NULL OR b.userNm LIKE %:searchKeyword%)) " +
            ") " +
            "GROUP BY a.bbsId.nttId, a.nttSj, b.userNm, a.ntcrNm, a.frstRegistPnttm, a.rdcnt, a.parntscttNo, a.answerAt, a.answerLc, a.useAt, a.atchFileId, a.bbsId.bbsId, a.ntceBgnde, a.ntceEndde, a.sjBoldAt, a.noticeAt, a.secretAt " +
            "ORDER BY a.sortOrdr DESC,a.parntscttNo ASC,a.answerLc ASC, a.nttNo ASC, a.frstRegistPnttm DESC "
    )
    List<BoardDTO> boardNoticeList(
            @Param("searchCondition") String searchCondition,
            @Param("searchKeyword") String searchKeyword,
            @Param("bbsId") String bbsId);

    @Query("SELECT new egovframework.com.cop.brd.service.BoardDTO(" +
            "a.bbsId.nttId, " +
            "a.bbsId.bbsId, " +
            "a.nttNo," +
            "a.nttSj, " +
            "a.nttCn, " +
            "a.frstRegisterId, " +
            "COALESCE(b.userNm, a.ntcrNm), " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.rdcnt, " +
            "a.parntscttNo, " +
            "a.answerAt, " +
            "a.answerLc, " +
            "a.sortOrdr, " +
            "a.useAt, " +
            "a.atchFileId, " +
            "a.ntceBgnde, " +
            "a.ntceEndde, " +
            "a.ntcrId, " +
            "a.ntcrNm, " +
            "a.sjBoldAt, " +
            "a.noticeAt, " +
            "a.secretAt, " +
            "0, " +
            "c.bbsNm " +
            ") " +
            "FROM brdBbs a " +
            "LEFT OUTER JOIN brdUserMaster b " +
            "ON a.frstRegisterId = b.esnlId " +
            "LEFT OUTER JOIN brdBbsMaster c " +
            "ON a.bbsId.bbsId = c.bbsId " +
            "WHERE a.bbsId.bbsId = :bbsId " +
            "AND a.bbsId.nttId = :nttId " +
            "AND a.useAt = 'Y'")
    BoardDTO selectBbsDetail(String bbsId, Long nttId);

    @Query("SELECT COALESCE(MAX(rdcnt),0)+1 " +
            "FROM brdBbs " +
            "WHERE bbsId.bbsId = :bbsId " +
            "AND bbsId.nttId = :nttId "
    )
    int selectRdcntMax(String bbsId, Long nttId);

    @Modifying
    @Query("UPDATE brdBbs " +
            "SET rdcnt = :rdcnt, lastUpdusrId = :lastUpdusrId, lastUpdtPnttm = :lastUpdtPnttm " +
            "WHERE bbsId.bbsId = :bbsId " +
            "AND bbsId.nttId = :nttId "
    )
    int updataRdcnt(int rdcnt, String lastUpdusrId, LocalDateTime lastUpdtPnttm, String bbsId, Long nttId);

    List<Bbs> findAllByBbsIdAndSortOrdr(BbsId bbsId, Long SortOrdr);

    List<Bbs> findAllByParntscttNo(int parntscttNo);

}
