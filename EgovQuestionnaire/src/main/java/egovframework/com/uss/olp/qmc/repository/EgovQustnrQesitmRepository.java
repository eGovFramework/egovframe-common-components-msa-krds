package egovframework.com.uss.olp.qmc.repository;

import egovframework.com.uss.olp.qmc.entity.QustnrQesitm;
import egovframework.com.uss.olp.qmc.entity.QustnrQesitmId;
import egovframework.com.uss.olp.qmc.service.QustnrQesitmDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("qmcEgovQustnrQesitmRepository")
public interface EgovQustnrQesitmRepository extends JpaRepository<QustnrQesitm, QustnrQesitmId> {

    @Query("SELECT new egovframework.com.uss.olp.qmc.service.QustnrQesitmDTO( " +
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
            "FROM qmcQustnrQesitm a " +
            "LEFT OUTER JOIN qmcQestnrInfo b " +
            "ON a.qustnrQesitmId.qestnrId = b.qestnrInfoId.qestnrId " +
            "LEFT OUTER JOIN qmcUserMaster c " +
            "ON a.frstRegisterId = c.esntlId " +
            "LEFT OUTER JOIN qmcCmmnDetailCode d " +
            "ON a.qestnTyCode = d.cmmnDetailCodeId.code " +
            "AND d.cmmnDetailCodeId.codeId = 'COM018' " +
            "WHERE a.qustnrQesitmId.qustnrTmplatId = :qustnrTmplatId " +
            "AND a.qustnrQesitmId.qestnrId = :qestnrId " +
            "ORDER BY a.qustnrQesitmId.qestnrId DESC, a.qustnrQesitmId.qustnrQesitmId DESC "
    )
    Page<QustnrQesitmDTO> qustnrQesitmList(
            @Param("qustnrTmplatId") String qustnrTmplatId,
            @Param("qestnrId") String qestnrId,
            Pageable pageable);

    @Query("SELECT new egovframework.com.uss.olp.qmc.service.QustnrQesitmDTO( " +
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
            "FROM qmcQustnrQesitm a " +
            "LEFT OUTER JOIN qmcQestnrInfo b " +
            "ON a.qustnrQesitmId.qestnrId = b.qestnrInfoId.qestnrId " +
            "LEFT OUTER JOIN qmcUserMaster c " +
            "ON a.frstRegisterId = c.esntlId " +
            "LEFT OUTER JOIN qmcCmmnDetailCode d " +
            "ON a.qestnTyCode = d.cmmnDetailCodeId.code " +
            "AND d.cmmnDetailCodeId.codeId = 'COM018' " +
            "WHERE a.qustnrQesitmId.qustnrTmplatId = :qustnrTmplatId " +
            "AND a.qustnrQesitmId.qestnrId = :qestnrId " +
            "ORDER BY a.qestnSn "
    )
    List<QustnrQesitmDTO> qustnrQesitmList(String qustnrTmplatId, String qestnrId);

    void deleteByQustnrQesitmIdQustnrTmplatIdAndQustnrQesitmIdQestnrId(String qustnrTmplatId, String qestnrId);

}
