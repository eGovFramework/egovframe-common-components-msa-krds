package egovframework.com.cop.brd.service;


import org.egovframe.rte.fdl.cmmn.exception.FdlException;

import java.util.Map;

public interface EgovStsfdgService {

    Map<String, Object> selectStsfdgList(StsfdgVO stsfdgVO);

    int insertStsfdg(StsfdgVO stsfdgVO, Map<String, String> userInfo) throws FdlException;

    int deleteStsfdg(String stsfdgNo);

}
