package egovframework.com.cop.cmy.service;

import org.springframework.data.domain.Page;

import java.util.Map;

public interface EgovBbsMasterService {

    Page<BbsMasterDTO> list(BbsMasterVO bbsMasterVO);

    BbsMasterDTO detail(BbsMasterVO bbsMasterVO);

    BbsMasterVO insert(BbsMasterVO bbsMasterVO, Map<String, String> userInfo);

    BbsMasterVO update(BbsMasterVO bbsMasterVO, Map<String, String> userInfo);

}
