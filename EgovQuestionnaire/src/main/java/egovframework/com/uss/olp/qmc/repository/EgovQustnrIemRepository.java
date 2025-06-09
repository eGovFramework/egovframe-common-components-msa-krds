package egovframework.com.uss.olp.qmc.repository;

import egovframework.com.uss.olp.qmc.entity.QustnrIem;
import egovframework.com.uss.olp.qmc.entity.QustnrIemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qmcEgovQustnrIemRepository")
public interface EgovQustnrIemRepository extends JpaRepository<QustnrIem, QustnrIemId> {
    void deleteByQustnrIemIdQustnrTmplatIdAndQustnrIemIdQestnrId(String qustnrTmplatId, String qestnrId);
}
