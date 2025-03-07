package egovframework.com.sec.rmt.service.impl;

import egovframework.com.sec.rmt.entity.RoleInfo;
import egovframework.com.sec.rmt.repository.EgovRoleInfoRepository;
import egovframework.com.sec.rmt.service.EgovRoleInfoService;
import egovframework.com.sec.rmt.service.RoleInfoDTO;
import egovframework.com.sec.rmt.service.RoleInfoVO;
import egovframework.com.sec.rmt.util.EgovRoleInfoUtility;
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
import java.time.format.DateTimeFormatter;

@Service("rmtEgovRoleInfoService")
public class EgovRoleInfoServiceImpl extends EgovAbstractServiceImpl implements EgovRoleInfoService {

    private final EgovRoleInfoRepository repository;
    private final EgovIdGnrService idgenService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EgovRoleInfoServiceImpl(
            EgovRoleInfoRepository repository,
            @Qualifier("egovRoleIdGnrService") EgovIdGnrService idgenService
    ) {
        this.repository = repository;
        this.idgenService = idgenService;
    }

    @Override
    public Page<RoleInfoDTO> list(RoleInfoVO roleInfoVO) {
        Pageable pageable = PageRequest.of(roleInfoVO.getFirstIndex(), roleInfoVO.getRecordCountPerPage());
        String searchCondition = roleInfoVO.getSearchCondition();
        String searchKeyword = roleInfoVO.getSearchKeyword();
        return repository.roleInfoList(searchCondition, searchKeyword, pageable);
    }

    @Override
    public RoleInfoVO detail(RoleInfoVO roleInfoVO) {
        String roleCode = roleInfoVO.getRoleCode();
        return repository.findById(roleCode).map(EgovRoleInfoUtility::roleEntityToVO).orElse(null);
    }

    @Transactional
    @Override
    public RoleInfoVO insert(RoleInfoVO roleInfoVO) throws FdlException {
        String roleTy = roleInfoVO.getRoleTy();
        if("method".equals(roleTy)) {
            roleInfoVO.setRoleCode("mtd-".concat(idgenService.getNextStringId()));
            roleInfoVO.setRoleCreatDe(LocalDateTime.now().format(formatter));
        } else if("pointcut".equals(roleTy)) {
            roleInfoVO.setRoleCode("pct-".concat(idgenService.getNextStringId()));
            roleInfoVO.setRoleCreatDe(LocalDateTime.now().format(formatter));
        } else {
            roleInfoVO.setRoleCode("web-".concat(idgenService.getNextStringId()));
            roleInfoVO.setRoleCreatDe(LocalDateTime.now().format(formatter));
        }

        RoleInfo roleInfo = EgovRoleInfoUtility.roleVOToEntity(roleInfoVO);
        return EgovRoleInfoUtility.roleEntityToVO(repository.save(roleInfo));
    }

    @Transactional
    @Override
    public RoleInfoVO update(RoleInfoVO roleInfoVO) {
        RoleInfo roleInfo = EgovRoleInfoUtility.roleVOToEntity(roleInfoVO);
        return repository.findById(roleInfo.getRoleCode())
                .map(result -> {
                    result.setRoleNm(roleInfo.getRoleNm());
                    result.setRolePttrn(roleInfo.getRolePttrn());
                    result.setRoleDc(roleInfo.getRoleDc());
                    result.setRoleTy(roleInfo.getRoleTy());
                    result.setRoleSort(roleInfo.getRoleSort());
                    result.setRoleCreatDe(LocalDateTime.now().format(formatter));
                    return repository.save(result);
                })
                .map(EgovRoleInfoUtility::roleEntityToVO).orElse(null);
    }

    @Transactional
    @Override
    public void delete(RoleInfoVO roleInfoVO) {
        String roleCode = roleInfoVO.getRoleCode();
        repository.deleteById(roleCode);
    }

}
