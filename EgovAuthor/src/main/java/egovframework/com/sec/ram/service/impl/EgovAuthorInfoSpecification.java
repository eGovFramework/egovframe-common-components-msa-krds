package egovframework.com.sec.ram.service.impl;

import egovframework.com.sec.ram.entity.AuthorInfo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

@UtilityClass
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
