package egovframework.com.uss.olp.qqm.repository;

import egovframework.com.uss.olp.qqm.entity.QustnrQesitm;
import egovframework.com.uss.olp.qqm.entity.QustnrQesitmId;
import egovframework.com.uss.olp.qqm.service.QustnrQesitmDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("qqmEgovQustnrQesitmRepository")
public interface EgovQustnrQesitmRepository extends JpaRepository<QustnrQesitm, QustnrQesitmId> {

    @Query("SELECT new egovframework.com.uss.olp.qqm.service.QustnrQesitmDTO( " +
            "a.qustnrQesitmId.qustnrTmplatId, " +
            "a.qustnrQesitmId.qestnrId, " +
            "a.qustnrQesitmId.qustnrQesitmId, " +
            "b.qustnrSj," +
            "a.qestnSn, " +
            "a.qestnTyCode, " +
            "d.codeNm, " +
            "a.qestnCn, " +
            "a.mxmmChoiseCo, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "c.userNm " +
            ") " +
            "FROM qqmQustnrQesitm a " +
            "LEFT OUTER JOIN qqmQestnrInfo b " +
            "ON a.qustnrQesitmId.qestnrId = b.qestnrInfoId.qestnrId " +
            "LEFT OUTER JOIN qmcUserMaster c " +
            "ON a.frstRegisterId = c.esntlId " +
            "LEFT OUTER JOIN qqmCmmnDetailCode d " +
            "ON a.qestnTyCode = d.cmmnDetailCodeId.code " +
            "AND d.cmmnDetailCodeId.codeId = 'COM018' " +
            "WHERE ( " +
            "(:searchCondition = '1' AND (:searchKeyword IS NULL OR b.qustnrSj LIKE %:searchKeyword%)) OR " +
            "(:searchCondition = '2' AND (:searchKeyword IS NULL OR a.qestnCn LIKE %:searchKeyword%)) " +
            ") " +
            "ORDER BY a.qustnrQesitmId.qestnrId DESC, a.qustnrQesitmId.qustnrQesitmId DESC "
    )
    Page<QustnrQesitmDTO> qustnrQusitmList(
            @Param("searchCondition") String searchCondition,
            @Param("searchKeyword") String searchKeyword,
            Pageable pageable);

    @Query("SELECT new egovframework.com.uss.olp.qqm.service.QustnrQesitmDTO( " +
            "a.qustnrQesitmId.qustnrTmplatId, " +
            "a.qustnrQesitmId.qestnrId, " +
            "a.qustnrQesitmId.qustnrQesitmId, " +
            "b.qustnrSj," +
            "a.qestnSn, " +
            "a.qestnTyCode, " +
            "d.codeNm, " +
            "a.qestnCn, " +
            "a.mxmmChoiseCo, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "c.userNm " +
            ") " +
            "FROM qqmQustnrQesitm a " +
            "LEFT OUTER JOIN qqmQestnrInfo b " +
            "ON a.qustnrQesitmId.qestnrId = b.qestnrInfoId.qestnrId " +
            "LEFT OUTER JOIN qmcUserMaster c " +
            "ON a.frstRegisterId = c.esntlId " +
            "LEFT OUTER JOIN qqmCmmnDetailCode d " +
            "ON a.qestnTyCode = d.cmmnDetailCodeId.code " +
            "AND d.cmmnDetailCodeId.codeId = 'COM018' " +
            "WHERE a.qustnrQesitmId.qustnrQesitmId = :qustnrQusitmId "
    )
    QustnrQesitmDTO qustnrQusitmDetail(String qustnrQusitmId);

    @Query("SELECT new egovframework.com.uss.olp.qqm.service.QustnrQesitmDTO( " +
            "a.qustnrQesitmId.qustnrTmplatId, " +
            "a.qustnrQesitmId.qestnrId, " +
            "a.qustnrQesitmId.qustnrQesitmId, " +
            "b.qustnrSj," +
            "a.qestnSn, " +
            "a.qestnTyCode, " +
            "d.codeNm, " +
            "a.qestnCn, " +
            "a.mxmmChoiseCo, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "c.userNm " +
            ") " +
            "FROM qqmQustnrQesitm a " +
            "LEFT OUTER JOIN qqmQestnrInfo b " +
            "ON a.qustnrQesitmId.qestnrId = b.qestnrInfoId.qestnrId " +
            "LEFT OUTER JOIN qmcUserMaster c " +
            "ON a.frstRegisterId = c.esntlId " +
            "LEFT OUTER JOIN qqmCmmnDetailCode d " +
            "ON a.qestnTyCode = d.cmmnDetailCodeId.code " +
            "AND d.cmmnDetailCodeId.codeId = 'COM018' " +
            "WHERE a.qustnrQesitmId.qustnrTmplatId = :qustnrTmplatId " +
            "AND a.qustnrQesitmId.qestnrId = :qestnrId " +
            "ORDER BY a.qestnSn "
    )
    List<QustnrQesitmDTO> qustnrQusitmList(String qustnrTmplatId, String qestnrId);

}
