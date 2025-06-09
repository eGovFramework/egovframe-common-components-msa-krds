package egovframework.com.uss.olp.qrm.repository;

import egovframework.com.uss.olp.qrm.entity.QustnrRespondInfo;
import egovframework.com.uss.olp.qrm.entity.QustnrRespondInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qrmEgovQustnrRespondInfoRepository")
public interface EgovQustnrRespondInfoRepository extends JpaRepository<QustnrRespondInfo, QustnrRespondInfoId> {

}
