package egovframework.com.sec.gmt.service.impl;

import egovframework.com.sec.gmt.entity.AuthorGroupInfo;
import egovframework.com.sec.gmt.repository.EgovAuthorGroupInfoRepository;
import egovframework.com.sec.gmt.service.AuthorGroupInfoVO;
import egovframework.com.sec.gmt.service.EgovAuthorGroupInfoService;
import egovframework.com.sec.gmt.util.EgovAuthorGroupInfoUtility;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Qualifier;
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

@Service("gmtEgovAuthorGroupInfoService")
public class EgovAuthorGroupInfoServiceImpl extends EgovAbstractServiceImpl implements EgovAuthorGroupInfoService {

    private final EgovAuthorGroupInfoRepository repository;
    private final EgovIdGnrService idgenService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EgovAuthorGroupInfoServiceImpl(
            EgovAuthorGroupInfoRepository repository,
            @Qualifier("egovIdGnrService") EgovIdGnrService idgenService
    ) {
        this.repository = repository;
        this.idgenService = idgenService;
    }

    @Override
    public Page<AuthorGroupInfoVO> list(AuthorGroupInfoVO authorGroupInfoVO) {
        Sort sort = Sort.by("groupCreatDe").descending();
        Pageable pageable = PageRequest.of(authorGroupInfoVO.getFirstIndex(), authorGroupInfoVO.getRecordCountPerPage(), sort);

        Specification<AuthorGroupInfo> spec = (root, query, criteriaBuilder) -> null;
        if (!ObjectUtils.isEmpty(authorGroupInfoVO.getSearchKeyword()) && "1".equals(authorGroupInfoVO.getSearchCondition())) {
            spec = spec.and(EgovAuthorGroupInfoSpecification.groupNmContains(authorGroupInfoVO.getSearchKeyword()));
            return repository.findAll(spec, pageable).map(EgovAuthorGroupInfoUtility::entityToVO);
        } else {
            return repository.findAll(pageable).map(EgovAuthorGroupInfoUtility::entityToVO);
        }
    }

    @Override
    public AuthorGroupInfoVO detail(AuthorGroupInfoVO authorGroupInfoVO) {
        String groupId = authorGroupInfoVO.getGroupId();
        return repository.findById(groupId).map(EgovAuthorGroupInfoUtility::entityToVO).orElse(null);
    }

    @Transactional
    @Override
    public AuthorGroupInfoVO insert(AuthorGroupInfoVO authorGroupInfoVO) throws FdlException {
        AuthorGroupInfo authorGroupInfo = EgovAuthorGroupInfoUtility.VOToEntity(authorGroupInfoVO);
        authorGroupInfo.setGroupId(idgenService.getNextStringId());
        authorGroupInfo.setGroupCreatDe(LocalDateTime.now().format(formatter));
        return EgovAuthorGroupInfoUtility.entityToVO(repository.save(authorGroupInfo));
    }

    @Transactional
    @Override
    public AuthorGroupInfoVO update(AuthorGroupInfoVO authorGroupInfoVO) {
        AuthorGroupInfo authorGroupInfo = EgovAuthorGroupInfoUtility.VOToEntity(authorGroupInfoVO);
        return repository.findById(authorGroupInfo.getGroupId())
                .map(result -> {
                    result.setGroupNm(authorGroupInfoVO.getGroupNm());
                    result.setGroupDc(authorGroupInfoVO.getGroupDc());
                    result.setGroupCreatDe(LocalDateTime.now().format(formatter));
                    return repository.save(result);
                })
                .map(EgovAuthorGroupInfoUtility::entityToVO).orElse(null);
    }

    @Transactional
    @Override
    public boolean delete(AuthorGroupInfoVO authorGroupInfoVO) {
        String groupId = authorGroupInfoVO.getGroupId();
        repository.deleteById(groupId);
        return !repository.existsById(groupId);
    }

}
