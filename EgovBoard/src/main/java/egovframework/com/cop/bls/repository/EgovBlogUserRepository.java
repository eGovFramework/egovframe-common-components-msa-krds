package egovframework.com.cop.bls.repository;

import egovframework.com.cop.bls.entity.BlogUser;
import egovframework.com.cop.bls.entity.BlogUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("blsEgovBlogUserRepository")
public interface EgovBlogUserRepository extends JpaRepository<BlogUser, BlogUserId> {
}
