package egovframework.com.sym.ccm.cca.service.impl;

import egovframework.com.sym.ccm.cca.entity.CmmnCode;
import egovframework.com.sym.ccm.cca.reposiroty.EgovCmmnCodeManageRepository;
import egovframework.com.sym.ccm.cca.service.CmmnCodeVO;
import egovframework.com.sym.ccm.cca.service.EgovCmmnCodeManageService;
import egovframework.com.sym.ccm.cca.util.EgovCmmnCodeUtility;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Map;

@Service("ccaEgovCmmnCodeManageServiceImpl")
@RequiredArgsConstructor
public class EgovCmmnCodeManageServiceImpl extends EgovAbstractServiceImpl implements EgovCmmnCodeManageService {

    private final EgovCmmnCodeManageRepository repository;

    @Override
    public Page<CmmnCodeVO> list(CmmnCodeVO cmmnCodeVO) {
        Sort sort = Sort.by("codeId").ascending();
        Pageable pageable = PageRequest.of(cmmnCodeVO.getFirstIndex(), cmmnCodeVO.getPageSize(), sort);

        Specification<CmmnCode> spec = (root, query, criteriaBuilder) -> null;
        if (!ObjectUtils.isEmpty(cmmnCodeVO.getSearchKeyword()) && "1".equals(cmmnCodeVO.getSearchCondition())) {
            spec = spec.and(EgovCmmnCodeManageSpecification.codeIdContains(cmmnCodeVO.getSearchKeyword()));
            return repository.findAll(spec, pageable).map(EgovCmmnCodeUtility::CmmnCodeAllEntityToVO);
        } else if (!ObjectUtils.isEmpty(cmmnCodeVO.getSearchKeyword()) && "2".equals(cmmnCodeVO.getSearchCondition())) {
            spec = spec.and(EgovCmmnCodeManageSpecification.codeIdNmContains(cmmnCodeVO.getSearchKeyword()));
            return repository.findAll(spec, pageable).map(EgovCmmnCodeUtility::CmmnCodeAllEntityToVO);
        } else {
            return repository.findAll(pageable).map(EgovCmmnCodeUtility::CmmnCodeAllEntityToVO);
        }
    }

    @Override
    public CmmnCodeVO detail(CmmnCodeVO cmmnCodeVO) {
        String codeId = cmmnCodeVO.getCodeId();
        return repository.findById(codeId).map(EgovCmmnCodeUtility::CmmnCodeAllEntityToVO).orElse(null);
    }

    @Transactional
    @Override
    public CmmnCodeVO insert(CmmnCodeVO cmmnCodeVO, Map<String, String> userInfo) {
        CmmnCode cmmnCode = EgovCmmnCodeUtility.CmmnCodeVOToEntity(cmmnCodeVO);
        cmmnCode.setFrstRegistPnttm(LocalDateTime.now());
        cmmnCode.setFrstRegisterId(userInfo.get("uniqId"));
        cmmnCode.setLastUpdtPnttm(LocalDateTime.now());
        cmmnCode.setLastUpdusrId(userInfo.get("uniqId"));
        return EgovCmmnCodeUtility.CmmnCodeEntityToVO(repository.save(cmmnCode));
    }

    @Transactional
    @Override
    public CmmnCodeVO update(CmmnCodeVO cmmnCodeVO, Map<String, String> userInfo) {
        CmmnCode cmmnCode = EgovCmmnCodeUtility.CmmnCodeVOToEntity(cmmnCodeVO);
        return repository.findById(cmmnCode.getCodeId())
                .map(result -> {
                    result.setCodeIdNm(cmmnCode.getCodeIdNm());
                    result.setCodeIdDc(cmmnCode.getCodeIdDc());
                    result.setUseAt(cmmnCode.getUseAt());
                    result.setLastUpdtPnttm(LocalDateTime.now());
                    result.setLastUpdusrId(userInfo.get("uniqId"));
                    return repository.save(result);
                })
                .map(EgovCmmnCodeUtility::CmmnCodeEntityToVO).orElse(null);
    }

    @Transactional
    @Override
    public CmmnCodeVO delete(CmmnCodeVO cmmnCodeVO, Map<String, String> userInfo) {
        CmmnCode cmmnCode = EgovCmmnCodeUtility.CmmnCodeVOToEntity(cmmnCodeVO);
        return repository.findById(cmmnCode.getCodeId())
                .map(result -> {
                    result.setUseAt("N");
                    result.setLastUpdtPnttm(LocalDateTime.now());
                    result.setLastUpdusrId(userInfo.get("uniqId"));
                    return repository.save(result);
                })
                .map(EgovCmmnCodeUtility::CmmnCodeEntityToVO).orElse(null);
    }

}
