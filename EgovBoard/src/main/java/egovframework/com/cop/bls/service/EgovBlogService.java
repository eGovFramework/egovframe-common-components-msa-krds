package egovframework.com.cop.bls.service;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface EgovBlogService {

    Page<BlogDTO> list(BlogVO blogVO);

    BlogDTO detail(BlogVO blogVO);

    BlogVO insert(BlogVO blogVO, Map<String, String> userInfo) throws FdlException;

    BlogVO update(BlogVO blogVO, Map<String, String> userInfo);

}
