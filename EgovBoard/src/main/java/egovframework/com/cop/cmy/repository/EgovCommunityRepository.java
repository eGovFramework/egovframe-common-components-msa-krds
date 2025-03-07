package egovframework.com.cop.cmy.repository;

import egovframework.com.cop.cmy.entity.Cmmnty;
import egovframework.com.cop.cmy.service.CommunityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("cmyEgovCommunityRepository")
public interface EgovCommunityRepository extends JpaRepository<Cmmnty, String> {

    @Query("SELECT new egovframework.com.cop.cmy.service.CommunityDTO( " +
            "a.cmmntyId, " +
            "a.cmmntyNm, " +
            "a.cmmntyIntrcn, " +
            "a.useAt, " +
            "a.registSeCode, " +
            "a.tmplatId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "b.userNm, " +
            "'' " +
            ") " +
            "FROM cmyCmmnty a " +
            "LEFT OUTER JOIN cmyUserMaster b " +
            "ON a.frstRegisterId = b.esnlId " +
            "LEFT OUTER JOIN cmyCmmnDetailCode c " +
            "ON a.registSeCode = c.cmmnDetailCodeId.code " +
            "AND c.useAt = 'Y' " +
            "AND c.cmmnDetailCodeId.codeId = 'COM001' " +
            "WHERE ((:searchCondition = '1' AND (:searchKeyword IS NULL OR a.cmmntyNm LIKE %:searchKeyword%))) " +
            "ORDER BY a.frstRegistPnttm DESC "
    )
    Page<CommunityDTO> communityList(
            @Param("searchCondition") String searchCondition,
            @Param("searchKeyword") String searchKeyword,
            Pageable pageable);

    @Query("SELECT new egovframework.com.cop.cmy.service.CommunityDTO( " +
            "a.cmmntyId, " +
            "a.cmmntyNm, " +
            "a.cmmntyIntrcn, " +
            "a.useAt, " +
            "a.registSeCode, " +
            "a.tmplatId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "b.userNm, " +
            "c.tmplatNm " +
            ") " +
            "FROM cmyCmmnty a " +
            "LEFT OUTER JOIN cmyUserMaster b " +
            "ON a.frstRegisterId = b.esnlId " +
            "LEFT OUTER JOIN cmyTmplatInfo c " +
            "ON a.tmplatId = c.tmplatId " +
            "WHERE a.cmmntyId = :cmmntyId "
    )
    CommunityDTO communityDetail(String cmmntyId);

}
