package egovframework.com.uss.olp.qmc.repository;

import egovframework.com.uss.olp.qmc.entity.QustnrRespondInfo;
import egovframework.com.uss.olp.qmc.entity.QustnrRespondInfoId;
import egovframework.com.uss.olp.qmc.service.QustnrRespondInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("qmcEgovQustnrRespondInfoRepository")
public interface EgovQustnrRespondInfoRepository extends JpaRepository<QustnrRespondInfo, QustnrRespondInfoId> {

    @Query("SELECT new egovframework.com.uss.olp.qmc.service.QustnrRespondInfoDTO( " +
            "a.qustnrRespondInfoId.qustnrTmplatId, " +
            "a.qustnrRespondInfoId.qestnrId, " +
            "c.qustnrSj, " +
            "a.qustnrRespondInfoId.qustnrRespondId, " +
            "a.sexdstnCode, " +
            "d.codeNm, " +
            "a.occpTyCode, " +
            "e.codeNm, " +
            "a.respondNm, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "b.userNm " +
            ") " +
            "FROM qmcQustnrRespondInfo a " +
            "LEFT OUTER JOIN qmcUserMaster b " +
            "ON a.frstRegisterId = b.esntlId " +
            "LEFT OUTER JOIN qmcQestnrInfo c " +
            "ON a.qustnrRespondInfoId.qestnrId = c.qestnrInfoId.qestnrId " +
            "LEFT OUTER JOIN qmcCmmnDetailCode d " +
            "ON a.sexdstnCode = d.cmmnDetailCodeId.code " +
            "AND d.cmmnDetailCodeId.codeId = 'COM014' " +
            "LEFT OUTER JOIN qmcCmmnDetailCode e " +
            "ON a.occpTyCode = e.cmmnDetailCodeId.code " +
            "AND e.cmmnDetailCodeId.codeId = 'COM034' " +
            "WHERE a.qustnrRespondInfoId.qustnrTmplatId = :qustnrTmplatId " +
            "AND a.qustnrRespondInfoId.qestnrId = :qestnrId " +
            "ORDER BY a.frstRegistPnttm DESC "
    )
    Page<QustnrRespondInfoDTO> qustnrRespondInfoList(
            @Param("qustnrTmplatId") String qustnrTmplatId,
            @Param("qestnrId") String qestnrId,
            Pageable pageable);

    void deleteByQustnrRespondInfoIdQustnrTmplatIdAndQustnrRespondInfoIdQestnrId(String qustnrTmplatId, String qesterId);

}
