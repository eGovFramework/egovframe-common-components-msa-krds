package egovframework.com.sec.rmt.repository;

import egovframework.com.sec.rmt.entity.CmmnDetailCode;
import egovframework.com.sec.rmt.entity.CmmnDetailCodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("rmtEgovCmmnDetailCodeRepository")
public interface EgovCmmnDetailCodeRepository extends JpaRepository<CmmnDetailCode, CmmnDetailCodeId> {

    List<CmmnDetailCode> findByCmmnDetailCodeIdCodeId(String cmmnDetailCodeId);

}
