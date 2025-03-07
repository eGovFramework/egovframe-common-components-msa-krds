package egovframework.com.sec.ram.repository;

import egovframework.com.sec.ram.entity.RoleInfo;
import egovframework.com.sec.ram.service.RoleInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("ramEgovRoleInfoRepository")
public interface EgovRoleInfoRepository extends JpaRepository<RoleInfo, String> {

    @Query("SELECT new egovframework.com.sec.ram.service.RoleInfoDTO( " +
            "a.roleCode, " +
            "a.roleNm, " +
            "a.rolePttrn, " +
            "a.roleDc, " +
            "a.roleTy, " +
            "a.roleSort, " +
            "CASE WHEN b.authorRoleRelatedId.roleCode IS NULL THEN 'N' ELSE 'Y' END, " +
            "COALESCE(FUNCTION('DATE_FORMAT', b.creatDt, '%Y-%m-%d'), '') " +
            ") " +
            "FROM ramRoleInfo a " +
            "LEFT OUTER JOIN ramAuthorRoleRelated b " +
            "ON a.roleCode = b.authorRoleRelatedId.roleCode " +
            "AND b.authorRoleRelatedId.authorCode = :searchKeyword " +
            "ORDER BY a.roleCode "
    )
    Page<RoleInfoDTO> roleInfoList(
            @Param("searchKeyword") String searchKeyword,
            Pageable pageable);

}
