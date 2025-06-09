package egovframework.com.uss.olp.qmc.repository;

import egovframework.com.uss.olp.qmc.entity.QustnrQesitm;
import egovframework.com.uss.olp.qmc.entity.QustnrQesitmId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qmcEgovQustnrQesitmRepository")
public interface EgovQustnrQesitmRepository extends JpaRepository<QustnrQesitm, QustnrQesitmId> {
    void deleteByQustnrQesitmIdQustnrTmplatIdAndQustnrQesitmIdQestnrId(String qustnrTmplatId, String qestnrId);
}
