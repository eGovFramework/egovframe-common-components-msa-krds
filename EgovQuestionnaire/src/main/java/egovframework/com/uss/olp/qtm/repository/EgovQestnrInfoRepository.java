package egovframework.com.uss.olp.qtm.repository;

import egovframework.com.uss.olp.qtm.entity.QestnrInfo;
import egovframework.com.uss.olp.qtm.entity.QestnrInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qtmEgovQestnrInfoRepository")
public interface EgovQestnrInfoRepository extends JpaRepository<QestnrInfo, QestnrInfoId> {

    void deleteByQestnrInfoIdQustnrTmplatId(String qustnrTmplatId);

}
