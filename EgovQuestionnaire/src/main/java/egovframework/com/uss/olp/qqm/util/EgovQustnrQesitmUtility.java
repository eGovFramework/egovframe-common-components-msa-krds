package egovframework.com.uss.olp.qqm.util;

import egovframework.com.uss.olp.qqm.entity.QustnrQesitm;
import egovframework.com.uss.olp.qqm.entity.QustnrQesitmId;
import egovframework.com.uss.olp.qqm.service.QustnrQesitmVO;
import org.springframework.beans.BeanUtils;

public class EgovQustnrQesitmUtility {

    public static QustnrQesitmVO QustnrQesitmEntityToVO(QustnrQesitm qustnrQesitm) {
        QustnrQesitmVO qustnrQesitmVO = new QustnrQesitmVO();
        BeanUtils.copyProperties(qustnrQesitm, qustnrQesitmVO);
        qustnrQesitmVO.setQustnrTmplatId(qustnrQesitm.getQustnrQesitmId().getQustnrTmplatId());
        qustnrQesitmVO.setQestnrId(qustnrQesitm.getQustnrQesitmId().getQestnrId());
        qustnrQesitmVO.setQustnrQesitmId(qustnrQesitm.getQustnrQesitmId().getQustnrQesitmId());
        return qustnrQesitmVO;
    }

    public static QustnrQesitm QustnrQesitmVOToEntity(QustnrQesitmVO qustnrQesitmVO) {
        QustnrQesitmId qustnrQesitmId = new QustnrQesitmId();
        qustnrQesitmId.setQustnrTmplatId(qustnrQesitmVO.getQustnrTmplatId());
        qustnrQesitmId.setQestnrId(qustnrQesitmVO.getQestnrId());
        qustnrQesitmId.setQustnrQesitmId(qustnrQesitmVO.getQustnrQesitmId());

        QustnrQesitm qustnrQesitm = new QustnrQesitm();
        BeanUtils.copyProperties(qustnrQesitmVO, qustnrQesitm);
        qustnrQesitm.setQustnrQesitmId(qustnrQesitmId);
        return qustnrQesitm;
    }

}
