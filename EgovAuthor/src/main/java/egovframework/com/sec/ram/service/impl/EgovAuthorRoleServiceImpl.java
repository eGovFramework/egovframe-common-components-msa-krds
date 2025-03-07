package egovframework.com.sec.ram.service.impl;

import egovframework.com.sec.ram.entity.AuthorRoleRelated;
import egovframework.com.sec.ram.repository.EgovAuthorRoleRepository;
import egovframework.com.sec.ram.repository.EgovRoleInfoRepository;
import egovframework.com.sec.ram.service.AuthorRoleRelatedVO;
import egovframework.com.sec.ram.service.EgovAuthorRoleService;
import egovframework.com.sec.ram.service.RoleInfoDTO;
import egovframework.com.sec.ram.util.EgovAuthorInfoUtility;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service("ramEgovAuthorRoleService")
@RequiredArgsConstructor
public class EgovAuthorRoleServiceImpl extends EgovAbstractServiceImpl implements EgovAuthorRoleService {

    private final EgovAuthorRoleRepository repository;
    private final EgovRoleInfoRepository egovRoleInfoRepository;

    @Override
    public Page<RoleInfoDTO> list(AuthorRoleRelatedVO authorRoleRelatedVO) {
        Pageable pageable = PageRequest.of(authorRoleRelatedVO.getFirstIndex(), authorRoleRelatedVO.getRecordCountPerPage());
        String searchKeyword = authorRoleRelatedVO.getSearchKeyword();
        return egovRoleInfoRepository.roleInfoList(searchKeyword, pageable);
    }

    @Transactional
    @Override
    public AuthorRoleRelatedVO insert(AuthorRoleRelatedVO authorRoleRelatedVO) {
        AuthorRoleRelated authorRoleRelated = EgovAuthorInfoUtility.authorRoleRelatedVOToEntity(authorRoleRelatedVO);
        authorRoleRelated.setCreatDt(LocalDateTime.now());
        return EgovAuthorInfoUtility.authorRoleRelatedEntityToVO(repository.save(authorRoleRelated));
    }

    @Transactional
    @Override
    public boolean delete(AuthorRoleRelatedVO authorRoleRelatedVO) {
        AuthorRoleRelated authorRoleRelated = EgovAuthorInfoUtility.authorRoleRelatedVOToEntity(authorRoleRelatedVO);
        repository.delete(authorRoleRelated);
        return !repository.existsById(authorRoleRelated.getAuthorRoleRelatedId());
    }

}
