package egovframework.com.cop.bbs.repository;

import egovframework.com.cop.bbs.entity.BbsMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("bbsEgovBbsMasterRepository")
public interface EgovBbsMasterRepository extends JpaRepository<BbsMaster, String> {

}
