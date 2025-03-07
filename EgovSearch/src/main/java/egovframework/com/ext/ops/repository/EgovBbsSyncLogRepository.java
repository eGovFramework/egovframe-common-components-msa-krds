package egovframework.com.ext.ops.repository;

import egovframework.com.ext.ops.entity.BbsSyncLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("opsEgovBbsSyncLogRepository")
public interface EgovBbsSyncLogRepository extends JpaRepository<BbsSyncLog, String> {

    /**
     * NTT_ID로 가장 최근의 동기화 로그를 조회한다.
     *
     * @param nttId 게시물 ID
     * @return 동기화 로그
     */
    Optional<BbsSyncLog> findTopByNttIdOrderByRegistPnttmDesc(Long nttId);

    /**
     * 동기화 상태 코드로 동기화 로그 목록을 조회한다.
     *
     * @param syncSttusCode 동기화 상태 코드
     * @return 동기화 로그 목록
     */
    List<BbsSyncLog> findBySyncSttusCode(String syncSttusCode);
    
    /**
     * NTT_ID와 동기화 상태 코드로 동기화 로그 목록을 조회한다.
     *
     * @param nttId 게시물 ID
     * @param syncSttusCode 동기화 상태 코드
     * @return 동기화 로그 목록
     */
    List<BbsSyncLog> findByNttIdAndSyncSttusCode(Long nttId, String syncSttusCode);

}
