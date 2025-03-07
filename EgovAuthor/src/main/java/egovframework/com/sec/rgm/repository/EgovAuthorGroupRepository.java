package egovframework.com.sec.rgm.repository;

import egovframework.com.sec.rgm.entity.Emplyrscrtyestbs;
import egovframework.com.sec.rgm.service.AuthorGroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("rgmEgovAuthorGroupRepository")
public interface EgovAuthorGroupRepository extends JpaRepository<Emplyrscrtyestbs, String> {

    @Query("SELECT new egovframework.com.sec.rgm.service.AuthorGroupDTO( " +
            "a.userId, " +
            "a.userNm, " +
            "a.groupId, " +
            "CASE WHEN a.userSe = 'GNR' THEN 'USR01' WHEN a.userSe = 'ENT' THEN 'USR02' ELSE 'USR03' END, " +
            "c.codeNm, " +
            "b.authorCode, " +
            "CASE WHEN b.scrtyDtrmnTrgetId IS NULL THEN 'N' ELSE 'Y' END, " +
            "a.esntlId " +
            ") " +
            "FROM rgmUserMaster a " +
            "LEFT OUTER JOIN rgmEmplyrscrtyestbs b " +
            "ON a.esntlId = b.scrtyDtrmnTrgetId " +
            "LEFT OUTER JOIN rgmCmmnDetailCode c " +
            "ON (CASE WHEN a.userSe = 'GNR' THEN 'USR01' WHEN a.userSe = 'ENT' THEN 'USR02' ELSE 'USR03' END = c.cmmnDetailCodeId.code) " +
            "AND c.useAt = 'Y' " +
            "AND c.cmmnDetailCodeId.codeId = 'COM012' " +
            "WHERE ( " +
            "(:searchCondition = '1' AND (:searchKeyword IS NULL OR a.userId LIKE %:searchKeyword%)) OR " +
            "(:searchCondition = '2' AND (:searchKeyword IS NULL OR a.userNm LIKE %:searchKeyword%)) OR " +
            "(:searchCondition = '3' AND (:searchKeyword IS NULL OR a.groupId = :searchKeyword)) " +
            ") " +
            "ORDER BY c.cmmnDetailCodeId.code "
    )
    Page<AuthorGroupDTO> authorGroupList(
            @Param("searchCondition") String searchCondition,
            @Param("searchKeyword") String searchKeyword,
            Pageable pageable);

}
