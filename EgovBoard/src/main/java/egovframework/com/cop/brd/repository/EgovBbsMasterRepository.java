package egovframework.com.cop.brd.repository;

import egovframework.com.cop.brd.entity.BbsMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("brdEgovBbsMasterRepository")
public interface EgovBbsMasterRepository extends JpaRepository<BbsMaster, String> {

}
