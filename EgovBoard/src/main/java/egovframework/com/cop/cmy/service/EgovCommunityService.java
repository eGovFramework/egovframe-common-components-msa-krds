package egovframework.com.cop.cmy.service;

import org.springframework.data.domain.Page;

import java.util.Map;

public interface EgovCommunityService {

    Page<CommunityDTO> list(CommunityVO communityVO);

    CommunityDTO detail(CommunityVO communityVO);

    CommunityVO insert(CommunityVO communityVO, Map<String, String> userInfo);

    CommunityVO update(CommunityVO communityVO, Map<String, String> userInfo);

}
