package egovframework.com.sym.ccm.cca.service.impl;

import egovframework.com.sym.ccm.cca.entity.CmmnCode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class EgovCmmnCodeManageSpecification {

    public static Specification<CmmnCode> codeIdContains(String searchKeyword) {
        return (Root<CmmnCode> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(searchKeyword)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("codeId"), "%" + searchKeyword + "%");
        };
    }

    public static Specification<CmmnCode> codeIdNmContains(String searchKeyword) {
        return (Root<CmmnCode> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(searchKeyword)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("codeIdNm"), "%" + searchKeyword + "%");
        };
    }

}
