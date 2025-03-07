package egovframework.com.sec.gmt.service.impl;

import egovframework.com.sec.gmt.entity.AuthorGroupInfo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class EgovAuthorGroupInfoSpecification {

    public static Specification<AuthorGroupInfo> groupNmContains(String searchKeyword) {
        return (Root<AuthorGroupInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(searchKeyword)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("groupNm"), "%" + searchKeyword + "%");
        };
    }

}
