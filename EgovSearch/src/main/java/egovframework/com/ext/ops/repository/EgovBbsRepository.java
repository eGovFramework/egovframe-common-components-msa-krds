package egovframework.com.ext.ops.repository;

import egovframework.com.ext.ops.entity.Bbs;
import egovframework.com.ext.ops.entity.BbsId;
import egovframework.com.ext.ops.service.BbsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("opsEgovBbsRepository")
public interface EgovBbsRepository extends JpaRepository<Bbs, BbsId> {

	@Query("SELECT new egovframework.com.ext.ops.service.BbsDTO( " +
			"a.bbsId.nttId, " +
			"a.bbsId.bbsId, " +
			"a.nttNo, " +
			"a.nttSj, " +
			"a.nttCn, " +
			"a.answerAt, " +
			"a.parntscttNo, " +
			"a.answerLc, " +
			"a.sortOrdr, " +
			"a.rdcnt, " +
			"a.useAt, " +
			"a.ntceBgnde, " +
			"a.ntceEndde, " +
			"a.ntcrId, " +
			"a.ntcrNm, " +
			"a.password, " +
			"a.atchFileId, " +
			"a.noticeAt, " +
			"a.sjBoldAt, " +
			"a.secretAt, " +
			"COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
			"a.frstRegisterId, " +
			"COALESCE(b.userNm, a.ntcrNm), " +
			"COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
			"a.lastUpdusrId, " +
			"c.bbsNm, " +
			"c.bbsIntrcn, " +
			"c.bbsTyCode, " +
			"c.replyPosblAt, " +
			"c.fileAtchPosblAt, " +
			"c.atchPosblFileNumber " +
			") " +
			"FROM opsBbs a " +
			"LEFT OUTER JOIN opsUserMaster b " +
			"ON a.frstRegisterId = b.esnlId " +
			"LEFT OUTER JOIN opsBbsMaster c " +
			"ON a.bbsId.bbsId = c.bbsId " +
			"WHERE a.useAt = 'Y' "
	)
	Page<BbsDTO> findAllArticlesWithPaging(Pageable pageable);

	@Query("SELECT new egovframework.com.ext.ops.service.BbsDTO( " +
			"a.bbsId.nttId, " +
			"a.bbsId.bbsId, " +
			"a.nttNo, " +
			"a.nttSj, " +
			"a.nttCn, " +
			"a.answerAt, " +
			"a.parntscttNo, " +
			"a.answerLc, " +
			"a.sortOrdr, " +
			"a.rdcnt, " +
			"a.useAt, " +
			"a.ntceBgnde, " +
			"a.ntceEndde, " +
			"a.ntcrId, " +
			"a.ntcrNm, " +
			"a.password, " +
			"a.atchFileId, " +
			"a.noticeAt, " +
			"a.sjBoldAt, " +
			"a.secretAt, " +
			"COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
			"a.frstRegisterId, " +
			"COALESCE(b.userNm, a.ntcrNm), " +
			"COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
			"a.lastUpdusrId, " +
			"c.bbsNm, " +
			"c.bbsIntrcn, " +
			"c.bbsTyCode, " +
			"c.replyPosblAt, " +
			"c.fileAtchPosblAt, " +
			"c.atchPosblFileNumber " +
			") " +
			"FROM opsBbs a " +
			"LEFT OUTER JOIN opsUserMaster b " +
			"ON a.frstRegisterId = b.esnlId " +
			"LEFT OUTER JOIN opsBbsMaster c " +
			"ON a.bbsId.bbsId = c.bbsId " +
			"WHERE a.bbsId.nttId = :nttId "
	)
	Optional<BbsDTO> findBBSDTOByNttId(@Param("nttId") Long nttId);

	@Query("SELECT COUNT(a) FROM opsBbs a WHERE a.useAt = 'Y'")
	long countAllArticles();

}
