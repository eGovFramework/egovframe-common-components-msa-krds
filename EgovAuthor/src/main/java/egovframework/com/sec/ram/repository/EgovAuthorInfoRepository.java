package egovframework.com.sec.ram.repository;

import egovframework.com.sec.ram.entity.AuthorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("ramEgovAuthorInfoRepository")
public interface EgovAuthorInfoRepository extends JpaRepository<AuthorInfo, String>, JpaSpecificationExecutor<AuthorInfo> {
}
