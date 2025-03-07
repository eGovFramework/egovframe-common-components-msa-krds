package egovframework.com.sym.ccm.ccc.service.impl;

import egovframework.com.sym.ccm.ccc.entity.CmmnClCode;
import egovframework.com.sym.ccm.ccc.repository.EgovCmmnClCodeManageRepository;
import egovframework.com.sym.ccm.ccc.service.CmmnClCodeVO;
import egovframework.com.sym.ccm.ccc.service.EgovCmmnClCodeManageService;
import egovframework.com.sym.ccm.ccc.util.EgovCmmnClCodeUtility;
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

@Service("cccEgovCmmnClCodeManageServiceImpl")
@RequiredArgsConstructor
public class EgovCmmnClCodeManageServiceImpl extends EgovAbstractServiceImpl implements EgovCmmnClCodeManageService {

    private final EgovCmmnClCodeManageRepository repository;

    @Override
    public Page<CmmnClCodeVO> list(CmmnClCodeVO cmmnClCodeVO) {
        Sort sort = Sort.by("clCode").ascending();
        Pageable pageable = PageRequest.of(cmmnClCodeVO.getFirstIndex(), cmmnClCodeVO.getPageSize(), sort);

        Specification<CmmnClCode> spec = (root, query, criteriaBuilder) -> null;
        if (!ObjectUtils.isEmpty(cmmnClCodeVO.getSearchKeyword()) && "1".equals(cmmnClCodeVO.getSearchCondition())) {
            spec = spec.and(EgovCmmnClCodeManageSpecification.clCodeContains(cmmnClCodeVO.getSearchKeyword()));
            return repository.findAll(spec, pageable).map(EgovCmmnClCodeUtility::CmmnClCodeEntityToVO);
        } else if (!ObjectUtils.isEmpty(cmmnClCodeVO.getSearchKeyword()) && "2".equals(cmmnClCodeVO.getSearchCondition())) {
            spec = spec.and(EgovCmmnClCodeManageSpecification.clCodeNmContains(cmmnClCodeVO.getSearchKeyword()));
            return repository.findAll(spec, pageable).map(EgovCmmnClCodeUtility::CmmnClCodeEntityToVO);
        } else {
            return repository.findAll(pageable).map(EgovCmmnClCodeUtility::CmmnClCodeEntityToVO);
        }
    }

    @Override
    public CmmnClCodeVO detail(CmmnClCodeVO cmmnClCodeVO) {
        String clCode = cmmnClCodeVO.getClCode();
        return repository.findById(clCode).map(EgovCmmnClCodeUtility::CmmnClCodeEntityToVO).orElse(null);
    }

    @Transactional
    @Override
    public CmmnClCodeVO insert(CmmnClCodeVO cmmnClCodeVO, Map<String, String> userInfo) {
        CmmnClCode cmmnClCode = EgovCmmnClCodeUtility.CmmnClCodeVOToEntity(cmmnClCodeVO);
        cmmnClCode.setFrstRegistPnttm(LocalDateTime.now());
        cmmnClCode.setFrstRegisterId(userInfo.get("uniqId"));
        cmmnClCode.setLastUpdtPnttm(LocalDateTime.now());
        cmmnClCode.setLastUpdusrId(userInfo.get("uniqId"));
        return EgovCmmnClCodeUtility.CmmnClCodeEntityToVO(repository.save(cmmnClCode));
    }

    @Transactional
    @Override
    public CmmnClCodeVO update(CmmnClCodeVO cmmnClCodeVO, Map<String, String> userInfo) {
        CmmnClCode cmmnClCode = EgovCmmnClCodeUtility.CmmnClCodeVOToEntity(cmmnClCodeVO);
        return repository.findById(cmmnClCode.getClCode())
                .map(result -> {
                    result.setClCodeNm(cmmnClCode.getClCodeNm());
                    result.setClCodeDc(cmmnClCode.getClCodeDc());
                    result.setUseAt(cmmnClCode.getUseAt());
                    result.setLastUpdtPnttm(LocalDateTime.now());
                    result.setLastUpdusrId(userInfo.get("uniqId"));
                    return repository.save(result);
                })
                .map(EgovCmmnClCodeUtility::CmmnClCodeEntityToVO).orElse(null);
    }

    @Transactional
    @Override
    public CmmnClCodeVO delete(CmmnClCodeVO cmmnClCodeVO, Map<String, String> userInfo) {
        CmmnClCode cmmnClCode = EgovCmmnClCodeUtility.CmmnClCodeVOToEntity(cmmnClCodeVO);
        return repository.findById(cmmnClCode.getClCode())
                .map(result -> {
                    result.setUseAt("N");
                    result.setLastUpdtPnttm(LocalDateTime.now());
                    result.setLastUpdusrId(userInfo.get("uniqId"));
                    return repository.save(result);
                })
                .map(EgovCmmnClCodeUtility::CmmnClCodeEntityToVO).orElse(null);
    }

}
