package egovframework.com.cop.cmy.service;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface EgovCommunityService {

    Page<CommunityDTO> list(CommunityVO communityVO);

    CommunityDTO detail(CommunityVO communityVO);

    CommunityVO insert(CommunityVO communityVO, Map<String, String> userInfo) throws FdlException;

    CommunityVO update(CommunityVO communityVO, Map<String, String> userInfo);

}
