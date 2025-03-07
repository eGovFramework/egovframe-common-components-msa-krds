package egovframework.com.uss.olp.qqm.repository;

import egovframework.com.uss.olp.qqm.entity.QustnrIem;
import egovframework.com.uss.olp.qqm.entity.QustnrIemId;
import egovframework.com.uss.olp.qqm.service.QustnrIemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("qqmEgovQustnrIemRepository")
public interface EgovQustnrIemRepository extends JpaRepository<QustnrIem, QustnrIemId> {

    @Query("SELECT new egovframework.com.uss.olp.qqm.service.QustnrIemDTO( " +
            "a.qustnrIemId.qustnrTmplatId, " +
            "a.qustnrIemId.qestnrId, " +
            "d.qustnrSj, " +
            "a.qustnrIemId.qustnrQesitmId, " +
            "c.qestnCn, " +
            "a.qustnrIemId.qustnrIemId, " +
            "a.iemSn, " +
            "a.iemCn, " +
            "a.etcAnswerAt, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "b.userNm " +
            ") " +
            "FROM qmcQustnrIem a " +
            "LEFT OUTER JOIN qmcUserMaster b " +
            "ON a.frstRegisterId = b.esntlId " +
            "LEFT OUTER JOIN qmcQustnrQesitm c " +
            "ON a.qustnrIemId.qustnrQesitmId = c.qustnrQesitmId.qustnrQesitmId " +
            "LEFT OUTER JOIN qmcQestnrInfo d " +
            "ON a.qustnrIemId.qestnrId = d.qestnrInfoId.qestnrId " +
            "WHERE a.qustnrIemId.qustnrTmplatId = :qustnrTmplatId " +
            "AND a.qustnrIemId.qestnrId = :qestnrId " +
            "AND a.qustnrIemId.qustnrQesitmId = :qustnrQesitmId " +
            "ORDER BY a.qustnrIemId.qestnrId DESC, a.qustnrIemId.qustnrQesitmId DESC, a.qustnrIemId.qustnrIemId DESC "
    )
    Page<QustnrIemDTO> qustnrIemList(
            @Param("qustnrTmplatId") String qustnrTmplatId,
            @Param("qestnrId") String qestnrId,
            @Param("qustnrQesitmId") String qustnrQesitmId,
            Pageable pageable);

    @Query("SELECT new egovframework.com.uss.olp.qqm.service.QustnrIemDTO( " +
            "a.qustnrIemId.qustnrTmplatId, " +
            "a.qustnrIemId.qestnrId, " +
            "d.qustnrSj, " +
            "a.qustnrIemId.qustnrQesitmId, " +
            "c.qestnCn, " +
            "a.qustnrIemId.qustnrIemId, " +
            "a.iemSn, " +
            "a.iemCn, " +
            "a.etcAnswerAt, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "b.userNm " +
            ") " +
            "FROM qmcQustnrIem a " +
            "LEFT OUTER JOIN qmcUserMaster b " +
            "ON a.frstRegisterId = b.esntlId " +
            "LEFT OUTER JOIN qmcQustnrQesitm c " +
            "ON a.qustnrIemId.qustnrQesitmId = c.qustnrQesitmId.qustnrQesitmId " +
            "LEFT OUTER JOIN qmcQestnrInfo d " +
            "ON a.qustnrIemId.qestnrId = d.qestnrInfoId.qestnrId " +
            "WHERE a.qustnrIemId.qustnrTmplatId = :qustnrTmplatId " +
            "AND a.qustnrIemId.qestnrId = :qestnrId " +
            "ORDER BY a.qustnrIemId.qustnrQesitmId, a.iemSn "
    )
    List<QustnrIemDTO> qustnrIemList(String qustnrTmplatId, String qestnrId);

}
