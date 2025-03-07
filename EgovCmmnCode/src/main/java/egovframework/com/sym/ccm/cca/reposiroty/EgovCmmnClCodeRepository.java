package egovframework.com.sym.ccm.cca.reposiroty;

import egovframework.com.sym.ccm.cca.entity.CmmnClCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ccaEgovCmmnClCodeRepository")
public interface EgovCmmnClCodeRepository extends JpaRepository<CmmnClCode, String> {

    List<CmmnClCode> findAllByUseAtEquals(String useAt);

}
