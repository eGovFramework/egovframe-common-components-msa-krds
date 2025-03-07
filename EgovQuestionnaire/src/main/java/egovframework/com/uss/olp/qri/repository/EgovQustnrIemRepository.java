package egovframework.com.uss.olp.qri.repository;

import egovframework.com.uss.olp.qri.entity.QustnrIem;
import egovframework.com.uss.olp.qri.entity.QustnrIemId;
import egovframework.com.uss.olp.qri.service.QustnrIemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("qriEgovQustnrIemRepository")
public interface EgovQustnrIemRepository extends JpaRepository<QustnrIem, QustnrIemId> {

    @Query("SELECT new egovframework.com.uss.olp.qri.service.QustnrIemDTO( " +
            "qustnrIemId.qustnrTmplatId, " +
            "qustnrIemId.qestnrId, " +
            "qustnrIemId.qustnrQesitmId, " +
            "qustnrIemId.qustnrIemId, " +
            "iemSn, " +
            "iemCn, " +
            "etcAnswerAt, " +
            "COALESCE(FUNCTION('DATE_FORMAT', frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "lastUpdusrId " +
            ") " +
            "FROM qriQustnrIem " +
            "WHERE qustnrIemId.qustnrTmplatId = :qustnrTmplatId " +
            "AND qustnrIemId.qestnrId = :qestnrId " +
            "ORDER BY qustnrIemId.qustnrQesitmId, iemSn "
    )
    List<QustnrIemDTO> qustnrIemList(String qustnrTmplatId, String qestnrId);

}
