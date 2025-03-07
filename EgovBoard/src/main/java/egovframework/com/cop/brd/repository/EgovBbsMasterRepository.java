package egovframework.com.cop.brd.repository;

import egovframework.com.cop.brd.entity.BbsMaster;
import egovframework.com.cop.brd.service.BbsMasterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("brdEgovBbsMasterRepository")
public interface EgovBbsMasterRepository extends JpaRepository<BbsMaster, String> {

    @Query("SELECT new egovframework.com.cop.brd.service.BbsMasterDTO( " +
            "a.bbsId, " +
            "a.bbsNm, " +
            "a.bbsIntrcn, " +
            "a.bbsTyCode, " +
            "a.replyPosblAt, " +
            "a.fileAtchPosblAt, " +
            "a.atchPosblFileNumber, " +
            "a.atchPosblFileSize, " +
            "a.useAt, " +
            "a.tmplatId, " +
            "a.cmmntyId, " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.blogId, " +
            "a.blogAt, " +
            "b.answerAt, " +
            "b.stsfdgAt, " +
            "c.tmplatNm, " +
            "d.userNm, " +
            "e.codeNm " +
            ") " +
            "FROM bbsBbsMaster a " +
            "LEFT OUTER JOIN bbsBbsMasterOptn b " +
            "ON a.bbsId = b.bbsId " +
            "LEFT OUTER JOIN bbsTmplatInfo c " +
            "ON a.tmplatId = c.tmplatId " +
            "LEFT OUTER JOIN bbsUserMaster d " +
            "ON a.frstRegisterId = d.esnlId " +
            "LEFT OUTER JOIN bbsCmmnDetailCode e " +
            "ON a.bbsTyCode = e.cmmnDetailCodeId.code " +
            "AND e.useAt = 'Y' " +
            "AND e.cmmnDetailCodeId.codeId = 'COM101' " +
            "WHERE a.bbsId = :bbsId "
    )
    BbsMasterDTO bbsMasterDetail(String bbsId);

}
