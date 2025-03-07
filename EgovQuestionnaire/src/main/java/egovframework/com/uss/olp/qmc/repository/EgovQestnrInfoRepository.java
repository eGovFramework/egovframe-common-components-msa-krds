package egovframework.com.uss.olp.qmc.repository;

import egovframework.com.uss.olp.qmc.entity.QestnrInfo;
import egovframework.com.uss.olp.qmc.entity.QestnrInfoId;
import egovframework.com.uss.olp.qmc.service.QestnrInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("qmcEgovQestnrInfoRepository")
public interface EgovQestnrInfoRepository extends JpaRepository<QestnrInfo, QestnrInfoId> {

    @Query("SELECT new egovframework.com.uss.olp.qmc.service.QestnrInfoDTO( " +
            "a.qestnrInfoId.qustnrTmplatId, " +
            "a.qestnrInfoId.qestnrId, " +
            "a.qustnrSj, " +
            "a.qustnrPurps, " +
            "a.qustnrWritingGuidanceCn, " +
            "a.qustnrTrget, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.qustnrBgnde, '%Y-%m-%d'), ''), " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.qustnrEndde, '%Y-%m-%d'), ''), " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "b.userNm, " +
            "c.qustnrTmplatTy," +
            "d.codeNm " +
            ") " +
            "FROM qmcQestnrInfo a " +
            "LEFT OUTER JOIN qmcUserMaster b " +
            "ON a.frstRegisterId = b.esntlId " +
            "LEFT OUTER JOIN qmcQustnrTmplat c " +
            "ON a.qestnrInfoId.qustnrTmplatId = c.qustnrTmplatId " +
            "LEFT OUTER JOIN qmcCmmnDetailCode d " +
            "ON a.qustnrTrget = d.cmmnDetailCodeId.code " +
            "AND d.cmmnDetailCodeId.codeId = 'COM034' " +
            "WHERE ( " +
            "(:searchCondition = '1' AND (:searchKeyword IS NULL OR a.qustnrSj LIKE %:searchKeyword%)) OR " +
            "(:searchCondition = '2' AND (:searchKeyword IS NULL OR b.userNm LIKE %:searchKeyword%)) " +
            ") " +
            "ORDER BY a.qestnrInfoId.qestnrId DESC "
    )
    Page<QestnrInfoDTO> qestnrInfoList(
            @Param("searchCondition") String searchCondition,
            @Param("searchKeyword") String searchKeyword,
            Pageable pageable);

    @Query("SELECT new egovframework.com.uss.olp.qmc.service.QestnrInfoDTO( " +
            "a.qestnrInfoId.qustnrTmplatId, " +
            "a.qestnrInfoId.qestnrId, " +
            "a.qustnrSj, " +
            "a.qustnrPurps, " +
            "a.qustnrWritingGuidanceCn, " +
            "a.qustnrTrget, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.qustnrBgnde, '%Y-%m-%d'), ''), " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.qustnrEndde, '%Y-%m-%d'), ''), " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "b.userNm, " +
            "c.qustnrTmplatTy," +
            "d.codeNm " +
            ") " +
            "FROM qmcQestnrInfo a " +
            "LEFT OUTER JOIN qmcUserMaster b " +
            "ON a.frstRegisterId = b.esntlId " +
            "LEFT OUTER JOIN qmcQustnrTmplat c " +
            "ON a.qestnrInfoId.qustnrTmplatId = c.qustnrTmplatId " +
            "LEFT OUTER JOIN qmcCmmnDetailCode d " +
            "ON a.qustnrTrget = d.cmmnDetailCodeId.code " +
            "AND d.cmmnDetailCodeId.codeId = 'COM034' " +
            "WHERE a.qestnrInfoId.qestnrId = :qestnrId "
    )
    QestnrInfoDTO qestnrInfoDetail(String qestnrId);

}
