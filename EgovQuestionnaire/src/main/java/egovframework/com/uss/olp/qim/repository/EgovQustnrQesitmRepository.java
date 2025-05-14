package egovframework.com.uss.olp.qim.repository;

import egovframework.com.uss.olp.qim.entity.QustnrQesitm;
import egovframework.com.uss.olp.qim.entity.QustnrQesitmId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qimEgovQustnrQesitmRepository")
public interface EgovQustnrQesitmRepository extends JpaRepository<QustnrQesitm, QustnrQesitmId> {
}
