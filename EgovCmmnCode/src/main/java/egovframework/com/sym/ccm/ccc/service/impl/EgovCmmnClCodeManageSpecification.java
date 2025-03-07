package egovframework.com.sym.ccm.ccc.service.impl;

import egovframework.com.sym.ccm.ccc.entity.CmmnClCode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class EgovCmmnClCodeManageSpecification {

    public static Specification<CmmnClCode> clCodeContains(String searchKeyword) {
        return (Root<CmmnClCode> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(searchKeyword)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("clCode"), "%" + searchKeyword + "%");
        };
    }

    public static Specification<CmmnClCode> clCodeNmContains(String searchKeyword) {
        return (Root<CmmnClCode> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(searchKeyword)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("clCodeNm"), "%" + searchKeyword + "%");
        };
    }

}
