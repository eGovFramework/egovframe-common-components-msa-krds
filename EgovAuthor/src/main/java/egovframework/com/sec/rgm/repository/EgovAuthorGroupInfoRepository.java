package egovframework.com.sec.rgm.repository;

import egovframework.com.sec.rgm.entity.AuthorGroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("rgmEgovAuthorGroupInfoRepository")
public interface EgovAuthorGroupInfoRepository extends JpaRepository<AuthorGroupInfo, String>, JpaSpecificationExecutor<AuthorGroupInfo> {
}
