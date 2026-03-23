package egovframework.com.sec.gmt.service.impl;

import egovframework.com.sec.gmt.entity.AuthorGroupInfo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

@UtilityClass
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
