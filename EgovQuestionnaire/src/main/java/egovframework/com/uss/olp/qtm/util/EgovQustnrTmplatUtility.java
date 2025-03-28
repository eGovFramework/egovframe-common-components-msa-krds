package egovframework.com.uss.olp.qtm.util;

import egovframework.com.uss.olp.qtm.entity.QustnrTmplat;
import egovframework.com.uss.olp.qtm.service.QustnrTmplatVO;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

@UtilityClass
public class EgovQustnrTmplatUtility {

    public static QustnrTmplat qustnrTmplatVOToEntity(QustnrTmplatVO qustnrTmplatVO) {
        QustnrTmplat qustnrTmplat = new QustnrTmplat();
        BeanUtils.copyProperties(qustnrTmplatVO, qustnrTmplat);
        return qustnrTmplat;
    }

    public static QustnrTmplatVO qustnrTmplatEntityToVO(QustnrTmplat qustnrTmplat) {
        QustnrTmplatVO qustnrTmplatVO = new QustnrTmplatVO();
        BeanUtils.copyProperties(qustnrTmplat, qustnrTmplatVO);
        qustnrTmplatVO.setQustnrTmplatImageByte(qustnrTmplat.getQustnrTmplatImageInfo());
        return qustnrTmplatVO;
    }

}
