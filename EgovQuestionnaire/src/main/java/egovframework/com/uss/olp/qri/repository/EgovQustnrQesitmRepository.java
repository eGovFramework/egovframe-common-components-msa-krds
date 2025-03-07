package egovframework.com.uss.olp.qri.repository;

import egovframework.com.uss.olp.qri.entity.QustnrQesitm;
import egovframework.com.uss.olp.qri.entity.QustnrQesitmId;
import egovframework.com.uss.olp.qri.service.QustnrQesitmDTO;
import egovframework.com.uss.olp.qri.service.QustnrQesitmItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("qriEgovQustnrQesitmRepository")
public interface EgovQustnrQesitmRepository extends JpaRepository<QustnrQesitm, QustnrQesitmId> {

    @Query("SELECT new egovframework.com.uss.olp.qri.service.QustnrQesitmItemDTO( " +
            "a.qustnrQesitmId.qustnrTmplatId, " +
            "a.qustnrQesitmId.qestnrId, " +
            "a.qustnrQesitmId.qustnrQesitmId, " +
            "b.qustnrIemId.qustnrIemId, " +
            "a.qestnSn, " +
            "a.qestnTyCode, " +
            "a.qestnCn, " +
            "a.mxmmChoiseCo, " +
            "b.iemSn, " +
            "b.iemCn " +
            ") " +
            "FROM qriQustnrQesitm a " +
            "LEFT OUTER JOIN qriQustnrIem b " +
            "ON a.qustnrQesitmId.qustnrTmplatId = b.qustnrIemId.qustnrTmplatId " +
            "AND a.qustnrQesitmId.qestnrId = b.qustnrIemId.qestnrId " +
            "AND a.qustnrQesitmId.qustnrQesitmId = b.qustnrIemId.qustnrQesitmId " +
            "WHERE a.qustnrQesitmId.qustnrTmplatId = :qustnrTmplatId " +
            "AND a.qustnrQesitmId.qestnrId = :qestnrId " +
            "GROUP BY a.qustnrQesitmId.qustnrTmplatId, " +
            "a.qustnrQesitmId.qestnrId, " +
            "a.qustnrQesitmId.qustnrQesitmId, " +
            "b.qustnrIemId.qustnrIemId, " +
            "a.qestnSn, " +
            "a.qestnTyCode, " +
            "a.qestnCn, " +
            "a.mxmmChoiseCo, " +
            "b.iemSn, " +
            "b.iemCn " +
            "ORDER BY a.qestnSn, b.iemSn "
    )
    List<QustnrQesitmItemDTO> qustnrQesitmItemList(String qustnrTmplatId, String qestnrId);

    @Query("SELECT new egovframework.com.uss.olp.qri.service.QustnrQesitmDTO( " +
            "qustnrQesitmId.qustnrTmplatId, " +
            "qustnrQesitmId.qestnrId, " +
            "qustnrQesitmId.qustnrQesitmId, " +
            "qestnTyCode, " +
            "qestnSn, " +
            "qestnCn, " +
            "mxmmChoiseCo, " +
            "COALESCE(FUNCTION('DATE_FORMAT', frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "lastUpdusrId " +
            ") " +
            "FROM qriQustnrQesitm " +
            "WHERE qustnrQesitmId.qustnrTmplatId = :qustnrTmplatId " +
            "AND qustnrQesitmId.qestnrId = :qestnrId " +
            "ORDER BY qestnSn "
    )
    List<QustnrQesitmDTO> qustnrQesitmList(String qustnrTmplatId, String qestnrId);

}
