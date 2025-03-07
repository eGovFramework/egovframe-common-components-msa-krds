package egovframework.com.sym.ccm.ccc.util;

import egovframework.com.sym.ccm.ccc.entity.CmmnClCode;
import egovframework.com.sym.ccm.ccc.service.CmmnClCodeVO;
import org.springframework.beans.BeanUtils;

public class EgovCmmnClCodeUtility {

    public static CmmnClCodeVO CmmnClCodeEntityToVO(CmmnClCode cmmnClCode) {
        CmmnClCodeVO cmmnClCodeVO = new CmmnClCodeVO();
        BeanUtils.copyProperties(cmmnClCode, cmmnClCodeVO);
        return cmmnClCodeVO;
    }

    public static CmmnClCode CmmnClCodeVOToEntity(CmmnClCodeVO cmmnClCodeVO) {
        CmmnClCode cmmnClCode = new CmmnClCode();
        BeanUtils.copyProperties(cmmnClCodeVO, cmmnClCode);
        return cmmnClCode;
    }

}
