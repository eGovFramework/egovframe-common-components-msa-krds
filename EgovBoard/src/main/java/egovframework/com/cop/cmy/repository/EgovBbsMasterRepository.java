package egovframework.com.cop.cmy.repository;

import egovframework.com.cop.cmy.entity.BbsMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("cmyEgovBbsMasterRepository")
public interface EgovBbsMasterRepository extends JpaRepository<BbsMaster, String> {

}
