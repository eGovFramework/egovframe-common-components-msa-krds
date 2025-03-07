package egovframework.com.sym.ccm.cca.reposiroty;

import egovframework.com.sym.ccm.cca.entity.CmmnCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("ccaEgovCmmnCodeManageRepository")
public interface EgovCmmnCodeManageRepository extends JpaRepository<CmmnCode, String>, JpaSpecificationExecutor<CmmnCode> {
}
