package egovframework.com.sec.ram.service.impl;

import egovframework.com.sec.ram.entity.AuthorInfo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class EgovAuthorInfoSpecification {

    public static Specification<AuthorInfo> authorNmContains(String searchKeyword) {
        return (Root<AuthorInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(searchKeyword)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("authorNm"), "%" + searchKeyword + "%");
        };
    }

}
