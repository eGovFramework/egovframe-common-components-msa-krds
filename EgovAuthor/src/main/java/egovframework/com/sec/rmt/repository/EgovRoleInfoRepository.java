package egovframework.com.sec.rmt.repository;

import egovframework.com.sec.rmt.entity.RoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("rmtEgovRoleInfoRepository")
public interface EgovRoleInfoRepository extends JpaRepository<RoleInfo, String> {
}
