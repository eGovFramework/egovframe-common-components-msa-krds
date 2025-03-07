package egovframework.com.uss.olp.qri.service.Impl;

import egovframework.com.uss.olp.qri.entity.QustnrRespondInfo;
import egovframework.com.uss.olp.qri.entity.QustnrRspnsResult;
import egovframework.com.uss.olp.qri.repository.EgovQustnrRespondInfoRepository;
import egovframework.com.uss.olp.qri.repository.EgovQustnrRspnsResultRepository;
import egovframework.com.uss.olp.qri.service.*;
import egovframework.com.uss.olp.qri.util.EgovQustnrRspnsResultUtility;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
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

@Service("qriEgovQustnrRspnsResultService")
public class EgovQustnrRspnsResultServiceImpl extends EgovAbstractServiceImpl implements EgovQustnrRspnsResultService {

    private final EgovQustnrRspnsResultRepository repository;
    private final EgovQustnrRespondInfoRepository qustnrRespondInfoRepository;
    private final EgovIdGnrService idgenService;
    private final EgovIdGnrService qustnrRespondInfoIdgenService;

    public EgovQustnrRspnsResultServiceImpl(
            EgovQustnrRspnsResultRepository repository,
            EgovQustnrRespondInfoRepository qustnrRespondInfoRepository,
            @Qualifier("qustnrRespondInfoIdGnrService") EgovIdGnrService idgenService,
            @Qualifier("qustnrRespondManageIdGnrService") EgovIdGnrService qustnrRespondInfoIdgenService
    ) {
        this.repository = repository;
        this.qustnrRespondInfoRepository = qustnrRespondInfoRepository;
        this.idgenService = idgenService;
        this.qustnrRespondInfoIdgenService = qustnrRespondInfoIdgenService;
    }

    @Override
    public Page<QustnrRspnsResultDTO> list(QustnrRspnsResultVO qustnrRspnsResultVO) {
        Pageable pageable = PageRequest.of(qustnrRspnsResultVO.getFirstIndex(), qustnrRspnsResultVO.getPageSize());
        String searchCondition = qustnrRspnsResultVO.getSearchCondition();
        String searchKeyword = qustnrRspnsResultVO.getSearchKeyword();
        return repository.qustnrRspnsResultList(searchCondition, searchKeyword, pageable);
    }

    @Transactional
    @Override
    public boolean insert(QustnrRspnsResultVO qustnrRspnsResultVO, Map<String, String> userInfo) {
        try {
            // 설문응답자 저장
            QustnrRespondInfoVO qustnrRespondInfoVO = new QustnrRespondInfoVO();
            qustnrRespondInfoVO.setQustnrRespondId(qustnrRespondInfoIdgenService.getNextStringId());
            qustnrRespondInfoVO.setQustnrTmplatId(qustnrRspnsResultVO.getQustnrTmplatId());
            qustnrRespondInfoVO.setQestnrId(qustnrRspnsResultVO.getQestnrId());
            qustnrRespondInfoVO.setSexdstnCode(qustnrRspnsResultVO.getSexdstnCode());
            qustnrRespondInfoVO.setOccpTyCode(qustnrRspnsResultVO.getOccpTyCode());
            qustnrRespondInfoVO.setRespondNm(qustnrRspnsResultVO.getRespondNm());
            QustnrRespondInfo qustnrRespondInfo = EgovQustnrRspnsResultUtility.QustnrRespondInfoVOTOEntity(qustnrRespondInfoVO);
            qustnrRespondInfo.setFrstRegistPnttm(LocalDateTime.now());
            qustnrRespondInfo.setFrstRegisterId(userInfo.get("uniqId"));
            qustnrRespondInfo.setLastUpdtPnttm(LocalDateTime.now());
            qustnrRespondInfo.setLastUpdusrId(userInfo.get("uniqId"));

            qustnrRespondInfoRepository.save(qustnrRespondInfo);

            // 설문조사 저장
            String[] itemArray = qustnrRspnsResultVO.getQustnrItemList();
            for (String item : itemArray) {
                String[] itemList = item.split(",");
                if ("1".equals(itemList[0])) {
                    qustnrRspnsResultVO.setQustnrRspnsResultId(idgenService.getNextStringId());
                    qustnrRspnsResultVO.setQustnrQesitmId(itemList[1]);
                    qustnrRspnsResultVO.setQustnrIemId(itemList[2]);
                    qustnrRspnsResultVO.setRespondAnswerCn("");
                    qustnrRespondInfoVO.setRespondNm(qustnrRspnsResultVO.getRespondNm());
                } else {
                    qustnrRspnsResultVO.setQustnrRspnsResultId(idgenService.getNextStringId());
                    qustnrRspnsResultVO.setQustnrQesitmId(itemList[1]);
                    qustnrRspnsResultVO.setQustnrIemId("");
                    qustnrRspnsResultVO.setRespondAnswerCn(itemList[2]);
                }
                QustnrRspnsResult qustnrRspnsResult = EgovQustnrRspnsResultUtility.qustnrRspnsResultVOToEntity(qustnrRspnsResultVO);
                qustnrRspnsResult.setFrstRegistPnttm(LocalDateTime.now());
                qustnrRspnsResult.setFrstRegisterId(userInfo.get("uniqId"));
                qustnrRspnsResult.setLastUpdtPnttm(LocalDateTime.now());
                qustnrRspnsResult.setLastUpdusrId(userInfo.get("uniqId"));

                repository.save(qustnrRspnsResult);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<QustnrRspnsResultMCStatsDTO> qustnrRspnsResultMCStats(QustnrRspnsResultVO qustnrRspnsResultVO) {
        return repository.qustnrRspnsResultMCStats(qustnrRspnsResultVO.getQustnrTmplatId(), qustnrRspnsResultVO.getQestnrId());
    }

    @Override
    public List<QustnrRspnsResultESStatsDTO> qustnrRspnsResultESStats(QustnrRspnsResultVO qustnrRspnsResultVO) {
        return repository.qustnrRspnsResultESStats(qustnrRspnsResultVO.getQustnrTmplatId(), qustnrRspnsResultVO.getQestnrId());
    }

}
