package egovframework.com.sec.rgm.service.impl;

import egovframework.com.sec.rgm.entity.AuthorGroupInfo;
import egovframework.com.sec.rgm.repository.EgovAuthorGroupInfoRepository;
import egovframework.com.sec.rgm.service.AuthorGroupInfoVO;
import egovframework.com.sec.rgm.service.EgovAuthorGroupInfoService;
import egovframework.com.sec.rgm.util.EgovAuthorGroupUtility;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service("rgmEgovAuthorGroupInfoService")
@RequiredArgsConstructor
public class EgovAuthorGroupInfoServiceImpl extends EgovAbstractServiceImpl implements EgovAuthorGroupInfoService {

    private final EgovAuthorGroupInfoRepository repository;

    @Override
    public Page<AuthorGroupInfoVO> list(AuthorGroupInfoVO authorGroupInfoVO) {
        Sort sort = Sort.by("groupCreatDe").descending();
        Pageable pageable = PageRequest.of(authorGroupInfoVO.getFirstIndex(), authorGroupInfoVO.getRecordCountPerPage(), sort);

        Specification<AuthorGroupInfo> spec = (root, query, criteriaBuilder) -> null;
        if (!ObjectUtils.isEmpty(authorGroupInfoVO.getSearchKeyword()) && "1".equals(authorGroupInfoVO.getSearchCondition())) {
            spec = spec.and(EgovAuthorGroupInfoSpecification.groupNmContains(authorGroupInfoVO.getSearchKeyword()));
            return repository.findAll(spec, pageable).map(EgovAuthorGroupUtility::authorGroupInfoEntityToVO);
        } else {
            return repository.findAll(pageable).map(EgovAuthorGroupUtility::authorGroupInfoEntityToVO);
        }
    }

}
