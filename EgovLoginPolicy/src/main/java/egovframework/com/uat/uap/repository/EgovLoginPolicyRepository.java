package egovframework.com.uat.uap.repository;

import egovframework.com.uat.uap.entity.LoginPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("uapEgovLoginPolicyRepository")
public interface EgovLoginPolicyRepository extends JpaRepository<LoginPolicy, String> {

}
