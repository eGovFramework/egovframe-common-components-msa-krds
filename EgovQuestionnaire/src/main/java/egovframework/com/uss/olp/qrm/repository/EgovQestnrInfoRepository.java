package egovframework.com.uss.olp.qrm.repository;

import egovframework.com.uss.olp.qrm.entity.QestnrInfo;
import egovframework.com.uss.olp.qrm.entity.QestnrInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qrmEgovQestnrInfoRepository")
public interface EgovQestnrInfoRepository extends JpaRepository<QestnrInfo, QestnrInfoId> {
}
