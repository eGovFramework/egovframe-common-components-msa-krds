package egovframework.com.cop.cmy.repository;

import egovframework.com.cop.cmy.entity.CmmntyUser;
import egovframework.com.cop.cmy.entity.CmmntyUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("cmyEgovCommunityUserRepository")
public interface EgovCommunityUserRepository extends JpaRepository<CmmntyUser, CmmntyUserId> {
}
