package egovframework.com.sec.ram.repository;

import egovframework.com.sec.ram.entity.AuthorRoleRelated;
import egovframework.com.sec.ram.entity.AuthorRoleRelatedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ramEgovAuthorRoleRepository")
public interface EgovAuthorRoleRepository extends JpaRepository<AuthorRoleRelated, AuthorRoleRelatedId> {
}
