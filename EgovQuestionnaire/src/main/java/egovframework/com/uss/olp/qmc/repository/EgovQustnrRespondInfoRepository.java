package egovframework.com.uss.olp.qmc.repository;

import egovframework.com.uss.olp.qmc.entity.QustnrRespondInfo;
import egovframework.com.uss.olp.qmc.entity.QustnrRespondInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qmcEgovQustnrRespondInfoRepository")
public interface EgovQustnrRespondInfoRepository extends JpaRepository<QustnrRespondInfo, QustnrRespondInfoId> {
    void deleteByQustnrRespondInfoIdQustnrTmplatIdAndQustnrRespondInfoIdQestnrId(String qustnrTmplatId, String qesterId);
}
