package egovframework.com.uss.olp.qmc.repository;

import egovframework.com.uss.olp.qmc.entity.QustnrTmplat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qmcEgovQustnrTmplatRepository")
public interface EgovQustnrTmplatRepository extends JpaRepository<QustnrTmplat, String> {
}
