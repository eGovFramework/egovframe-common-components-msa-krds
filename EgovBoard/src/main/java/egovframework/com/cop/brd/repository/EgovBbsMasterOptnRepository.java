package egovframework.com.cop.brd.repository;

import egovframework.com.cop.brd.entity.BbsMasterOptn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("brdEgovBbsMasterOptnRepository")
public interface EgovBbsMasterOptnRepository extends JpaRepository<BbsMasterOptn, String> {
}
