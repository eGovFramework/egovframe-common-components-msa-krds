package egovframework.com.uss.olp.qri.repository;

import egovframework.com.uss.olp.qri.entity.QustnrRespondInfo;
import egovframework.com.uss.olp.qri.entity.QustnrRespondInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qriEgovQustnrRespondInfoRepository")
public interface EgovQustnrRespondInfoRepository extends JpaRepository<QustnrRespondInfo, QustnrRespondInfoId> {
}
