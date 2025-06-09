package egovframework.com.cop.bls.repository;

import egovframework.com.cop.bls.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("blsEgovBlogRepository")
public interface EgovBlogRepository extends JpaRepository<Blog, String> {

}
