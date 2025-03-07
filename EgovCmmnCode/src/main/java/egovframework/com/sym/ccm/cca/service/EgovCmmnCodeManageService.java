package egovframework.com.sym.ccm.cca.service;

import org.springframework.data.domain.Page;

import java.util.Map;

public interface EgovCmmnCodeManageService {

    Page<CmmnCodeVO> list(CmmnCodeVO cmmnCodeVO);

    CmmnCodeVO detail(CmmnCodeVO cmmnCodeVO);

    CmmnCodeVO insert(CmmnCodeVO cmmnCodeVO, Map<String, String> userInfo);

    CmmnCodeVO update(CmmnCodeVO cmmnCodeVO, Map<String, String> userInfo);

    CmmnCodeVO delete(CmmnCodeVO cmmnCodeVO, Map<String, String> userInfo);

}
