package egovframework.com.sym.ccm.cde.util;

import egovframework.com.sym.ccm.cde.entity.CmmnClCode;
import egovframework.com.sym.ccm.cde.entity.CmmnCode;
import egovframework.com.sym.ccm.cde.entity.CmmnDetailCode;
import egovframework.com.sym.ccm.cde.entity.CmmnDetailCodeId;
import egovframework.com.sym.ccm.cde.service.CmmnClCodeVO;
import egovframework.com.sym.ccm.cde.service.CmmnCodeVO;
import egovframework.com.sym.ccm.cde.service.CmmnDetailCodeVO;
import org.springframework.beans.BeanUtils;

public class EgovCmmnDetailCodeUtility {

    public static CmmnClCodeVO clCodeEntityTOVO(CmmnClCode cmmnClCode) {
        CmmnClCodeVO cmmnClCodeVO = new CmmnClCodeVO();
        BeanUtils.copyProperties(cmmnClCode, cmmnClCodeVO);
        return cmmnClCodeVO;
    }

    public static CmmnCodeVO codeEntityToVO(CmmnCode cmmnCode) {
        CmmnCodeVO cmmnCodeVO = new CmmnCodeVO();
        BeanUtils.copyProperties(cmmnCode, cmmnCodeVO);
        return cmmnCodeVO;
    }

    public static CmmnDetailCodeVO entityToVO(CmmnDetailCode cmmnDetailCode) {
        CmmnDetailCodeVO cmmnDetailCodeVO = new CmmnDetailCodeVO();
        cmmnDetailCodeVO.setCodeId(cmmnDetailCode.getCmmnDetailCodeId().getCodeId());
        cmmnDetailCodeVO.setCodeIdNm(cmmnDetailCode.getCmmnCode().getCodeIdNm());
        cmmnDetailCodeVO.setCode(cmmnDetailCode.getCmmnDetailCodeId().getCode());
        cmmnDetailCodeVO.setCodeNm(cmmnDetailCode.getCodeNm());
        cmmnDetailCodeVO.setCodeDc(cmmnDetailCode.getCodeDc());
        cmmnDetailCodeVO.setUseAt(cmmnDetailCode.getUseAt());
        cmmnDetailCodeVO.setFrstRegistPnttm(cmmnDetailCode.getFrstRegistPnttm());
        cmmnDetailCodeVO.setFrstRegisterId(cmmnDetailCode.getFrstRegisterId());
        cmmnDetailCodeVO.setLastUpdtPnttm(cmmnDetailCode.getLastUpdtPnttm());
        cmmnDetailCodeVO.setLastUpdusrId(cmmnDetailCode.getLastUpdusrId());
        return cmmnDetailCodeVO;
    }

    public static CmmnDetailCode VOToEntity(CmmnDetailCodeVO cmmnDetailCodeVO) {
        CmmnDetailCodeId cmmnDetailCodeId = new CmmnDetailCodeId();
        cmmnDetailCodeId.setCodeId(cmmnDetailCodeVO.getCodeId());
        cmmnDetailCodeId.setCode(cmmnDetailCodeVO.getCode());

        CmmnDetailCode cmmnDetailCode = new CmmnDetailCode();
        cmmnDetailCode.setCmmnDetailCodeId(cmmnDetailCodeId);
        cmmnDetailCode.setCodeNm(cmmnDetailCodeVO.getCodeNm());
        cmmnDetailCode.setCodeDc(cmmnDetailCodeVO.getCodeDc());
        cmmnDetailCode.setUseAt(cmmnDetailCodeVO.getUseAt());
        cmmnDetailCode.setFrstRegistPnttm(cmmnDetailCodeVO.getFrstRegistPnttm());
        cmmnDetailCode.setFrstRegisterId(cmmnDetailCodeVO.getFrstRegisterId());
        cmmnDetailCode.setLastUpdtPnttm(cmmnDetailCodeVO.getLastUpdtPnttm());
        cmmnDetailCode.setLastUpdusrId(cmmnDetailCodeVO.getLastUpdusrId());
        return cmmnDetailCode;
    }

}
