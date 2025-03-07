package egovframework.com.sym.ccm.ccc.repository;

import egovframework.com.sym.ccm.ccc.entity.CmmnClCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("cccEgovCmmnClCodeManageRepository")
public interface EgovCmmnClCodeManageRepository extends JpaRepository<CmmnClCode, String>, JpaSpecificationExecutor<CmmnClCode> {
}
