package egovframework.com.sym.ccm.ccc.util;

import egovframework.com.sym.ccm.ccc.entity.CmmnClCode;
import egovframework.com.sym.ccm.ccc.service.CmmnClCodeVO;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

@UtilityClass
public class EgovCmmnClCodeUtility {

    public static CmmnClCodeVO cmmnClCodeEntityToVO(CmmnClCode cmmnClCode) {
        CmmnClCodeVO cmmnClCodeVO = new CmmnClCodeVO();
        BeanUtils.copyProperties(cmmnClCode, cmmnClCodeVO);
        return cmmnClCodeVO;
    }

    public static CmmnClCode cmmnClCodeVOToEntity(CmmnClCodeVO cmmnClCodeVO) {
        CmmnClCode cmmnClCode = new CmmnClCode();
        BeanUtils.copyProperties(cmmnClCodeVO, cmmnClCode);
        return cmmnClCode;
    }

}
