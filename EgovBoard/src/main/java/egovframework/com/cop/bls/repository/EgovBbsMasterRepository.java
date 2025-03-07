package egovframework.com.cop.bls.repository;

import egovframework.com.cop.bls.entity.BbsMaster;
import egovframework.com.cop.bls.service.BbsMasterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("blsEgovBbsMasterRepository")
public interface EgovBbsMasterRepository extends JpaRepository<BbsMaster, String> {

    @Query("SELECT new egovframework.com.cop.bls.service.BbsMasterDTO( " +
            "a.bbsId, " +
            "a.bbsNm, " +
            "a.bbsIntrcn, " +
            "a.bbsTyCode, " +
            "a.replyPosblAt, " +
            "a.fileAtchPosblAt, " +
            "a.atchPosblFileNumber, " +
            "a.atchPosblFileSize, " +
            "a.useAt, " +
            "a.tmplatId, " +
            "a.cmmntyId, " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.blogId, " +
            "a.blogAt, " +
            "b.answerAt, " +
            "b.stsfdgAt, " +
            "c.tmplatNm, " +
            "d.userNm, " +
            "e.codeNm " +
            ") " +
            "FROM bbsBbsMaster a " +
            "LEFT OUTER JOIN bbsBbsMasterOptn b " +
            "ON a.bbsId = b.bbsId " +
            "LEFT OUTER JOIN bbsTmplatInfo c " +
            "ON a.tmplatId = c.tmplatId " +
            "LEFT OUTER JOIN bbsUserMaster d " +
            "ON a.frstRegisterId = d.esnlId " +
            "LEFT OUTER JOIN bbsCmmnDetailCode e " +
            "ON a.bbsTyCode = e.cmmnDetailCodeId.code " +
            "AND e.useAt = 'Y' " +
            "AND e.cmmnDetailCodeId.codeId = 'COM101' " +
            "WHERE a.blogId = :blogId " +
            "AND ( " +
            "(:searchCondition = '1' AND (:searchKeyword IS NULL OR a.bbsNm LIKE %:searchKeyword%)) OR " +
            "(:searchCondition = '2' AND (:searchKeyword IS NULL OR a.bbsIntrcn LIKE %:searchKeyword%)) " +
            ") " +
            "ORDER BY b.frstRegistPnttm DESC "
    )
    Page<BbsMasterDTO> bbsMasterList(
            @Param("searchCondition") String searchCondition,
            @Param("searchKeyword") String searchKeyword,
            @Param("blogId") String blogId,
            Pageable pageable);

    @Query("SELECT new egovframework.com.cop.bls.service.BbsMasterDTO( " +
            "a.bbsId, " +
            "a.bbsNm, " +
            "a.bbsIntrcn, " +
            "a.bbsTyCode, " +
            "a.replyPosblAt, " +
            "a.fileAtchPosblAt, " +
            "a.atchPosblFileNumber, " +
            "a.atchPosblFileSize, " +
            "a.useAt, " +
            "a.tmplatId, " +
            "a.cmmntyId, " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.blogId, " +
            "a.blogAt, " +
            "b.answerAt, " +
            "b.stsfdgAt, " +
            "c.tmplatNm, " +
            "d.userNm, " +
            "e.codeNm " +
            ") " +
            "FROM bbsBbsMaster a " +
            "LEFT OUTER JOIN bbsBbsMasterOptn b " +
            "ON a.bbsId = b.bbsId " +
            "LEFT OUTER JOIN bbsTmplatInfo c " +
            "ON a.tmplatId = c.tmplatId " +
            "LEFT OUTER JOIN bbsUserMaster d " +
            "ON a.frstRegisterId = d.esnlId " +
            "LEFT OUTER JOIN bbsCmmnDetailCode e " +
            "ON a.bbsTyCode = e.cmmnDetailCodeId.code " +
            "AND e.useAt = 'Y' " +
            "AND e.cmmnDetailCodeId.codeId = 'COM101' " +
            "WHERE a.bbsId = :bbsId " +
            "AND a.blogId = :blogId "
    )
    BbsMasterDTO bbsMasterDetail(
            @Param("bbsId") String bbsId,
            @Param("blogId") String blogId
    );

}
