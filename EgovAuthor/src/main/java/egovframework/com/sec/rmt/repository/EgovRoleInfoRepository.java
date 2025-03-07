package egovframework.com.sec.rmt.repository;

import egovframework.com.sec.rmt.entity.RoleInfo;
import egovframework.com.sec.rmt.service.RoleInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("rmtEgovRoleInfoRepository")
public interface EgovRoleInfoRepository extends JpaRepository<RoleInfo, String> {

    @Query("SELECT new egovframework.com.sec.rmt.service.RoleInfoDTO( " +
            "a.roleCode, " +
            "a.roleNm, " +
            "a.rolePttrn, " +
            "a.roleDc, " +
            "a.roleTy, " +
            "b.codeNm, " +
            "a.roleSort, " +
            "a.roleCreatDe " +
            ") " +
            "FROM rmtRoleInfo a " +
            "LEFT OUTER JOIN rmtCmmnDetailCode b " +
            "ON a.roleTy = b.cmmnDetailCodeId.code " +
            "AND b.cmmnDetailCodeId.codeId = 'COM029' " +
            "WHERE ((:searchCondition = '1' AND (:searchKeyword IS NULL OR a.roleNm LIKE %:searchKeyword%))) " +
            "ORDER BY a.roleCode "
    )
    Page<RoleInfoDTO> roleInfoList(
            @Param("searchCondition") String searchCondition,
            @Param("searchKeyword") String searchKeyword,
            Pageable pageable);

}
