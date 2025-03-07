package egovframework.com.uss.olp.qqm.repository;

import egovframework.com.uss.olp.qqm.entity.QestnrInfo;
import egovframework.com.uss.olp.qqm.entity.QestnrInfoId;
import egovframework.com.uss.olp.qqm.service.QestnrInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("qqmEgovQestnrInfoRepository")
public interface EgovQestnrInfoRepository extends JpaRepository<QestnrInfo, QestnrInfoId> {

    @Query("SELECT new egovframework.com.uss.olp.qqm.service.QestnrInfoDTO( " +
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
            "c.codeNm " +
            ") " +
            "FROM qqmQestnrInfo a " +
            "LEFT OUTER JOIN qqmUserMaster b " +
            "ON a.frstRegisterId = b.esntlId " +
            "LEFT OUTER JOIN qqmCmmnDetailCode c " +
            "ON a.qustnrTrget = c.cmmnDetailCodeId.code " +
            "AND c.cmmnDetailCodeId.codeId = 'COM034' " +
            "WHERE ( " +
            "(:searchCondition = '1' AND (:searchKeyword IS NULL OR a.qustnrSj LIKE %:searchKeyword%)) OR " +
            "(:searchCondition = '2' AND (:searchKeyword IS NULL OR b.userNm LIKE %:searchKeyword%)) " +
            ") " +
            "ORDER BY a.frstRegistPnttm DESC "
    )
    Page<QestnrInfoDTO> qestnrInfoList(
            @Param("searchCondition") String searchCondition,
            @Param("searchKeyword") String searchKeyword,
            Pageable pageable);

    @Query("SELECT new egovframework.com.uss.olp.qqm.service.QestnrInfoDTO( " +
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
            "c.codeNm " +
            ") " +
            "FROM qqmQestnrInfo a " +
            "LEFT OUTER JOIN qqmUserMaster b " +
            "ON a.frstRegisterId = b.esntlId " +
            "LEFT OUTER JOIN qqmCmmnDetailCode c " +
            "ON a.qustnrTrget = c.cmmnDetailCodeId.code " +
            "AND c.cmmnDetailCodeId.codeId = 'COM034' " +
            "WHERE a.qestnrInfoId.qustnrTmplatId = :qustnrTmplatId " +
            "AND a.qestnrInfoId.qestnrId = :qestnrId "
    )
    QestnrInfoDTO qestnrInfoDetail(String qustnrTmplatId, String qestnrId);

}
