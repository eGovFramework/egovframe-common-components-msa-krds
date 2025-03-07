package egovframework.com.uss.olp.qim.service.Impl;

import egovframework.com.uss.olp.qim.entity.QustnrIem;
import egovframework.com.uss.olp.qim.entity.QustnrIemId;
import egovframework.com.uss.olp.qim.repository.EgovQusntrItemRepository;
import egovframework.com.uss.olp.qim.service.EgovQustnrItemService;
import egovframework.com.uss.olp.qim.service.QustnrIemDTO;
import egovframework.com.uss.olp.qim.service.QustnrIemVO;
import egovframework.com.uss.olp.qim.util.EgovQusntrItemUtility;
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

@Service("qimEgovQustnrItemService")
public class EgovQustnrItemServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrItemService {

    private final EgovQusntrItemRepository repository;
    private final EgovIdGnrService idgenService;

    public EgovQustnrItemServiceImpl(
            EgovQusntrItemRepository repository,
            @Qualifier("egovQustnrItemManageIdGnrService") EgovIdGnrService idgenService
    ) {
        this.repository = repository;
        this.idgenService = idgenService;
    }

    @Override
    public Page<QustnrIemDTO> list(QustnrIemVO qustnrIemVO) {
        Pageable pageable = PageRequest.of(qustnrIemVO.getFirstIndex(), qustnrIemVO.getRecordCountPerPage());
        String searchCondition = qustnrIemVO.getSearchCondition();
        String searchKeyword = qustnrIemVO.getSearchKeyword();
        return repository.qustnrItemList(searchCondition, searchKeyword, pageable);
    }

    @Override
    public QustnrIemDTO detail(QustnrIemVO qustnrIemVO) {
        return repository.qustnrItemDetail(qustnrIemVO.getQustnrIemId());
    }

    @Transactional
    @Override
    public QustnrIemVO insert(QustnrIemVO qustnrIemVO, Map<String, String> userInfo) throws FdlException {
        String itemId = idgenService.getNextStringId();
        qustnrIemVO.setQustnrIemId(itemId);
        QustnrIem qustnrIem = EgovQusntrItemUtility.QustnrIemVOToEntity(qustnrIemVO);
        qustnrIem.setFrstRegistPnttm(LocalDateTime.now());
        qustnrIem.setFrstRegisterId(userInfo.get("uniqId"));
        qustnrIem.setLastUpdtPnttm(LocalDateTime.now());
        qustnrIem.setLastUpdusrId(userInfo.get("uniqId"));
        return EgovQusntrItemUtility.qustnrIemEntityToVO(repository.save(qustnrIem));
    }

    @Transactional
    @Override
    public QustnrIemVO update(QustnrIemVO qustnrIemVO, Map<String, String> userInfo) {
        QustnrIemId qustnrIemId = new QustnrIemId();
        qustnrIemId.setQustnrTmplatId(qustnrIemVO.getQustnrTmplatId());
        qustnrIemId.setQestnrId(qustnrIemVO.getQestnrId());
        qustnrIemId.setQustnrQesitmId(qustnrIemVO.getQustnrQesitmId());
        qustnrIemId.setQustnrIemId(qustnrIemVO.getQustnrIemId());

        return repository.findById(qustnrIemId)
                .map(existingItem -> updateExistingItem(existingItem, qustnrIemVO, userInfo.get("uniqId")))
                .map(repository::save)
                .map(EgovQusntrItemUtility::qustnrIemEntityToVO)
                .orElse(null);
    }

    @Transactional
    @Override
    public boolean delete(QustnrIemVO qustnrIemVO) {
        QustnrIemId qustnrIemId = new QustnrIemId();
        qustnrIemId.setQustnrTmplatId(qustnrIemVO.getQustnrTmplatId());
        qustnrIemId.setQestnrId(qustnrIemVO.getQestnrId());
        qustnrIemId.setQustnrQesitmId(qustnrIemVO.getQustnrQesitmId());
        qustnrIemId.setQustnrIemId(qustnrIemVO.getQustnrIemId());

        return repository.findById(qustnrIemId)
                .map(result -> {
                    repository.delete(result);
                    return true;
                })
                .orElse(false);
    }

    private QustnrIem updateExistingItem(QustnrIem existingItem, QustnrIemVO qustnrIemVO, String uniqId) {
        existingItem.setIemSn(qustnrIemVO.getIemSn());
        existingItem.setIemCn(qustnrIemVO.getIemCn());
        existingItem.setLastUpdtPnttm(LocalDateTime.now());
        existingItem.setLastUpdusrId(uniqId);
        return existingItem;
    }

}
