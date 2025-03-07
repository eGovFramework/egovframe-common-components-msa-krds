package egovframework.com.uat.uap.service.impl;

import egovframework.com.uat.uap.entity.LoginPolicy;
import egovframework.com.uat.uap.repository.EgovLoginPolicyRepository;
import egovframework.com.uat.uap.service.EgovLoginPolicyService;
import egovframework.com.uat.uap.service.LoginPolicyDTO;
import egovframework.com.uat.uap.service.LoginPolicyVO;
import egovframework.com.uat.uap.util.EgovLoginPolicyUtility;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service("uapEgovLoginPolicyServiceImpl")
@RequiredArgsConstructor
public class EgovLoginPolicyServiceImpl extends EgovAbstractServiceImpl implements EgovLoginPolicyService {

    private final EgovLoginPolicyRepository repository;

    @Override
    public Page<LoginPolicyDTO> list(LoginPolicyVO loginPolicyVO) {
        Pageable pageable = PageRequest.of(loginPolicyVO.getFirstIndex(), loginPolicyVO.getRecordCountPerPage());
        String searchCondition = loginPolicyVO.getSearchCondition();
        String searchKeyword = loginPolicyVO.getSearchKeyword();
        return this.repository.loginPolicyList(searchCondition, searchKeyword, pageable);
    }

    @Override
    public LoginPolicyVO detail(LoginPolicyVO loginPolicyVO) {
        String employerId = loginPolicyVO.getEmployerId();
        return this.repository.findById(employerId).map(EgovLoginPolicyUtility::entityToVO)
                .orElse(new LoginPolicyVO(
                        employerId,
                        "",
                        "",
                        "",
                        "",
                        LocalDateTime.now(),
                        "",
                        LocalDateTime.now(),
                        "",
                        "")
                );
    }

    @Transactional
    @Override
    public LoginPolicyVO insert(LoginPolicyVO loginPolicyVO, Map<String, String> userInfo) {
        LoginPolicy loginPolicy = EgovLoginPolicyUtility.VOToEntity(loginPolicyVO);
        loginPolicy.setFrstRegisterId(userInfo.get("uniqId"));
        loginPolicy.setFrstRegisterPnttm(LocalDateTime.now());
        loginPolicy.setLastUpdusrId(userInfo.get("uniqId"));
        loginPolicy.setLastUpdtPnttm(LocalDateTime.now());
        return EgovLoginPolicyUtility.entityToVO(this.repository.save(loginPolicy));
    }

    @Transactional
    @Override
    public LoginPolicyVO update(LoginPolicyVO loginPolicyVO, Map<String, String> userInfo) {
        LoginPolicy loginPolicy = EgovLoginPolicyUtility.VOToEntity(loginPolicyVO);
        return this.repository.findById(loginPolicyVO.getEmployerId())
                .map(result -> {
                    result.setIpInfo(loginPolicy.getIpInfo());
                    result.setLmttAt(loginPolicy.getLmttAt());
                    result.setLastUpdusrId(userInfo.get("uniqId"));
                    result.setLastUpdtPnttm(LocalDateTime.now());
                    return this.repository.save(result);
                })
                .map(EgovLoginPolicyUtility::entityToVO).orElse(loginPolicyVO);
    }

    @Transactional
    @Override
    public void delete(LoginPolicyVO loginPolicyVO) {
        String employerId = loginPolicyVO.getEmployerId();
        this.repository.deleteById(employerId);
    }

}
