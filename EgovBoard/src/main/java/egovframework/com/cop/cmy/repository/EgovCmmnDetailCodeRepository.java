package egovframework.com.cop.cmy.repository;

import egovframework.com.cop.cmy.entity.CmmnDetailCode;
import egovframework.com.cop.cmy.entity.CmmnDetailCodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("cmyEgovCmmnDetailCodeRepository")
public interface EgovCmmnDetailCodeRepository extends JpaRepository<CmmnDetailCode, CmmnDetailCodeId> {

    List<CmmnDetailCode> findByCmmnDetailCodeIdCodeId(String cmmnDetailCodeId);

}
