package egovframework.com.sec.rgm.service.impl;

import egovframework.com.sec.rgm.entity.AuthorGroupInfo;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
