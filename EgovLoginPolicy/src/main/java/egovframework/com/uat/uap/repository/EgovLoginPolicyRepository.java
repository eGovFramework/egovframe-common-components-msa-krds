package egovframework.com.uat.uap.repository;

import egovframework.com.uat.uap.entity.LoginPolicy;
import egovframework.com.uat.uap.service.LoginPolicyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("uapEgovLoginPolicyRepository")
public interface EgovLoginPolicyRepository extends JpaRepository<LoginPolicy, String> {

    @Query("SELECT new egovframework.com.uat.uap.service.LoginPolicyDTO( " +
            "a.userId, " +
            "a.userNm, " +
            "a.userSe, " +
            "COALESCE(b.ipInfo, ''), " +
            "COALESCE(b.dplctPermAt, ''), " +
            "COALESCE(b.lmttAt, ''), " +
            "COALESCE(b.lastUpdusrId, ''), " +
            "COALESCE(FUNCTION('DATE_FORMAT', b.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "CASE WHEN b.employerId IS NULL THEN 'N' ELSE 'Y' END " +
            ") " +
            "FROM uapUserMaster a " +
            "LEFT OUTER JOIN uapLoginPolicy b " +
            "ON a.userId = b.employerId " +
            "WHERE ((:searchCondition = '1' AND (:searchKeyword IS NULL OR a.userNm LIKE %:searchKeyword%))) " +
            "ORDER BY a.userId "
    )
    Page<LoginPolicyDTO> loginPolicyList(
            @Param("searchCondition") String searchCondition,
            @Param("searchKeyword") String searchKeyword,
            Pageable pageable);

}
