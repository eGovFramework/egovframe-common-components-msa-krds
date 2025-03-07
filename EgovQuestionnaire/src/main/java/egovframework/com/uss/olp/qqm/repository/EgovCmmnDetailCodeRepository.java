package egovframework.com.uss.olp.qqm.repository;

import egovframework.com.uss.olp.qqm.entity.CmmnDetailCode;
import egovframework.com.uss.olp.qqm.entity.CmmnDetailCodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("qqmEgovCmmnDetailCodeRepository")
public interface EgovCmmnDetailCodeRepository extends JpaRepository<CmmnDetailCode, CmmnDetailCodeId> {

    List<CmmnDetailCode> findByCmmnDetailCodeIdCodeId(String cmmnDetailCodeId);

}
