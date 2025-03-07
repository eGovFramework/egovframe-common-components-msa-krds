package egovframework.com.uss.olp.qtm.repository;

import egovframework.com.uss.olp.qtm.entity.QustnrQesitm;
import egovframework.com.uss.olp.qtm.entity.QustnrQesitmId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qtmEgovQustnrQesitmRepository")
public interface EgovQustnrQesitmRepository extends JpaRepository<QustnrQesitm, QustnrQesitmId> {

    void deleteByQustnrQesitmIdQustnrTmplatId(String qustnrTmplatId);

}
