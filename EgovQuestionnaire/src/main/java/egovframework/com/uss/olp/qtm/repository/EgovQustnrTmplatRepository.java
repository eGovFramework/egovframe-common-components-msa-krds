package egovframework.com.uss.olp.qtm.repository;

import egovframework.com.uss.olp.qtm.entity.QustnrTmplat;
import egovframework.com.uss.olp.qtm.service.QustnrTmplatDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("qtmEgovQustnrTmplatRepository")
public interface EgovQustnrTmplatRepository extends JpaRepository<QustnrTmplat, String> {

    @Query("SELECT new egovframework.com.uss.olp.qtm.service.QustnrTmplatDTO( " +
            "a.qustnrTmplatId, " +
            "a.qustnrTmplatTy, " +
            "a.qustnrTmplatDc, " +
            "a.qustnrTmplatPathNm, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "a.qustnrTmplatImageInfo, " +
            "b.userNm " +
            ") " +
            "FROM qtmQustnrTmplat a " +
            "LEFT OUTER JOIN qtmUserMaster b " +
            "ON a.frstRegisterId = b.esntlId " +
            "WHERE ( " +
            "(:searchCondition = '1' AND (:searchKeyword IS NULL OR a.qustnrTmplatDc LIKE %:searchKeyword%)) OR " +
            "(:searchCondition = '2' AND (:searchKeyword IS NULL OR a.qustnrTmplatTy LIKE %:searchKeyword%)) " +
            ") " +
            "ORDER BY a.frstRegistPnttm DESC "
    )
    Page<QustnrTmplatDTO> qustnrTmplatList(
            @Param("searchCondition") String searchCondition,
            @Param("searchKeyword") String searchKeyword,
            Pageable pageable);

    @Query("SELECT new egovframework.com.uss.olp.qtm.service.QustnrTmplatDTO( " +
            "a.qustnrTmplatId, " +
            "a.qustnrTmplatTy, " +
            "a.qustnrTmplatDc, " +
            "a.qustnrTmplatPathNm, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "a.qustnrTmplatImageInfo, " +
            "b.userNm " +
            ") " +
            "FROM qtmQustnrTmplat a " +
            "LEFT OUTER JOIN qtmUserMaster b " +
            "ON a.frstRegisterId = b.esntlId " +
            "WHERE a.qustnrTmplatId = :qustnrTmplatId "
    )
    QustnrTmplatDTO qustnrTmplatDetail(String qustnrTmplatId);

}
