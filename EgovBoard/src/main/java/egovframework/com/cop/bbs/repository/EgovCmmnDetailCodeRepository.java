package egovframework.com.cop.bbs.repository;

import egovframework.com.cop.bbs.entity.CmmnDetailCode;
import egovframework.com.cop.bbs.entity.CmmnDetailCodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bbsEgovCmmnDetailCodeRepository")
public interface EgovCmmnDetailCodeRepository extends JpaRepository<CmmnDetailCode, CmmnDetailCodeId> {

    List<CmmnDetailCode> findByCmmnDetailCodeIdCodeId(String cmmnDetailCodeId);

}
