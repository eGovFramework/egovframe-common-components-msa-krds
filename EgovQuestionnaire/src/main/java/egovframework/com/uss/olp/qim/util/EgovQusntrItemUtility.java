package egovframework.com.uss.olp.qim.util;

import egovframework.com.uss.olp.qim.entity.QustnrIem;
import egovframework.com.uss.olp.qim.entity.QustnrIemId;
import egovframework.com.uss.olp.qim.service.QustnrIemVO;
import org.springframework.beans.BeanUtils;

public class EgovQusntrItemUtility {

    public static QustnrIemVO qustnrIemEntityToVO(QustnrIem qustnrIem) {
        QustnrIemVO qustnrIemVO = new QustnrIemVO();
        qustnrIemVO.setQustnrTmplatId(qustnrIem.getQustnrIemId().getQestnrId());
        qustnrIemVO.setQestnrId(qustnrIem.getQustnrIemId().getQustnrTmplatId());
        qustnrIemVO.setQustnrQesitmId(qustnrIem.getQustnrIemId().getQustnrQesitmId());
        qustnrIemVO.setQustnrIemId(qustnrIem.getQustnrIemId().getQustnrIemId());
        BeanUtils.copyProperties(qustnrIem, qustnrIemVO);
        return qustnrIemVO;
    }

    public static QustnrIem QustnrIemVOToEntity(QustnrIemVO qustnrIemVO) {
        QustnrIem qustnrIem = new QustnrIem();
        QustnrIemId qustnrIemId = new QustnrIemId();
        qustnrIemId.setQustnrTmplatId(qustnrIemVO.getQustnrTmplatId());
        qustnrIemId.setQestnrId(qustnrIemVO.getQestnrId());
        qustnrIemId.setQustnrQesitmId(qustnrIemVO.getQustnrQesitmId());
        qustnrIemId.setQustnrIemId(qustnrIemVO.getQustnrIemId());
        qustnrIem.setQustnrIemId(qustnrIemId);
        BeanUtils.copyProperties(qustnrIemVO, qustnrIem);
        return qustnrIem;
    }

}
