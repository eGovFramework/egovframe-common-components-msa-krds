package egovframework.com.uss.olp.qqm.repository;

import egovframework.com.uss.olp.qqm.entity.QustnrQesitm;
import egovframework.com.uss.olp.qqm.entity.QustnrQesitmId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qqmEgovQustnrQesitmRepository")
public interface EgovQustnrQesitmRepository extends JpaRepository<QustnrQesitm, QustnrQesitmId> {

}
