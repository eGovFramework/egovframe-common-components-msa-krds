package egovframework.com.uss.olp.qtm.repository;

import egovframework.com.uss.olp.qtm.entity.QustnrIem;
import egovframework.com.uss.olp.qtm.entity.QustnrIemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qtmEgovQustnrIemRepository")
public interface EgovQustnrIemRepository extends JpaRepository<QustnrIem, QustnrIemId> {

    void deleteByQustnrIemIdQustnrTmplatId(String qustnrTmplatId);

}
