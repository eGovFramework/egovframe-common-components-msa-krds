package egovframework.com.sec.ram.service.impl;

import egovframework.com.sec.ram.entity.AuthorInfo;
import egovframework.com.sec.ram.repository.EgovAuthorInfoRepository;
import egovframework.com.sec.ram.repository.EgovMenuCreateDetailRepository;
import egovframework.com.sec.ram.service.AuthorInfoVO;
import egovframework.com.sec.ram.service.EgovAuthorManageService;
import egovframework.com.sec.ram.util.EgovAuthorInfoUtility;
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
import java.time.format.DateTimeFormatter;

@Service("ramEgovAuthorInfoService")
@RequiredArgsConstructor
public class EgovAuthorInfoServiceImpl extends EgovAbstractServiceImpl implements EgovAuthorManageService {

    private final EgovAuthorInfoRepository repository;
    private final EgovMenuCreateDetailRepository menuCreateDetailRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Page<AuthorInfoVO> list(AuthorInfoVO authorInfoVO) {
        Sort sort = Sort.by("authorCreatDe").descending();
        Pageable pageable = PageRequest.of(authorInfoVO.getFirstIndex(), authorInfoVO.getRecordCountPerPage(), sort);

        Specification<AuthorInfo> spec = (root, query, criteriaBuilder) -> null;
        if (!ObjectUtils.isEmpty(authorInfoVO.getSearchKeyword()) && "1".equals(authorInfoVO.getSearchCondition())) {
            spec = spec.and(EgovAuthorInfoSpecification.authorNmContains(authorInfoVO.getSearchKeyword()));
            return repository.findAll(spec, pageable).map(EgovAuthorInfoUtility::authorInfoEntityToVO);
        } else {
            return repository.findAll(pageable).map(EgovAuthorInfoUtility::authorInfoEntityToVO);
        }
    }

    @Override
    public AuthorInfoVO detail(AuthorInfoVO authorInfoVO) {
        String authorCode = authorInfoVO.getAuthorCode();
        return repository.findById(authorCode).map(EgovAuthorInfoUtility::authorInfoEntityToVO).orElse(null);
    }

    @Transactional
    @Override
    public AuthorInfoVO insert(AuthorInfoVO authorInfoVO) {
        AuthorInfo authorInfo = EgovAuthorInfoUtility.authorInfoVOToEntity(authorInfoVO);
        authorInfo.setAuthorCreatDe(LocalDateTime.now().format(formatter));
        return EgovAuthorInfoUtility.authorInfoEntityToVO(repository.save(authorInfo));
    }

    @Transactional
    @Override
    public AuthorInfoVO update(AuthorInfoVO authorInfoVO) {
        boolean result = delete(authorInfoVO);
        if (result) {
            return insert(authorInfoVO);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public boolean delete(AuthorInfoVO authorInfoVO) {
        String authorCode = authorInfoVO.getOriginalAuthorCode();
        long count = menuCreateDetailRepository.countByAuthorCode(authorCode);
        if (count > 0) {
            return false;
        } else {
            repository.deleteById(authorCode);
            return true;
        }
    }

}
