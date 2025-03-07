package egovframework.com.sym.ccm.cde.service.impl;

import egovframework.com.sym.ccm.cde.entity.CmmnCode;
import egovframework.com.sym.ccm.cde.entity.CmmnDetailCode;
import egovframework.com.sym.ccm.cde.repository.EgovCmmnDetailCodeManageRepository;
import egovframework.com.sym.ccm.cde.service.CmmnDetailCodeVO;
import egovframework.com.sym.ccm.cde.service.EgovCmmnDetailCodeManageService;
import egovframework.com.sym.ccm.cde.util.EgovCmmnDetailCodeUtility;
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

@Service("cdeEgovCmmnDetailCodeManageServiceImpl")
@RequiredArgsConstructor
public class EgovCmmnDetailCodeManageServiceImpl extends EgovAbstractServiceImpl implements EgovCmmnDetailCodeManageService {

    private final EgovCmmnDetailCodeManageRepository repository;

    @Override
    public Page<CmmnDetailCodeVO> list(CmmnDetailCodeVO cmmnDetailCodeVO) {
        Sort sort = Sort.by("cmmnDetailCodeId.codeId").ascending();
        Pageable pageable = PageRequest.of(cmmnDetailCodeVO.getFirstIndex(), cmmnDetailCodeVO.getPageSize(), sort);

        Specification<CmmnDetailCode> spec = (root, query, criteriaBuilder) -> null;
        if (!ObjectUtils.isEmpty(cmmnDetailCodeVO.getSearchKeyword()) && "1".equals(cmmnDetailCodeVO.getSearchCondition())) {
            spec = spec.and(EgovCmmnDetailCodeManageSpecification.codeIdContains(cmmnDetailCodeVO.getSearchKeyword()));
            return repository.findAll(spec, pageable).map(EgovCmmnDetailCodeUtility::entityToVO);
        } else if (!ObjectUtils.isEmpty(cmmnDetailCodeVO.getSearchKeyword()) && "2".equals(cmmnDetailCodeVO.getSearchCondition())) {
            spec = spec.and(EgovCmmnDetailCodeManageSpecification.codeContains(cmmnDetailCodeVO.getSearchKeyword()));
            return repository.findAll(spec, pageable).map(EgovCmmnDetailCodeUtility::entityToVO);
        } else if (!ObjectUtils.isEmpty(cmmnDetailCodeVO.getSearchKeyword()) && "3".equals(cmmnDetailCodeVO.getSearchCondition())) {
            spec = spec.and(EgovCmmnDetailCodeManageSpecification.codeNmContains(cmmnDetailCodeVO.getSearchKeyword()));
            return repository.findAll(spec, pageable).map(EgovCmmnDetailCodeUtility::entityToVO);
        } else {
            spec = spec.and(EgovCmmnDetailCodeManageSpecification.useAtContains());
            return repository.findAll(spec, pageable).map(EgovCmmnDetailCodeUtility::entityToVO);
        }
    }

    @Override
    public CmmnDetailCodeVO detail(CmmnDetailCodeVO cmmnDetailCodeVO) {
        String codeId = cmmnDetailCodeVO.getCodeId();
        String code = cmmnDetailCodeVO.getCode();
        return repository.findByCmmnDetailCodeId_CodeIdAndCmmnDetailCodeId_Code(codeId, code).map(EgovCmmnDetailCodeUtility::entityToVO).orElse(null);
    }

    @Transactional
    @Override
    public CmmnDetailCodeVO insert(CmmnDetailCodeVO cmmnDetailCodeVO, Map<String, String> userInfo) {
        CmmnCode cmmnCode = new CmmnCode();
        cmmnCode.setCodeId(cmmnDetailCodeVO.getCodeId());

        CmmnDetailCode cmmnDetailCode = EgovCmmnDetailCodeUtility.VOToEntity(cmmnDetailCodeVO);
        cmmnDetailCode.setFrstRegistPnttm(LocalDateTime.now());
        cmmnDetailCode.setFrstRegisterId(userInfo.get("uniqId"));
        cmmnDetailCode.setLastUpdtPnttm(LocalDateTime.now());
        cmmnDetailCode.setLastUpdusrId(userInfo.get("uniqId"));

        // 관계 설정
        cmmnDetailCode.setCmmnCode(cmmnCode);

        return EgovCmmnDetailCodeUtility.entityToVO(repository.save(cmmnDetailCode));
    }

    @Transactional
    @Override
    public CmmnDetailCodeVO update(CmmnDetailCodeVO cmmnDetailCodeVO, Map<String, String> userInfo) {
        String codeId = cmmnDetailCodeVO.getCodeId();
        String code = cmmnDetailCodeVO.getCode();
        CmmnDetailCode cmmnDetailCode = EgovCmmnDetailCodeUtility.VOToEntity(cmmnDetailCodeVO);
        return repository.findByCmmnDetailCodeId_CodeIdAndCmmnDetailCodeId_Code(codeId, code)
                .map(result -> {
                    result.setCodeNm(cmmnDetailCode.getCodeNm());
                    result.setCodeDc(cmmnDetailCode.getCodeDc());
                    result.setUseAt(cmmnDetailCode.getUseAt());
                    result.setLastUpdtPnttm(LocalDateTime.now());
                    result.setLastUpdusrId(userInfo.get("uniqId"));
                    return repository.save(result);
                })
                .map(EgovCmmnDetailCodeUtility::entityToVO).orElse(null);
    }

    @Transactional
    @Override
    public CmmnDetailCodeVO delete(CmmnDetailCodeVO cmmnDetailCodeVO, Map<String, String> userInfo) {
        String codeId = cmmnDetailCodeVO.getCodeId();
        String code = cmmnDetailCodeVO.getCode();
        return repository.findByCmmnDetailCodeId_CodeIdAndCmmnDetailCodeId_Code(codeId, code)
                .map(result -> {
                    result.setUseAt("N");
                    result.setLastUpdtPnttm(LocalDateTime.now());
                    result.setLastUpdusrId(userInfo.get("uniqId"));
                    return repository.save(result);
                })
                .map(EgovCmmnDetailCodeUtility::entityToVO).orElse(null);
    }

}
