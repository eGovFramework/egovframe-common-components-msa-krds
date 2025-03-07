package egovframework.com.uss.olp.qim.repository;

import egovframework.com.uss.olp.qim.entity.QustnrQesitm;
import egovframework.com.uss.olp.qim.entity.QustnrQesitmId;
import egovframework.com.uss.olp.qim.service.QustnrQesitmDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("qimEgovQustnrQesitmRepository")
public interface EgovQustnrQesitmRepository extends JpaRepository<QustnrQesitm, QustnrQesitmId> {

    @Query("SELECT new egovframework.com.uss.olp.qim.service.QustnrQesitmDTO( " +
            "a.qustnrQesitmId.qestnrId, " +
            "a.qustnrQesitmId.qustnrQesitmId, " +
            "a.qustnrQesitmId.qustnrTmplatId, " +
            "a.qestnSn, " +
            "a.qestnTyCode, " +
            "a.qestnCn, " +
            "a.mxmmChoiseCo, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "b.userNm " +
            ") " +
            "FROM qimQustnrQesitm a " +
            "LEFT OUTER JOIN qimUserMaster b " +
            "ON a.frstRegisterId = b.esntlId " +
            "WHERE a.qustnrQesitmId.qustnrTmplatId = :qustnrTmplatId "+
            "AND a.qustnrQesitmId.qestnrId = :qestnrId " +
            "AND (" +
            "(:searchCondition = '1' AND (:searchKeyword IS NULL OR a.qestnCn LIKE %:searchKeyword%)) OR " +
            "(:searchCondition = '2' AND (:searchKeyword IS NULL OR b.userNm LIKE %:searchKeyword%)) " +
            ") " +
            "ORDER BY a.qustnrQesitmId.qestnrId DESC, a.qustnrQesitmId.qustnrQesitmId DESC "
    )
    Page<QustnrQesitmDTO> qustnrQesitmList(
            @Param("searchCondition") String searchCondition,
            @Param("searchKeyword") String searchKeyword,
            @Param("qustnrTmplatId") String qustnrTmplatId,
            @Param("qestnrId") String qestnrId,
            Pageable pageable);

}
