package egovframework.com.sec.rgm.repository;

import egovframework.com.sec.rgm.entity.Emplyrscrtyestbs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("rgmEgovAuthorGroupRepository")
public interface EgovAuthorGroupRepository extends JpaRepository<Emplyrscrtyestbs, String> {
}
