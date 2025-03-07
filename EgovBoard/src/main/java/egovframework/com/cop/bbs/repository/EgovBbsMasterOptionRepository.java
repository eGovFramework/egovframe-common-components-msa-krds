package egovframework.com.cop.bbs.repository;

import egovframework.com.cop.bbs.entity.BbsMasterOptn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("bbsEgovBbsMasterOptionRepository")
public interface EgovBbsMasterOptionRepository extends JpaRepository<BbsMasterOptn, String> {
}
