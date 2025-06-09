package egovframework.com.uss.olp.qim.repository;

import egovframework.com.uss.olp.qim.entity.QestnrInfo;
import egovframework.com.uss.olp.qim.entity.QestnrInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qimEgovQestnrInfoRepository")
public interface EgovQestnrInfoRepository extends JpaRepository<QestnrInfo, QestnrInfoId> {
}
