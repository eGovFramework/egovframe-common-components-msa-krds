package egovframework.com.cop.brd.repository;

import egovframework.com.cop.brd.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("bbsEgovFileRepository")
public interface EgovFileRepository extends JpaRepository<File, String> {
}
