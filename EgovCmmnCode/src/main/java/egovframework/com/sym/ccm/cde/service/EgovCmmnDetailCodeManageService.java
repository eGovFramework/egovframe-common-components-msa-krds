package egovframework.com.sym.ccm.cde.service;

import org.springframework.data.domain.Page;

import java.util.Map;

public interface EgovCmmnDetailCodeManageService {

    Page<CmmnDetailCodeVO> list(CmmnDetailCodeVO cmmnDetailCodeVO);

    CmmnDetailCodeVO detail(CmmnDetailCodeVO cmmnDetailCodeVO);

    CmmnDetailCodeVO insert(CmmnDetailCodeVO cmmnDetailCodeVO, Map<String, String> userInfo);

    CmmnDetailCodeVO update(CmmnDetailCodeVO cmmnDetailCodeVO, Map<String, String> userInfo);

    CmmnDetailCodeVO delete(CmmnDetailCodeVO cmmnDetailCodeVO, Map<String, String> userInfo);

}
