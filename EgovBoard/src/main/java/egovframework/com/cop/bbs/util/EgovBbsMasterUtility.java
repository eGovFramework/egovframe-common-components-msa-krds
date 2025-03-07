package egovframework.com.cop.bbs.util;

import egovframework.com.cop.bbs.entity.BbsMaster;
import egovframework.com.cop.bbs.entity.BbsMasterOptn;
import egovframework.com.cop.bbs.service.BbsMasterOptnVO;
import egovframework.com.cop.bbs.service.BbsMasterVO;
import org.springframework.beans.BeanUtils;

public class EgovBbsMasterUtility {

    public static BbsMasterVO bbsMasterEntityTOVO(BbsMaster bbsMaster) {
        BbsMasterVO bbsMasterVO = new BbsMasterVO();
        BeanUtils.copyProperties(bbsMaster, bbsMasterVO);
        return bbsMasterVO;
    }

    public static BbsMaster bbsMasterVOTOEntity(BbsMasterVO bbsMasterVO) {
        BbsMaster bbsMaster = new BbsMaster();
        BeanUtils.copyProperties(bbsMasterVO, bbsMaster);
        return bbsMaster;
    }

    public static BbsMasterOptn bbsMasterOptnVOEntity(BbsMasterOptnVO bbsMasterOptnVO) {
        BbsMasterOptn bbsMaster = new BbsMasterOptn();
        BeanUtils.copyProperties(bbsMasterOptnVO, bbsMaster);
        return bbsMaster;
    }

}
