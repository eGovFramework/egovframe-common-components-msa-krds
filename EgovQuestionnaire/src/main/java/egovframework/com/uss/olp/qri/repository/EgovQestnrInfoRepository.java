package egovframework.com.uss.olp.qri.repository;

import egovframework.com.uss.olp.qri.entity.QestnrInfo;
import egovframework.com.uss.olp.qri.entity.QestnrInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qriEgovQestnrInfoRepository")
public interface EgovQestnrInfoRepository extends JpaRepository<QestnrInfo, QestnrInfoId> {

}
