package egovframework.com.cop.tpl.service.impl;

import egovframework.com.cop.tpl.entity.TmplatInfo;
import egovframework.com.cop.tpl.repository.EgovTemplateRepository;
import egovframework.com.cop.tpl.service.EgovTemplateService;
import egovframework.com.cop.tpl.service.TemplateDTO;
import egovframework.com.cop.tpl.service.TemplateVO;
import egovframework.com.cop.tpl.util.EgovTemplateUtility;
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

@Service("tplEgovTemplateServcieImpl")
public class EgovTemplateServcieImpl extends EgovAbstractServiceImpl implements EgovTemplateService {

    private final EgovTemplateRepository repository;
    private final EgovIdGnrService idgenService;

    public EgovTemplateServcieImpl(
            EgovTemplateRepository repository,
            @Qualifier("egovTmplatIdGnrService") EgovIdGnrService idgenService
    ) {
        this.repository = repository;
        this.idgenService = idgenService;
    }

    @Override
    public Page<TemplateDTO> list(TemplateVO templateVO) {
        Pageable pageable = PageRequest.of(templateVO.getFirstIndex(), templateVO.getRecordCountPerPage());
        String searchCondition = templateVO.getSearchCondition();
        String searchKeyword = templateVO.getSearchKeyword();
        return repository.templateList(searchCondition, searchKeyword, pageable);
    }

    @Override
    public TemplateDTO detail(TemplateVO templateVO) {
        return repository.templateDetail(templateVO.getTmplatId());
    }

    @Transactional
    @Override
    public TemplateVO insert(TemplateVO templateVO, Map<String, String> userInfo) throws FdlException {
        String tmplatId = idgenService.getNextStringId();
        templateVO.setTmplatId(tmplatId);
        templateVO.setFrstRegisterId(userInfo.get("uniqId"));
        templateVO.setFrstRegistPnttm(LocalDateTime.now());
        TmplatInfo tmplatInfo = repository.save(EgovTemplateUtility.templateVOToEntity(templateVO));
        return EgovTemplateUtility.templateEntityToVO(tmplatInfo);
    }

    @Transactional
    @Override
    public TemplateVO update(TemplateVO templateVO, Map<String, String> userInfo) {
        return repository.findById(templateVO.getTmplatId())
                .map(result -> {
                    result.setTmplatSeCode(templateVO.getTmplatSeCode());
                    result.setTmplatCours(templateVO.getTmplatCours());
                    result.setUseAt(templateVO.getUseAt());
                    result.setLastUpdusrId(userInfo.get("uniqId"));
                    result.setLastUpdtPnttm(LocalDateTime.now());
                    return repository.save(result);
                })
                .map(EgovTemplateUtility::templateEntityToVO).orElse(null);
    }

    @Transactional
    @Override
    public void delete(TemplateVO templateVO) {
        repository.deleteById(templateVO.getTmplatId());
    }

}
