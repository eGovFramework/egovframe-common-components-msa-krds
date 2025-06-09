package egovframework.com.uss.olp.qqm.repository;

import egovframework.com.uss.olp.qqm.entity.QustnrRspnsResult;
import egovframework.com.uss.olp.qqm.entity.QustnrRspnsResultId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qqmEgovQustnrRspnsResultRepository")
public interface EgovQustnrRspnsResultRepository extends JpaRepository<QustnrRspnsResult, QustnrRspnsResultId> {
}
