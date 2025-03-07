package egovframework.com.sym.ccm.cde.repository;

import egovframework.com.sym.ccm.cde.entity.CmmnCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("cdeEgovCmmnCodeRepository")
public interface EgovCmmnCodeRepository extends JpaRepository<CmmnCode, String> {

    List<CmmnCode> findAllByClCode(String clCode);

}
