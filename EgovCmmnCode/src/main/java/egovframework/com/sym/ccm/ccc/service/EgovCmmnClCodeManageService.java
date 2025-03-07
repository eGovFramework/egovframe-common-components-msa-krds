package egovframework.com.sym.ccm.ccc.service;

import org.springframework.data.domain.Page;

import java.util.Map;

public interface EgovCmmnClCodeManageService {

    Page<CmmnClCodeVO> list(CmmnClCodeVO cmmnClCodeVO);

    CmmnClCodeVO detail(CmmnClCodeVO cmmnClCodeVO);

    CmmnClCodeVO insert(CmmnClCodeVO cmmnClCodeVO, Map<String, String> userInfo);

    CmmnClCodeVO update(CmmnClCodeVO cmmnClCodeVO, Map<String, String> userInfo);

    CmmnClCodeVO delete(CmmnClCodeVO cmmnClCodeVO, Map<String, String> userInfo);

}
