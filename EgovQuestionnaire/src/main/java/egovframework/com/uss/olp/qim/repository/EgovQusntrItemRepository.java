package egovframework.com.uss.olp.qim.repository;

import egovframework.com.uss.olp.qim.entity.QustnrIem;
import egovframework.com.uss.olp.qim.entity.QustnrIemId;
import egovframework.com.uss.olp.qim.service.QustnrIemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("qimEgovQusntrItemRepository")
public interface EgovQusntrItemRepository extends JpaRepository<QustnrIem, QustnrIemId> {

    @Query("SELECT new egovframework.com.uss.olp.qim.service.QustnrIemDTO( " +
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
            "FROM qimQustnrIem a " +
            "LEFT OUTER JOIN qimUserMaster b " +
            "ON a.frstRegisterId = b.esntlId " +
            "LEFT OUTER JOIN qimQustnrQesitm c " +
            "ON a.qustnrIemId.qustnrQesitmId = c.qustnrQesitmId.qustnrQesitmId " +
            "LEFT OUTER JOIN qimQestnrInfo d " +
            "ON a.qustnrIemId.qestnrId = d.qestnrInfoId.qestnrId " +
            "WHERE ( " +
            "(:searchCondition = '1' AND (:searchKeyword IS NULL OR a.iemCn LIKE %:searchKeyword%)) OR " +
            "(:searchCondition = '2' AND (:searchKeyword IS NULL OR b.userNm LIKE %:searchKeyword%)) " +
            ") " +
            "ORDER BY a.qustnrIemId.qestnrId DESC, a.qustnrIemId.qustnrQesitmId DESC, a.qustnrIemId.qustnrIemId DESC "
    )
    Page<QustnrIemDTO> qustnrItemList(
            @Param("searchCondition") String searchCondition,
            @Param("searchKeyword") String searchKeyword,
            Pageable pageable);

    @Query("SELECT new egovframework.com.uss.olp.qim.service.QustnrIemDTO( " +
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
            "FROM qimQustnrIem a " +
            "LEFT OUTER JOIN qimUserMaster b " +
            "ON a.frstRegisterId = b.esntlId " +
            "LEFT OUTER JOIN qimQustnrQesitm c " +
            "ON a.qustnrIemId.qustnrQesitmId = c.qustnrQesitmId.qustnrQesitmId " +
            "LEFT OUTER JOIN qimQestnrInfo d " +
            "ON a.qustnrIemId.qestnrId = d.qestnrInfoId.qestnrId " +
            "WHERE a.qustnrIemId.qustnrIemId = :qustnrIemId "
    )
    QustnrIemDTO qustnrItemDetail(String qustnrIemId);

}
