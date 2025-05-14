package egovframework.com.uss.olp.qim.repository;

import egovframework.com.uss.olp.qim.entity.QustnrIem;
import egovframework.com.uss.olp.qim.entity.QustnrIemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("qimEgovQusntrItemRepository")
public interface EgovQusntrItemRepository extends JpaRepository<QustnrIem, QustnrIemId> {
}
