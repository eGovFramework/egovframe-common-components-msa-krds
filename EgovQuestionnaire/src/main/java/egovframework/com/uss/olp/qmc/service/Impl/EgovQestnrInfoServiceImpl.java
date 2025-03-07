package egovframework.com.uss.olp.qmc.service.Impl;

import egovframework.com.uss.olp.qmc.entity.QestnrInfo;
import egovframework.com.uss.olp.qmc.entity.QestnrInfoId;
import egovframework.com.uss.olp.qmc.repository.*;
import egovframework.com.uss.olp.qmc.service.EgovQestnrInfoService;
import egovframework.com.uss.olp.qmc.service.QestnrInfoDTO;
import egovframework.com.uss.olp.qmc.service.QestnrInfoVO;
import egovframework.com.uss.olp.qmc.util.EgovQestnrInfoUtility;
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
import java.util.Map;

@Service("qmcEgovQestnrInfoService")
public class EgovQestnrInfoServiceImpl extends EgovAbstractServiceImpl implements EgovQestnrInfoService {

    private final EgovQestnrInfoRepository repository;
    private final EgovQustnrQesitmRepository qustnrQesitmRepository;
    private final EgovQustnrIemRepository qustnrIemRepository;
    private final EgovQustnrRespondInfoRepository qustnrRespondInfoRepository;
    private final EgovQustnrRspnsResultRepository qustnrRspnsResultRepository;
    private final EgovIdGnrService idgenService;

    public EgovQestnrInfoServiceImpl(
            EgovQestnrInfoRepository repository,
            EgovQustnrQesitmRepository qustnrQesitmRepository,
            EgovQustnrIemRepository qustnrIemRepository,
            EgovQustnrRespondInfoRepository qustnrRespondInfoRepository,
            EgovQustnrRspnsResultRepository qustnrRspnsResultRepository,
            @Qualifier("egovQustnrManageIdGnrService") EgovIdGnrService idgenService
    ) {
        this.repository = repository;
        this.qustnrQesitmRepository = qustnrQesitmRepository;
        this.qustnrIemRepository = qustnrIemRepository;
        this.qustnrRespondInfoRepository = qustnrRespondInfoRepository;
        this.qustnrRspnsResultRepository = qustnrRspnsResultRepository;
        this.idgenService = idgenService;
    }

    @Override
    public Page<QestnrInfoDTO> list(QestnrInfoVO qestnrInfoVO) {
        Pageable pageable = PageRequest.of(qestnrInfoVO.getFirstIndex(), qestnrInfoVO.getRecordCountPerPage());
        String searchCondition = qestnrInfoVO.getSearchCondition();
        String searchKeyword = qestnrInfoVO.getSearchKeyword();
        return repository.qestnrInfoList(searchCondition, searchKeyword, pageable);
    }

    @Override
    public QestnrInfoDTO detail(QestnrInfoVO qestnrInfoVO) {
        return repository.qestnrInfoDetail(qestnrInfoVO.getQestnrId());
    }

    @Transactional
    @Override
    public QestnrInfoVO insert(QestnrInfoVO qestnrInfoVO, Map<String, String> userInfo) throws FdlException {
        String qestnrId = idgenService.getNextStringId();
        qestnrInfoVO.setQestnrId(qestnrId);

        QestnrInfo qestnrInfo = EgovQestnrInfoUtility.QestnrInfoVOToEntiry(qestnrInfoVO);
        qestnrInfo.setQustnrBgnde(qestnrInfo.getQustnrBgnde().replace("-", ""));
        qestnrInfo.setQustnrEndde(qestnrInfo.getQustnrEndde().replace("-", ""));
        qestnrInfo.setFrstRegistPnttm(LocalDateTime.now());
        qestnrInfo.setFrstRegisterId(userInfo.get("uniqId"));
        qestnrInfo.setLastUpdtPnttm(LocalDateTime.now());
        qestnrInfo.setLastUpdusrId(userInfo.get("uniqId"));
        return EgovQestnrInfoUtility.QuesnrInfoEntityToVO(repository.save(qestnrInfo));
    }

    @Transactional
    @Override
    public QestnrInfoVO update(QestnrInfoVO qestnrInfoVO, Map<String, String> userInfo) {
        QestnrInfoId qestnrInfoId = new QestnrInfoId();
        qestnrInfoId.setQustnrTmplatId(qestnrInfoVO.getQustnrTmplatId());
        qestnrInfoId.setQestnrId(qestnrInfoVO.getQestnrId());

        return repository.findById(qestnrInfoId)
                .map(item -> updateItem(item, qestnrInfoVO, userInfo.get("uniqId")))
                .map(repository::save)
                .map(EgovQestnrInfoUtility::QuesnrInfoEntityToVO)
                .orElse(null);
    }

    @Transactional
    @Override
    public boolean delete(QestnrInfoVO qestnrInfoVO) {
        String qustnrTmplatId = qestnrInfoVO.getQustnrTmplatId();
        String qestnrId = qestnrInfoVO.getQestnrId();

        QestnrInfoId qestnrInfoId = new QestnrInfoId();
        qestnrInfoId.setQustnrTmplatId(qustnrTmplatId);
        qestnrInfoId.setQestnrId(qestnrId);

        return repository.findById(qestnrInfoId)
                .map(result -> {
                    qustnrRespondInfoRepository.deleteByQustnrRespondInfoIdQustnrTmplatIdAndQustnrRespondInfoIdQestnrId(qustnrTmplatId, qestnrId);
                    qustnrRspnsResultRepository.deleteByQustnrRspnsResultIdQustnrTmplatIdAndQustnrRspnsResultIdQestnrId(qustnrTmplatId, qestnrId);
                    qustnrIemRepository.deleteByQustnrIemIdQustnrTmplatIdAndQustnrIemIdQestnrId(qustnrTmplatId, qestnrId);
                    qustnrQesitmRepository.deleteByQustnrQesitmIdQustnrTmplatIdAndQustnrQesitmIdQestnrId(qustnrTmplatId, qestnrId);
                    repository.delete(result);
                    return true;
                })
                .orElse(false);
    }

    private QestnrInfo updateItem(QestnrInfo qestnrInfo, QestnrInfoVO qestnrInfoVO, String uniqId) {
        qestnrInfo.setQustnrSj(qestnrInfoVO.getQustnrSj());
        qestnrInfo.setQustnrPurps(qestnrInfoVO.getQustnrPurps());
        qestnrInfo.setQustnrWritingGuidanceCn(qestnrInfoVO.getQustnrWritingGuidanceCn());
        qestnrInfo.setQustnrTrget(qestnrInfoVO.getQustnrTrget());
        qestnrInfo.setQustnrBgnde(qestnrInfoVO.getQustnrBgnde());
        qestnrInfo.setQustnrEndde(qestnrInfoVO.getQustnrEndde());
        qestnrInfo.setLastUpdtPnttm(LocalDateTime.now());
        qestnrInfo.setLastUpdusrId(uniqId);
        return qestnrInfo;
    }

}
