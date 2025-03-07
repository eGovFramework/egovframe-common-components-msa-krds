package egovframework.com.uss.olp.qtm.repository;

import egovframework.com.uss.olp.qtm.entity.QustnrRspnsResult;
import egovframework.com.uss.olp.qtm.entity.QustnrRspnsResultId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qtmEgovQustnrRspnsResultRepository")
public interface EgovQustnrRspnsResultRepository extends JpaRepository<QustnrRspnsResult, QustnrRspnsResultId> {

    void deleteByQustnrRspnsResultIdQustnrTmplatId(String qustnrTmplatId);

}
