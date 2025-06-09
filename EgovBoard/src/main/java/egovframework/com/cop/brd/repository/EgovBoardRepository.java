package egovframework.com.cop.brd.repository;

import egovframework.com.cop.brd.entity.Bbs;
import egovframework.com.cop.brd.entity.BbsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("brdEgovBoardRepository")
public interface EgovBoardRepository extends JpaRepository<Bbs, BbsId> {

    List<Bbs> findAllByBbsIdAndSortOrdr(BbsId bbsId, Long sortOrdr);

    List<Bbs> findAllByParntscttNo(int parntscttNo);

}
