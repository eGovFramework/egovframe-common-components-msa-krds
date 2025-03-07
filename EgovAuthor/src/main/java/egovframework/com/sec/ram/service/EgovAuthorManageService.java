package egovframework.com.sec.ram.service;

import org.springframework.data.domain.Page;

public interface EgovAuthorManageService {

    Page<AuthorInfoVO> list(AuthorInfoVO authorInfoVO);

    AuthorInfoVO detail(AuthorInfoVO authorInfoVO);

    AuthorInfoVO insert(AuthorInfoVO authorInfoVO);

    AuthorInfoVO update(AuthorInfoVO authorInfoVO);

    boolean delete(AuthorInfoVO authorInfoVO);

}
