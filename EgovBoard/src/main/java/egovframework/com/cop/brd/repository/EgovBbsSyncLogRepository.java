package egovframework.com.cop.brd.repository;

import egovframework.com.cop.brd.entity.BbsSyncLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("brdEgovBbsSyncLogRepository")
public interface EgovBbsSyncLogRepository extends JpaRepository<BbsSyncLog, String> {
}
