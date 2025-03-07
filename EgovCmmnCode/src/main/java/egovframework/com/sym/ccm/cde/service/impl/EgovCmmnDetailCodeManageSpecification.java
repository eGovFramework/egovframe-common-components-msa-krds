package egovframework.com.sym.ccm.cde.service.impl;

import egovframework.com.sym.ccm.cde.entity.CmmnDetailCode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class EgovCmmnDetailCodeManageSpecification {

    public static Specification<CmmnDetailCode> codeIdContains(String searchKeyword) {
        return (Root<CmmnDetailCode> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(searchKeyword)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.and(
                    criteriaBuilder.like(root.get("cmmnDetailCodeId").get("codeId"), "%" + searchKeyword + "%"),
                    criteriaBuilder.equal(root.get("cmmnCode").get("useAt"), "Y")
            );
        };
    }

    public static Specification<CmmnDetailCode> codeContains(String searchKeyword) {
        return (Root<CmmnDetailCode> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(searchKeyword)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.and(
                    criteriaBuilder.like(root.get("cmmnDetailCodeId").get("code"), "%" + searchKeyword + "%"),
                    criteriaBuilder.equal(root.get("cmmnCode").get("useAt"), "Y")
            );
        };
    }

    public static Specification<CmmnDetailCode> codeNmContains(String searchKeyword) {
        return (Root<CmmnDetailCode> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(searchKeyword)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.and(
                    criteriaBuilder.like(root.get("codeNm"), "%" + searchKeyword + "%"),
                    criteriaBuilder.equal(root.get("cmmnCode").get("useAt"), "Y")
            );
        };
    }

    public static Specification<CmmnDetailCode> useAtContains() {
        return (Root<CmmnDetailCode> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("cmmnCode").get("useAt"), "Y");
        };
    }

}
