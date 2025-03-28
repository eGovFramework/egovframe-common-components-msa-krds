package egovframework.com.sec.gmt.service;

import org.springframework.data.domain.Page;

public interface EgovAuthorGroupInfoService {

    Page<AuthorGroupInfoVO> list(AuthorGroupInfoVO authorGroupInfoVO);

    AuthorGroupInfoVO detail(AuthorGroupInfoVO authorGroupInfoVO);

    AuthorGroupInfoVO insert(AuthorGroupInfoVO authorGroupInfoVO);

    AuthorGroupInfoVO update(AuthorGroupInfoVO authorGroupInfoVO);

    boolean delete(AuthorGroupInfoVO authorGroupInfoVO);

}
