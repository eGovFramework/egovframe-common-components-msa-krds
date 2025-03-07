package egovframework.com.uss.olp.qtm.service.Impl;

import egovframework.com.uss.olp.qtm.entity.QustnrTmplat;
import egovframework.com.uss.olp.qtm.repository.*;
import egovframework.com.uss.olp.qtm.service.EgovQustnrTmplatService;
import egovframework.com.uss.olp.qtm.service.QustnrTmplatDTO;
import egovframework.com.uss.olp.qtm.service.QustnrTmplatVO;
import egovframework.com.uss.olp.qtm.util.EgovQustnrTmplatUtility;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Service("qtmEgovQustnrTmplatService")
public class EgovQustnrTmplatServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrTmplatService {

    private final EgovQustnrTmplatRepository repository;
    private final EgovQestnrInfoRepository egovQestnrInfoRepository;
    private final EgovQustnrQesitmRepository egovQustnrQesitmRepository;
    private final EgovQustnrIemRepository egovQustnrIemRepository;
    private final EgovQustnrRespondInfoRepository egovQustnrRespondInfoRepository;
    private final EgovQustnrRspnsResultRepository egovQustnrRspnsResultRepository;
    private final EgovIdGnrService idgenService;

    public EgovQustnrTmplatServiceImpl(
            EgovQustnrTmplatRepository repository,
            EgovQestnrInfoRepository egovQestnrInfoRepository,
            EgovQustnrQesitmRepository egovQustnrQesitmRepository,
            EgovQustnrIemRepository egovQustnrIemRepository,
            EgovQustnrRespondInfoRepository egovQustnrRespondInfoRepository,
            EgovQustnrRspnsResultRepository egovQustnrRspnsResultRepository,
            @Qualifier("egovQustnrTmplatManageIdGnrService") EgovIdGnrService idgenService) {
        this.repository = repository;
        this.egovQestnrInfoRepository = egovQestnrInfoRepository;
        this.egovQustnrQesitmRepository = egovQustnrQesitmRepository;
        this.egovQustnrIemRepository = egovQustnrIemRepository;
        this.egovQustnrRespondInfoRepository = egovQustnrRespondInfoRepository;
        this.egovQustnrRspnsResultRepository = egovQustnrRspnsResultRepository;
        this.idgenService = idgenService;
    }

    @Override
    public Page<QustnrTmplatDTO> list(QustnrTmplatVO qustnrTmplatVO) {
        Pageable pageable = PageRequest.of(qustnrTmplatVO.getFirstIndex(), qustnrTmplatVO.getRecordCountPerPage());
        String searchCondition = qustnrTmplatVO.getSearchCondition();
        String searchKeyword = qustnrTmplatVO.getSearchKeyword();
        return repository.qustnrTmplatList(searchCondition, searchKeyword, pageable);
    }

    @Override
    public QustnrTmplatDTO detail(QustnrTmplatVO qustnrTmplatVO) {
        return repository.qustnrTmplatDetail(qustnrTmplatVO.getQustnrTmplatId());
    }

    @Transactional
    @Override
    public QustnrTmplatVO insert(QustnrTmplatVO qustnrTmplatVO, Map<String, String> userInfo) throws FdlException, IOException {
        String qustnrTmplatId = idgenService.getNextStringId();
        qustnrTmplatVO.setQustnrTmplatId(qustnrTmplatId);

        QustnrTmplat qustnrTmplat = EgovQustnrTmplatUtility.QustnrTmplatVOToEntity(qustnrTmplatVO);
        qustnrTmplat.setQustnrTmplatImageInfo(qustnrTmplatVO.getQustnrTmplatImageInfo().getBytes());
        qustnrTmplat.setFrstRegistPnttm(LocalDateTime.now());
        qustnrTmplat.setFrstRegisterId(userInfo.get("uniqId"));
        qustnrTmplat.setLastUpdtPnttm(LocalDateTime.now());
        qustnrTmplat.setLastUpdusrId(userInfo.get("uniqId"));
        return EgovQustnrTmplatUtility.QustnrTmplatEntityToVO(repository.save(qustnrTmplat));
    }

    @Transactional
    @Override
    public QustnrTmplatVO update(QustnrTmplatVO qustnrTmplatVO, Map<String, String> userInfo) throws FdlException, IOException {
        String qustnrTmplatId = qustnrTmplatVO.getQustnrTmplatId();
        return repository.findById(qustnrTmplatId)
                .map(item -> {
                    try {
                        return updateItem(item, qustnrTmplatVO, userInfo.get("uniqId"));
                    } catch (IOException e) {
                        return null;
                    }
                })
                .map(EgovQustnrTmplatUtility::QustnrTmplatEntityToVO)
                .orElse(null);
    }

    @Transactional
    @Override
    public boolean delete(QustnrTmplatVO qustnrTmplatVO) {
        String qustnrTmplatId = qustnrTmplatVO.getQustnrTmplatId();
        return repository.findById(qustnrTmplatId)
                .map(result -> {
                    egovQustnrRspnsResultRepository.deleteByQustnrRspnsResultIdQustnrTmplatId(qustnrTmplatId);
                    egovQustnrRespondInfoRepository.deleteByQustnrRespondInfoIdQustnrTmplatId(qustnrTmplatId);
                    egovQustnrIemRepository.deleteByQustnrIemIdQustnrTmplatId(qustnrTmplatId);
                    egovQustnrQesitmRepository.deleteByQustnrQesitmIdQustnrTmplatId(qustnrTmplatId);
                    egovQestnrInfoRepository.deleteByQestnrInfoIdQustnrTmplatId(qustnrTmplatId);
                    repository.deleteById(qustnrTmplatId);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public byte[] getImage(String qustnrTmplatId) {
        return repository.findById(qustnrTmplatId).get().getQustnrTmplatImageInfo();
    }

    private QustnrTmplat updateItem(QustnrTmplat qustnrTmplat, QustnrTmplatVO qustnrTmplatVO, String uniqId) throws IOException {
        if (!"update".equals(qustnrTmplatVO.getQustnrTmplatImageState())) {
            qustnrTmplat.setQustnrTmplatImageInfo(qustnrTmplatVO.getQustnrTmplatImageInfo().getBytes());
        }
        qustnrTmplat.setQustnrTmplatTy(qustnrTmplatVO.getQustnrTmplatTy());
        qustnrTmplat.setQustnrTmplatDc(qustnrTmplatVO.getQustnrTmplatDc());
        qustnrTmplat.setQustnrTmplatPathNm(qustnrTmplatVO.getQustnrTmplatPathNm());
        qustnrTmplat.setLastUpdtPnttm(LocalDateTime.now());
        qustnrTmplat.setLastUpdusrId(uniqId);
        return qustnrTmplat;
    }

}
