package egovframework.com.sec.rgm.service.impl;

import egovframework.com.sec.rgm.entity.Emplyrscrtyestbs;
import egovframework.com.sec.rgm.repository.EgovAuthorGroupRepository;
import egovframework.com.sec.rgm.repository.EgovAuthorInfoRepository;
import egovframework.com.sec.rgm.service.AuthorGroupDTO;
import egovframework.com.sec.rgm.service.AuthorGroupVO;
import egovframework.com.sec.rgm.service.AuthorInfoVO;
import egovframework.com.sec.rgm.service.EgovAuthorGroupService;
import egovframework.com.sec.rgm.util.EgovAuthorGroupUtility;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("rgmEgovAuthorGroupService")
@RequiredArgsConstructor
public class EgovAuthorGroupServiceImpl extends EgovAbstractServiceImpl implements EgovAuthorGroupService {

    private final EgovAuthorGroupRepository repository;
    private final EgovAuthorInfoRepository authorInfoRepository;

    @Override
    public Page<AuthorGroupDTO> list(AuthorGroupVO authorGroupVO) {
        Pageable pageable = PageRequest.of(authorGroupVO.getFirstIndex(), authorGroupVO.getRecordCountPerPage());
        String searchCondition = authorGroupVO.getSearchCondition();
        String searchKeyword = authorGroupVO.getSearchKeyword();
        return repository.authorGroupList(searchCondition, searchKeyword, pageable);
    }

    @Override
    public List<AuthorInfoVO> authorInfoList() {
        return authorInfoRepository.findAll().stream().map(EgovAuthorGroupUtility::authorInfoEntityToVO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public AuthorGroupVO insert(AuthorGroupVO authorGroupVO) {
        Emplyrscrtyestbs emplyrscrtyestbs = EgovAuthorGroupUtility.authorGroupVOToEntity(authorGroupVO);
        return EgovAuthorGroupUtility.authorGroupEntityToVO(repository.save(emplyrscrtyestbs));
    }

    @Transactional
    @Override
    public AuthorGroupVO update(AuthorGroupVO authorGroupVO) {
        Emplyrscrtyestbs emplyrscrtyestbs = EgovAuthorGroupUtility.authorGroupVOToEntity(authorGroupVO);
        return repository.findById(emplyrscrtyestbs.getScrtyDtrmnTrgetId())
                .map(result -> {
                    result.setMberTyCode(emplyrscrtyestbs.getMberTyCode());
                    result.setAuthorCode(emplyrscrtyestbs.getAuthorCode());
                    return repository.save(result);
                })
                .map(EgovAuthorGroupUtility::authorGroupEntityToVO).orElse(null);
    }

    @Transactional
    @Override
    public void delete(AuthorGroupVO authorGroupVO) {
        String scrtyDtrmnTrgetId = authorGroupVO.getScrtyDtrmnTrgetId();
        repository.deleteById(scrtyDtrmnTrgetId);
    }

}
