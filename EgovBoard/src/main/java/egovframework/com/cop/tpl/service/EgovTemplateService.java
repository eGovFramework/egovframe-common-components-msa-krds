package egovframework.com.cop.tpl.service;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface EgovTemplateService {

    Page<TemplateDTO> list(TemplateVO templateVO);

    TemplateDTO detail(TemplateVO templateVO);

    TemplateVO insert(TemplateVO templateVO, Map<String, String> userInfo) throws FdlException;

    TemplateVO update(TemplateVO templateVO, Map<String, String> userInfo);

    void delete(TemplateVO templateVO);

}
