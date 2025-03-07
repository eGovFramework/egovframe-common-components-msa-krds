package egovframework.com.sym.ccm.cde.repository;

import egovframework.com.sym.ccm.cde.entity.CmmnDetailCode;
import egovframework.com.sym.ccm.cde.entity.CmmnDetailCodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("cdeEgovCmmnDetailCodeManageRepository")
public interface EgovCmmnDetailCodeManageRepository extends JpaRepository<CmmnDetailCode, CmmnDetailCodeId>, JpaSpecificationExecutor<CmmnDetailCode> {

    Optional<CmmnDetailCode> findByCmmnDetailCodeId_CodeIdAndCmmnDetailCodeId_Code(String codeId, String code);

}
