package egovframework.com.sec.rgm.service;

import org.springframework.data.domain.Page;

public interface EgovAuthorGroupInfoService {

    Page<AuthorGroupInfoVO> list(AuthorGroupInfoVO authorGroupInfoVO);

}
