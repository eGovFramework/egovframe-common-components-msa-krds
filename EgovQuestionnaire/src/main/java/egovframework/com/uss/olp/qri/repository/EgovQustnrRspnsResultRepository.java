package egovframework.com.uss.olp.qri.repository;

import egovframework.com.uss.olp.qri.entity.QustnrRspnsResult;
import egovframework.com.uss.olp.qri.entity.QustnrRspnsResultId;
import egovframework.com.uss.olp.qri.service.QustnrRspnsResultDTO;
import egovframework.com.uss.olp.qri.service.QustnrRspnsResultESStatsDTO;
import egovframework.com.uss.olp.qri.service.QustnrRspnsResultMCStatsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("qriEgovQustnrRspnsResultRepository")
public interface EgovQustnrRspnsResultRepository extends JpaRepository<QustnrRspnsResult, QustnrRspnsResultId> {

    @Query("SELECT new egovframework.com.uss.olp.qri.service.QustnrRspnsResultDTO( " +
            "a.qestnrInfoId.qustnrTmplatId, " +
            "a.qestnrInfoId.qestnrId, " +
            "a.qustnrSj, " +
            "a.qustnrPurps, " +
            "a.qustnrWritingGuidanceCn, " +
            "a.qustnrTrget, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.qustnrBgnde, '%Y-%m-%d'), ''), " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.qustnrEndde, '%Y-%m-%d'), ''), " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "b.userNm, " +
            "c.qustnrTmplatTy," +
            "d.codeNm " +
            ") " +
            "FROM qriQestnrInfo a " +
            "LEFT OUTER JOIN qriUserMaster b " +
            "ON a.frstRegisterId = b.esntlId " +
            "LEFT OUTER JOIN qriQustnrTmplat c " +
            "ON a.qestnrInfoId.qustnrTmplatId = c.qustnrTmplatId " +
            "LEFT OUTER JOIN qriCmmnDetailCode d " +
            "ON a.qustnrTrget = d.cmmnDetailCodeId.code " +
            "AND d.cmmnDetailCodeId.codeId = 'COM034' " +
            "WHERE ( " +
            "(:searchCondition = '1' AND (:searchKeyword IS NULL OR a.qustnrSj LIKE %:searchKeyword%)) OR " +
            "(:searchCondition = '2' AND (:searchKeyword IS NULL OR b.userNm LIKE %:searchKeyword%)) " +
            ") " +
            "ORDER BY a.qestnrInfoId.qestnrId DESC "
    )
    Page<QustnrRspnsResultDTO> qustnrRspnsResultList(
            @Param("searchCondition") String searchCondition,
            @Param("searchKeyword") String searchKeyword,
            Pageable pageable);

    @Query("SELECT new egovframework.com.uss.olp.qri.service.QustnrRspnsResultMCStatsDTO( " +
            "a.qustnrIemId.qustnrTmplatId, " +
            "a.qustnrIemId.qestnrId, " +
            "a.qustnrIemId.qustnrQesitmId, " +
            "a.qustnrIemId.qustnrIemId, " +
            "a.iemCn, " +
            "COUNT(b.qustnrIemId), " +
            "ROUND(100/((SELECT COUNT(*) FROM qriQustnrRspnsResult WHERE qustnrRspnsResultId.qustnrQesitmId = b.qustnrRspnsResultId.qustnrQesitmId))*COUNT(b.qustnrIemId)) " +
            ") " +
            "FROM qriQustnrIem a " +
            "LEFT OUTER JOIN qriQustnrRspnsResult b " +
            "ON a.qustnrIemId.qustnrIemId = b.qustnrIemId " +
            "WHERE a.qustnrIemId.qustnrTmplatId = :qustnrTmplatId " +
            "AND a.qustnrIemId.qestnrId = :qestnrId " +
            "GROUP BY a.qustnrIemId.qustnrTmplatId, "+
            "a.qustnrIemId.qestnrId, " +
            "a.qustnrIemId.qustnrQesitmId, " +
            "a.qustnrIemId.qustnrIemId, " +
            "b.qustnrRspnsResultId.qustnrQesitmId, " +
            "a.iemCn "
    )
    List<QustnrRspnsResultMCStatsDTO> qustnrRspnsResultMCStats(String qustnrTmplatId, String qestnrId);

    @Query("SELECT new egovframework.com.uss.olp.qri.service.QustnrRspnsResultESStatsDTO( " +
            "a.qustnrRspnsResultId.qustnrTmplatId, " +
            "a.qustnrRspnsResultId.qestnrId, " +
            "a.qustnrRspnsResultId.qustnrQesitmId, " +
            "a.qustnrIemId, " +
            "a.respondAnswerCn, " +
            "a.etcAnswerCn, " +
            "a.respondNm " +
            ") " +
            "FROM qriQustnrRspnsResult a " +
            "WHERE a.qustnrRspnsResultId.qustnrTmplatId = :qustnrTmplatId " +
            "AND a.qustnrRspnsResultId.qestnrId = :qestnrId " +
            "AND (a.qustnrIemId IS NULL OR a.qustnrIemId = '') "
    )
    List<QustnrRspnsResultESStatsDTO> qustnrRspnsResultESStats(String qustnrTmplatId, String qestnrId);

}
