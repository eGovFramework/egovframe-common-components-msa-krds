package egovframework.com.uss.olp.qrm.service.Impl;

import egovframework.com.uss.olp.qrm.entity.QustnrRespondInfo;
import egovframework.com.uss.olp.qrm.entity.QustnrRespondInfoId;
import egovframework.com.uss.olp.qrm.repository.EgovQustnrRespondInfoRepository;
import egovframework.com.uss.olp.qrm.service.EgovQustnrRespondInfoService;
import egovframework.com.uss.olp.qrm.service.QustnrRespondInfoDTO;
import egovframework.com.uss.olp.qrm.service.QustnrRespondInfoVO;
import egovframework.com.uss.olp.qrm.util.EgovQustnrRespondInfoUtility;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service("qrmEgovQustnrRespondInfoService")
public class EgovQustnrRespondInfoServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrRespondInfoService {

    private final EgovQustnrRespondInfoRepository repository;
    private final EgovIdGnrService idgenService;

    public EgovQustnrRespondInfoServiceImpl(
            EgovQustnrRespondInfoRepository repository,
            @Qualifier("qustnrRespondManageIdGnrService") EgovIdGnrService idgenService) {
        this.repository = repository;
        this.idgenService = idgenService;
    }

    @Override
    public Page<QustnrRespondInfoDTO> list(QustnrRespondInfoVO qustnrRespondInfoVO) {
        Pageable pageable = PageRequest.of(qustnrRespondInfoVO.getFirstIndex(), qustnrRespondInfoVO.getRecordCountPerPage());
        String searchCondition = qustnrRespondInfoVO.getSearchCondition();
        String searchKeyword = qustnrRespondInfoVO.getSearchKeyword();
        return repository.qustnrRespondInfoList(searchCondition, searchKeyword, pageable);
    }

    @Override
    public QustnrRespondInfoDTO detail(QustnrRespondInfoVO qustnrRespondInfoVO) {
        return repository.qustnrRespondInfoDetail(qustnrRespondInfoVO.getQustnrRespondId());
    }

    @Transactional
    @Override
    public QustnrRespondInfoVO insert(QustnrRespondInfoVO qustnrRespondInfoVO) throws FdlException {
        String qustnrRespondId = idgenService.getNextStringId();
        qustnrRespondInfoVO.setQustnrRespondId(qustnrRespondId);

        QustnrRespondInfo qustnrRespondInfo = EgovQustnrRespondInfoUtility.QustnrRespondInfoVOTOEntity(qustnrRespondInfoVO);
        qustnrRespondInfo.setFrstRegistPnttm(LocalDateTime.now());
        qustnrRespondInfo.setLastUpdtPnttm(LocalDateTime.now());

        return EgovQustnrRespondInfoUtility.QustnrRespondInfoEntityToVO(repository.save(qustnrRespondInfo));
    }

    @Transactional
    @Override
    public QustnrRespondInfoVO update(QustnrRespondInfoVO qustnrRespondInfoVO) {
        QustnrRespondInfoId qustnrRespondInfoId = new QustnrRespondInfoId();
        qustnrRespondInfoId.setQustnrTmplatId(qustnrRespondInfoVO.getQustnrTmplatId());
        qustnrRespondInfoId.setQestnrId(qustnrRespondInfoVO.getQestnrId());
        qustnrRespondInfoId.setQustnrRespondId(qustnrRespondInfoVO.getQustnrRespondId());

        return repository.findById(qustnrRespondInfoId)
                .map(item -> updateItem(item, qustnrRespondInfoVO))
                .map(repository::save)
                .map(EgovQustnrRespondInfoUtility::QustnrRespondInfoEntityToVO)
                .orElse(null);
    }

    @Transactional
    @Override
    public boolean delete(QustnrRespondInfoVO qustnrRespondInfoVO) {
        QustnrRespondInfoId qustnrRespondInfoId = new QustnrRespondInfoId();
        qustnrRespondInfoId.setQustnrTmplatId(qustnrRespondInfoVO.getQustnrTmplatId());
        qustnrRespondInfoId.setQestnrId(qustnrRespondInfoVO.getQestnrId());
        qustnrRespondInfoId.setQustnrRespondId(qustnrRespondInfoVO.getQustnrRespondId());

        return repository.findById(qustnrRespondInfoId)
                .map(result -> {
                    repository.delete(result);
                    return true;
                })
                .orElse(false);
    }

    private QustnrRespondInfo updateItem(QustnrRespondInfo qustnrRespondInfo, QustnrRespondInfoVO qustnrRespondInfoVO) {
        qustnrRespondInfo.setSexdstnCode(qustnrRespondInfoVO.getSexdstnCode());
        qustnrRespondInfo.setOccpTyCode(qustnrRespondInfoVO.getOccpTyCode());
        qustnrRespondInfo.setRespondNm(qustnrRespondInfoVO.getRespondNm());
        qustnrRespondInfo.setLastUpdtPnttm(LocalDateTime.now());
        qustnrRespondInfo.setLastUpdusrId(qustnrRespondInfoVO.getLastUpdusrId());
        return qustnrRespondInfo;
    }

}
