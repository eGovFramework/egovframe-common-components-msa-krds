package egovframework.com.uss.olp.qtm.repository;

import egovframework.com.uss.olp.qtm.entity.QustnrRespondInfo;
import egovframework.com.uss.olp.qtm.entity.QustnrRespondInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qtmEgovQustnrRespondInfoRepository")
public interface EgovQustnrRespondInfoRepository extends JpaRepository<QustnrRespondInfo, QustnrRespondInfoId> {

    void deleteByQustnrRespondInfoIdQustnrTmplatId(String qustnrTmplatId);

}
