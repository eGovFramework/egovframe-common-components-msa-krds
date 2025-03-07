package egovframework.com.uss.olp.qrm.repository;

import egovframework.com.uss.olp.qrm.entity.CmmnDetailCode;
import egovframework.com.uss.olp.qrm.entity.CmmnDetailCodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("qrmEgovCmmnDetailCodeRepository")
public interface EgovCmmnDetailCodeRepository extends JpaRepository<CmmnDetailCode, CmmnDetailCodeId> {

    List<CmmnDetailCode> findByCmmnDetailCodeIdCodeId(String cmmnDetailCodeId);

}
