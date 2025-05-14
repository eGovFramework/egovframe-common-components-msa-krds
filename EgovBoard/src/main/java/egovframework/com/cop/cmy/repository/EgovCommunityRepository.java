package egovframework.com.cop.cmy.repository;

import egovframework.com.cop.cmy.entity.Cmmnty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("cmyEgovCommunityRepository")
public interface EgovCommunityRepository extends JpaRepository<Cmmnty, String> {

}
