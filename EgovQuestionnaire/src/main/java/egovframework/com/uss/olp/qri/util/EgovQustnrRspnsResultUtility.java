package egovframework.com.uss.olp.qri.util;

import egovframework.com.uss.olp.qri.entity.QustnrRespondInfo;
import egovframework.com.uss.olp.qri.entity.QustnrRespondInfoId;
import egovframework.com.uss.olp.qri.entity.QustnrRspnsResult;
import egovframework.com.uss.olp.qri.entity.QustnrRspnsResultId;
import egovframework.com.uss.olp.qri.service.QustnrRespondInfoVO;
import egovframework.com.uss.olp.qri.service.QustnrRspnsResultVO;
import org.springframework.beans.BeanUtils;

public class EgovQustnrRspnsResultUtility {

    public static QustnrRspnsResultVO QustnrRspnsResultEntityToVO(QustnrRspnsResult qustnrRspnsResult) {
        QustnrRspnsResultVO qustnrRspnsResultVO = new QustnrRspnsResultVO();
        BeanUtils.copyProperties(qustnrRspnsResult, qustnrRspnsResultVO);
        qustnrRspnsResultVO.setQustnrTmplatId(qustnrRspnsResult.getQustnrRspnsResultId().getQustnrTmplatId());
        qustnrRspnsResultVO.setQestnrId(qustnrRspnsResult.getQustnrRspnsResultId().getQestnrId());
        qustnrRspnsResultVO.setQustnrQesitmId(qustnrRspnsResult.getQustnrRspnsResultId().getQustnrQesitmId());
        qustnrRspnsResultVO.setQustnrRspnsResultId(qustnrRspnsResult.getQustnrRspnsResultId().getQustnrRspnsResultId());
        return qustnrRspnsResultVO;
    }

    public static QustnrRspnsResult qustnrRspnsResultVOToEntity(QustnrRspnsResultVO qustnrRspnsResultVO) {
        QustnrRspnsResultId qustnrRspnsResultId = new QustnrRspnsResultId();
        qustnrRspnsResultId.setQustnrTmplatId(qustnrRspnsResultVO.getQustnrTmplatId());
        qustnrRspnsResultId.setQestnrId(qustnrRspnsResultVO.getQestnrId());
        qustnrRspnsResultId.setQustnrQesitmId(qustnrRspnsResultVO.getQustnrQesitmId());
        qustnrRspnsResultId.setQustnrRspnsResultId(qustnrRspnsResultVO.getQustnrRspnsResultId());

        QustnrRspnsResult qustnrRspnsResult = new QustnrRspnsResult();
        BeanUtils.copyProperties(qustnrRspnsResultVO, qustnrRspnsResult);
        qustnrRspnsResult.setQustnrRspnsResultId(qustnrRspnsResultId);
        return qustnrRspnsResult;
    }

    public static QustnrRespondInfoVO QustnrRespondInfoEntityToVO(QustnrRespondInfo qustnrRespondInfo) {
        QustnrRespondInfoVO qustnrRespondInfoVO = new QustnrRespondInfoVO();
        BeanUtils.copyProperties(qustnrRespondInfo, qustnrRespondInfoVO);
        qustnrRespondInfoVO.setQustnrTmplatId(qustnrRespondInfo.getQustnrRespondInfoId().getQustnrTmplatId());
        qustnrRespondInfoVO.setQestnrId(qustnrRespondInfo.getQustnrRespondInfoId().getQestnrId());
        qustnrRespondInfoVO.setQustnrRespondId(qustnrRespondInfo.getQustnrRespondInfoId().getQustnrRespondId());
        return qustnrRespondInfoVO;
    }

    public static QustnrRespondInfo QustnrRespondInfoVOTOEntity(QustnrRespondInfoVO qustnrRespondInfoVO) {
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
