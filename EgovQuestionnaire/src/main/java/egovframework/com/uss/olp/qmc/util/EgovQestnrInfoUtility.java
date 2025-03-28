package egovframework.com.uss.olp.qmc.util;

import egovframework.com.uss.olp.qmc.entity.QestnrInfo;
import egovframework.com.uss.olp.qmc.entity.QestnrInfoId;
import egovframework.com.uss.olp.qmc.entity.QustnrTmplat;
import egovframework.com.uss.olp.qmc.service.QestnrInfoVO;
import egovframework.com.uss.olp.qmc.service.QustnrTmplatVO;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

@UtilityClass
public class EgovQestnrInfoUtility {

    public static QestnrInfoVO quesnrInfoEntityToVO(QestnrInfo qestnrInfo) {
        QestnrInfoVO qestnrInfoVO = new QestnrInfoVO();
        BeanUtils.copyProperties(qestnrInfo, qestnrInfoVO);
        qestnrInfoVO.setQustnrTmplatId(qestnrInfo.getQestnrInfoId().getQustnrTmplatId());
        qestnrInfoVO.setQestnrId(qestnrInfo.getQestnrInfoId().getQestnrId());
        return qestnrInfoVO;
    }

    public static QestnrInfo qestnrInfoVOToEntiry(QestnrInfoVO qestnrInfoVO) {
        QestnrInfoId qestnrInfoId = new QestnrInfoId();
        qestnrInfoId.setQustnrTmplatId(qestnrInfoVO.getQustnrTmplatId());
        qestnrInfoId.setQestnrId(qestnrInfoVO.getQestnrId());

        QestnrInfo qestnrInfo = new QestnrInfo();
        BeanUtils.copyProperties(qestnrInfoVO, qestnrInfo);
        qestnrInfo.setQestnrInfoId(qestnrInfoId);
        return qestnrInfo;
    }

    public static QustnrTmplatVO qustnrTmplatEntityToVO(QustnrTmplat qustnrTmplat) {
        QustnrTmplatVO qustnrTmplatVO = new QustnrTmplatVO();
        BeanUtils.copyProperties(qustnrTmplat, qustnrTmplatVO);
        qustnrTmplatVO.setQustnrTmplatImageByte(qustnrTmplat.getQustnrTmplatImageInfo());
        return qustnrTmplatVO;
    }

}
