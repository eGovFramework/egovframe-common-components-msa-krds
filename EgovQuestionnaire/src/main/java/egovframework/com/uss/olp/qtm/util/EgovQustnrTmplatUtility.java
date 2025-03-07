package egovframework.com.uss.olp.qtm.util;

import egovframework.com.uss.olp.qtm.entity.QustnrTmplat;
import egovframework.com.uss.olp.qtm.service.QustnrTmplatVO;
import org.springframework.beans.BeanUtils;

public class EgovQustnrTmplatUtility {

    public static QustnrTmplat QustnrTmplatVOToEntity(QustnrTmplatVO qustnrTmplatVO) {
        QustnrTmplat qustnrTmplat = new QustnrTmplat();
        BeanUtils.copyProperties(qustnrTmplatVO, qustnrTmplat);
        return qustnrTmplat;
    }

    public static QustnrTmplatVO QustnrTmplatEntityToVO(QustnrTmplat qustnrTmplat) {
        QustnrTmplatVO qustnrTmplatVO = new QustnrTmplatVO();
        BeanUtils.copyProperties(qustnrTmplat, qustnrTmplatVO);
        qustnrTmplatVO.setQustnrTmplatImageByte(qustnrTmplat.getQustnrTmplatImageInfo());
        return qustnrTmplatVO;
    }

}
