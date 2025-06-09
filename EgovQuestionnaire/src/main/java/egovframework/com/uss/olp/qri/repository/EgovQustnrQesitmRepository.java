package egovframework.com.uss.olp.qri.repository;

import egovframework.com.uss.olp.qri.entity.QustnrQesitm;
import egovframework.com.uss.olp.qri.entity.QustnrQesitmId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qriEgovQustnrQesitmRepository")
public interface EgovQustnrQesitmRepository extends JpaRepository<QustnrQesitm, QustnrQesitmId> {

}
