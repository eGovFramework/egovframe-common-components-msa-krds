package egovframework.com.cop.tpl.util;

import egovframework.com.cop.tpl.entity.TmplatInfo;
import egovframework.com.cop.tpl.service.TemplateVO;
import org.springframework.beans.BeanUtils;

public class EgovTemplateUtility {

    public static TemplateVO templateEntityToVO(TmplatInfo tmplatInfo) {
        TemplateVO templateVO = new TemplateVO();
        BeanUtils.copyProperties(tmplatInfo, templateVO);
        return templateVO;
    }

    public static TmplatInfo templateVOToEntity(TemplateVO templateVO) {
        TmplatInfo tmplatInfo = new TmplatInfo();
        BeanUtils.copyProperties(templateVO, tmplatInfo);
        return tmplatInfo;
    }

}



