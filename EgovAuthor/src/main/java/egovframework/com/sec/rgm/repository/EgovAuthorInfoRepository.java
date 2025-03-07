package egovframework.com.sec.rgm.repository;

import egovframework.com.sec.rgm.entity.AuthorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("rgmEgovAuthorInfoRepository")
public interface EgovAuthorInfoRepository extends JpaRepository<AuthorInfo, String> {
}
