package egovframework.com.uss.olp.qmc.repository;

import egovframework.com.uss.olp.qmc.entity.QustnrRspnsResult;
import egovframework.com.uss.olp.qmc.entity.QustnrRspnsResultId;
import egovframework.com.uss.olp.qmc.service.QustnrRspnsResultESStatsDTO;
import egovframework.com.uss.olp.qmc.service.QustnrRspnsResultMCStatsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("qmcEgovQustnrRspnsResultRepository")
public interface EgovQustnrRspnsResultRepository extends JpaRepository<QustnrRspnsResult, QustnrRspnsResultId> {

    @Query("SELECT new egovframework.com.uss.olp.qmc.service.QustnrRspnsResultMCStatsDTO( " +
            "a.qustnrIemId.qustnrTmplatId, " +
            "a.qustnrIemId.qestnrId, " +
            "a.qustnrIemId.qustnrQesitmId, " +
            "a.qustnrIemId.qustnrIemId, " +
            "a.iemCn, " +
            "COUNT(b.qustnrIemId), " +
            "ROUND(100/((SELECT COUNT(*) FROM qriQustnrRspnsResult WHERE qustnrRspnsResultId.qustnrQesitmId = b.qustnrRspnsResultId.qustnrQesitmId))*COUNT(b.qustnrIemId)) " +
            ") " +
            "FROM qmcQustnrIem a " +
            "LEFT OUTER JOIN qmcQustnrRspnsResult b " +
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

    @Query("SELECT new egovframework.com.uss.olp.qmc.service.QustnrRspnsResultESStatsDTO( " +
            "a.qustnrRspnsResultId.qustnrTmplatId, " +
            "a.qustnrRspnsResultId.qestnrId, " +
            "a.qustnrRspnsResultId.qustnrQesitmId, " +
            "a.qustnrIemId, " +
            "a.respondAnswerCn, " +
            "a.etcAnswerCn, " +
            "a.respondNm " +
            ") " +
            "FROM qmcQustnrRspnsResult a " +
            "WHERE a.qustnrRspnsResultId.qustnrTmplatId = :qustnrTmplatId " +
            "AND a.qustnrRspnsResultId.qestnrId = :qestnrId " +
            "AND (a.qustnrIemId IS NULL OR a.qustnrIemId = '') "
    )
    List<QustnrRspnsResultESStatsDTO> qustnrRspnsResultESStats(String qustnrTmplatId, String qestnrId);

    void deleteByQustnrRspnsResultIdQustnrTmplatIdAndQustnrRspnsResultIdQestnrId(String qustnrTmplatId, String qestnrId);

}
