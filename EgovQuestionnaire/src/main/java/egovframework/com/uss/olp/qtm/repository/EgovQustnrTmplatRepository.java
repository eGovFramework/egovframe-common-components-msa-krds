package egovframework.com.uss.olp.qtm.repository;

import egovframework.com.uss.olp.qtm.entity.QustnrTmplat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qtmEgovQustnrTmplatRepository")
public interface EgovQustnrTmplatRepository extends JpaRepository<QustnrTmplat, String> {
}
