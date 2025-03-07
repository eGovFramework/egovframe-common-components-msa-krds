package egovframework.com.cop.brd.repository;

import egovframework.com.cop.brd.entity.FileDetail;
import egovframework.com.cop.brd.entity.FileDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("brdEgovFileDetailRepository")
public interface EgovFileDetailRepository extends JpaRepository<FileDetail, FileDetailId> {

    List<FileDetail> findAllByFileDetailId_AtchFileId(String atchFileId);

}
