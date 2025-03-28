package egovframework.com.uat.uia.repository;

import egovframework.com.uat.uia.entity.LoginPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("uiaEgovLoginPolicyRepository")
public interface EgovLoginPolicyRepository extends JpaRepository<LoginPolicy, String> {
}
