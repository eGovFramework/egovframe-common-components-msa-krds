package egovframework.com.cop.cmy.repository;

import egovframework.com.cop.cmy.entity.BbsMasterOptn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("cmyEgovBbsMasterOptionRepository")
public interface EgovBbsMasterOptionRepository extends JpaRepository<BbsMasterOptn, String> {
}
