package egovframework.com.cop.bls.repository;

import egovframework.com.cop.bls.entity.BbsMasterOptn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("blsEgovBbsMasterOptionRepository")
public interface EgovBbsMasterOptionRepository extends JpaRepository<BbsMasterOptn, String> {
}
