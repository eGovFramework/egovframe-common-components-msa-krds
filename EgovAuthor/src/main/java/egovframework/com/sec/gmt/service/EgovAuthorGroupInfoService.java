package egovframework.com.sec.gmt.service;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.springframework.data.domain.Page;

public interface EgovAuthorGroupInfoService {

    Page<AuthorGroupInfoVO> list(AuthorGroupInfoVO authorGroupInfoVO);

    AuthorGroupInfoVO detail(AuthorGroupInfoVO authorGroupInfoVO);

    AuthorGroupInfoVO insert(AuthorGroupInfoVO authorGroupInfoVO) throws FdlException;

    AuthorGroupInfoVO update(AuthorGroupInfoVO authorGroupInfoVO);

    boolean delete(AuthorGroupInfoVO authorGroupInfoVO);

}
