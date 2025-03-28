package egovframework.com.uss.olp.qrm.util;

import egovframework.com.uss.olp.qrm.entity.QustnrRespondInfo;
import egovframework.com.uss.olp.qrm.entity.QustnrRespondInfoId;
import egovframework.com.uss.olp.qrm.service.QustnrRespondInfoVO;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

@UtilityClass
public class EgovQustnrRespondInfoUtility {

    public static QustnrRespondInfoVO qustnrRespondInfoEntityToVO(QustnrRespondInfo qustnrRespondInfo) {
        QustnrRespondInfoVO qustnrRespondInfoVO = new QustnrRespondInfoVO();
        BeanUtils.copyProperties(qustnrRespondInfo, qustnrRespondInfoVO);
        qustnrRespondInfoVO.setQustnrTmplatId(qustnrRespondInfo.getQustnrRespondInfoId().getQustnrTmplatId());
        qustnrRespondInfoVO.setQestnrId(qustnrRespondInfo.getQustnrRespondInfoId().getQestnrId());
        qustnrRespondInfoVO.setQustnrRespondId(qustnrRespondInfo.getQustnrRespondInfoId().getQustnrRespondId());
        return qustnrRespondInfoVO;
    }

    public static QustnrRespondInfo qustnrRespondInfoVOTOEntity(QustnrRespondInfoVO qustnrRespondInfoVO) {
        QustnrRespondInfoId qustnrRespondInfoId = new QustnrRespondInfoId();
        qustnrRespondInfoId.setQustnrTmplatId(qustnrRespondInfoVO.getQustnrTmplatId());
        qustnrRespondInfoId.setQestnrId(qustnrRespondInfoVO.getQestnrId());
        qustnrRespondInfoId.setQustnrRespondId(qustnrRespondInfoVO.getQustnrRespondId());

        QustnrRespondInfo qustnrRespondInfo = new QustnrRespondInfo();
        BeanUtils.copyProperties(qustnrRespondInfoVO, qustnrRespondInfo);
        qustnrRespondInfo.setQustnrRespondInfoId(qustnrRespondInfoId);
        return qustnrRespondInfo;
    }

}
