package egovframework.com.sec.gmt.repository;

import egovframework.com.sec.gmt.entity.AuthorGroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("gmtEgovAuthorGroupInfoRepository")
public interface EgovAuthorGroupInfoRepository extends JpaRepository<AuthorGroupInfo, String>, JpaSpecificationExecutor<AuthorGroupInfo> {
}
