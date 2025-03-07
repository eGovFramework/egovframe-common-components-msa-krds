package egovframework.com.cop.bbs.service;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface EgovBbsMasterService {

    Page<BbsMasterDTO> list(BbsMasterVO bbsMasterVO);

    BbsMasterDTO detail(BbsMasterVO bbsMasterVO);

    BbsMasterVO insert(BbsMasterVO bbsMasterVO, Map<String, String> userInfo) throws FdlException;

    BbsMasterVO update(BbsMasterVO bbsMasterVO, Map<String, String> userInfo);

}
