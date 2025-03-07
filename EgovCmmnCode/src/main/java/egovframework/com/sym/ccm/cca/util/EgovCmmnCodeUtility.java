package egovframework.com.sym.ccm.cca.util;

import egovframework.com.sym.ccm.cca.entity.CmmnClCode;
import egovframework.com.sym.ccm.cca.entity.CmmnCode;
import egovframework.com.sym.ccm.cca.service.CmmnClCodeVO;
import egovframework.com.sym.ccm.cca.service.CmmnCodeVO;
import org.springframework.beans.BeanUtils;

public class EgovCmmnCodeUtility {

    public static CmmnCodeVO CmmnCodeAllEntityToVO(CmmnCode cmmnCode) {
        CmmnCodeVO cmmnCodeVO = new CmmnCodeVO();
        cmmnCodeVO.setCodeId(cmmnCode.getCodeId());
        cmmnCodeVO.setCodeIdNm(cmmnCode.getCodeIdNm());
        cmmnCodeVO.setCodeIdDc(cmmnCode.getCodeIdDc());
        cmmnCodeVO.setUseAt(cmmnCode.getUseAt());
        cmmnCodeVO.setClCode(cmmnCode.getClCode());
        cmmnCodeVO.setClCodeNm(cmmnCode.getCmmnClCode().getClCodeNm());
        cmmnCodeVO.setFrstRegistPnttm(cmmnCode.getFrstRegistPnttm());
        cmmnCodeVO.setFrstRegisterId(cmmnCode.getFrstRegisterId());
        cmmnCodeVO.setLastUpdtPnttm(cmmnCode.getLastUpdtPnttm());
        cmmnCodeVO.setLastUpdusrId(cmmnCode.getLastUpdusrId());
        return cmmnCodeVO;
    }

    public static CmmnCodeVO CmmnCodeEntityToVO(CmmnCode cmmnCode) {
        CmmnCodeVO cmmnCodeVO = new CmmnCodeVO();
        BeanUtils.copyProperties(cmmnCode, cmmnCodeVO);
        return cmmnCodeVO;
    }

    public static CmmnCode CmmnCodeVOToEntity(CmmnCodeVO cmmnCodeVO) {
        CmmnCode cmmnCode = new CmmnCode();
        BeanUtils.copyProperties(cmmnCodeVO, cmmnCode);
        return cmmnCode;
    }

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
