package egovframework.com.uss.olp.qqm.service.Impl;

import egovframework.com.uss.olp.qqm.entity.QustnrQesitm;
import egovframework.com.uss.olp.qqm.entity.QustnrQesitmId;
import egovframework.com.uss.olp.qqm.repository.EgovQustnrQesitmRepository;
import egovframework.com.uss.olp.qqm.service.EgovQustnrQesitmService;
import egovframework.com.uss.olp.qqm.service.QustnrQesitmDTO;
import egovframework.com.uss.olp.qqm.service.QustnrQesitmVO;
import egovframework.com.uss.olp.qqm.util.EgovQustnrQesitmUtility;
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
import java.util.List;
import java.util.Map;

@Service("qqmEgovQustnrQesitmService")
public class EgovQustnrQesitmServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrQesitmService {

    private final EgovQustnrQesitmRepository repository;
    private final EgovIdGnrService idgenService;

    public EgovQustnrQesitmServiceImpl(
            EgovQustnrQesitmRepository repository,
            @Qualifier("egovQustnrQestnManageIdGnrService") EgovIdGnrService idgenService
    ) {
        this.repository = repository;
        this.idgenService = idgenService;
    }

    @Override
    public Page<QustnrQesitmDTO> list(QustnrQesitmVO qustnrQesitmVO) {
        Pageable pageable = PageRequest.of(qustnrQesitmVO.getFirstIndex(), qustnrQesitmVO.getRecordCountPerPage());
        String searchCondition = qustnrQesitmVO.getSearchCondition();
        String searchKeyword = qustnrQesitmVO.getSearchKeyword();
        return repository.qustnrQusitmList(searchCondition, searchKeyword, pageable);
    }

    @Override
    public QustnrQesitmDTO detail(QustnrQesitmVO qustnrQesitmVO) {
        return repository.qustnrQusitmDetail(qustnrQesitmVO.getQustnrQesitmId());
    }

    @Transactional
    @Override
    public QustnrQesitmVO insert(QustnrQesitmVO qustnrQesitmVO, Map<String, String> userInfo) throws FdlException {
        String qustnrQesitmId = idgenService.getNextStringId();
        qustnrQesitmVO.setQustnrQesitmId(qustnrQesitmId);

        QustnrQesitm qustnrQesitm = EgovQustnrQesitmUtility.QustnrQesitmVOToEntity(qustnrQesitmVO);
        qustnrQesitm.setFrstRegistPnttm(LocalDateTime.now());
        qustnrQesitm.setFrstRegisterId(userInfo.get("uniqId"));
        qustnrQesitm.setLastUpdtPnttm(LocalDateTime.now());
        qustnrQesitm.setLastUpdusrId(userInfo.get("uniqId"));

        return EgovQustnrQesitmUtility.QustnrQesitmEntityToVO(repository.save(qustnrQesitm));
    }

    @Transactional
    @Override
    public QustnrQesitmVO update(QustnrQesitmVO qustnrQesitmVO, Map<String, String> userInfo) {
        QustnrQesitmId qustnrQesitmId = new QustnrQesitmId();
        qustnrQesitmId.setQustnrTmplatId(qustnrQesitmVO.getQustnrTmplatId());
        qustnrQesitmId.setQestnrId(qustnrQesitmVO.getQestnrId());
        qustnrQesitmId.setQustnrQesitmId(qustnrQesitmVO.getQustnrQesitmId());

        return repository.findById(qustnrQesitmId)
                .map(item -> updateItem(item, qustnrQesitmVO, userInfo.get("uniqId")))
                .map(repository::save)
                .map(EgovQustnrQesitmUtility::QustnrQesitmEntityToVO)
                .orElse(null);
    }

    @Transactional
    @Override
    public boolean delete(QustnrQesitmVO qustnrQesitmVO) {
        QustnrQesitmId qustnrQesitmId = new QustnrQesitmId();
        qustnrQesitmId.setQustnrTmplatId(qustnrQesitmVO.getQustnrTmplatId());
        qustnrQesitmId.setQestnrId(qustnrQesitmVO.getQestnrId());
        qustnrQesitmId.setQustnrQesitmId(qustnrQesitmVO.getQustnrQesitmId());

        return repository.findById(qustnrQesitmId)
                .map(result -> {
                    repository.delete(result);
                    return true;
                })
                .orElse(false);
    }

    private QustnrQesitm updateItem(QustnrQesitm qustnrQesitm, QustnrQesitmVO qustnrQesitmVO, String uniqId) {
        qustnrQesitm.setQestnSn(qustnrQesitmVO.getQestnSn());
        qustnrQesitm.setQestnTyCode(qustnrQesitmVO.getQestnTyCode());
        qustnrQesitm.setQestnCn(qustnrQesitmVO.getQestnCn());
        qustnrQesitm.setMxmmChoiseCo(qustnrQesitmVO.getMxmmChoiseCo());
        qustnrQesitm.setLastUpdtPnttm(LocalDateTime.now());
        qustnrQesitm.setLastUpdusrId(uniqId);
        return qustnrQesitm;
    }

    @Override
    public List<QustnrQesitmDTO> qustnrQusitmList(QustnrQesitmVO qustnrQesitmVO) {
        String qustnrTmplatId = qustnrQesitmVO.getQustnrTmplatId();
        String qestnrId = qustnrQesitmVO.getQestnrId();
        String qustnrQesitmId = qustnrQesitmVO.getQustnrQesitmId();
        return repository.qustnrQusitmList(qustnrTmplatId, qestnrId);
    }

}
