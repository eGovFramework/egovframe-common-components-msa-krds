package egovframework.com.sec.ram.repository;

import egovframework.com.sec.ram.entity.RoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ramEgovRoleInfoRepository")
public interface EgovRoleInfoRepository extends JpaRepository<RoleInfo, String> {

}
