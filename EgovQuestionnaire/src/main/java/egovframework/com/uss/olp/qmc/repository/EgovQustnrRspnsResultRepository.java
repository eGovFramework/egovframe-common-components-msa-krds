package egovframework.com.uss.olp.qmc.repository;

import egovframework.com.uss.olp.qmc.entity.QustnrRspnsResult;
import egovframework.com.uss.olp.qmc.entity.QustnrRspnsResultId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qmcEgovQustnrRspnsResultRepository")
public interface EgovQustnrRspnsResultRepository extends JpaRepository<QustnrRspnsResult, QustnrRspnsResultId> {
    void deleteByQustnrRspnsResultIdQustnrTmplatIdAndQustnrRspnsResultIdQestnrId(String qustnrTmplatId, String qestnrId);
}
