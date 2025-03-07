package egovframework.com.cop.bls.repository;

import egovframework.com.cop.bls.entity.Blog;
import egovframework.com.cop.bls.service.BlogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("blsEgovBlogRepository")
public interface EgovBlogRepository extends JpaRepository<Blog, String> {

    @Query("SELECT new egovframework.com.cop.bls.service.BlogDTO( " +
            "a.blogId, " +
            "a.blogNm, " +
            "a.blogIntrcn, " +
            "a.useAt, " +
            "a.registSeCode, " +
            "a.tmplatId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "b.userNm, " +
            "'' " +
            ") " +
            "FROM blsBlog a " +
            "LEFT OUTER JOIN blsUserMaster b " +
            "ON a.frstRegisterId = b.esnlId " +
            "LEFT OUTER JOIN blsCmmnDetailCode c " +
            "ON a.registSeCode = c.cmmnDetailCodeId.code " +
            "AND c.useAt = 'Y' " +
            "AND c.cmmnDetailCodeId.codeId = 'COM001' " +
            "WHERE ((:searchCondition = '1' AND (:searchKeyword IS NULL OR a.blogNm LIKE %:searchKeyword%))) " +
            "ORDER BY a.frstRegistPnttm DESC "
    )
    Page<BlogDTO> blodList(
            @Param("searchCondition") String searchCondition,
            @Param("searchKeyword") String searchKeyword,
            Pageable pageable);

    @Query("SELECT new egovframework.com.cop.bls.service.BlogDTO( " +
            "a.blogId, " +
            "a.blogNm, " +
            "a.blogIntrcn, " +
            "a.useAt, " +
            "a.registSeCode, " +
            "a.tmplatId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.lastUpdtPnttm, '%Y-%m-%d'), ''), " +
            "a.lastUpdusrId, " +
            "b.userNm, " +
            "c.tmplatNm " +
            ") " +
            "FROM blsBlog a " +
            "LEFT OUTER JOIN blsUserMaster b " +
            "ON a.frstRegisterId = b.esnlId " +
            "LEFT OUTER JOIN blsTmplatInfo c " +
            "ON a.tmplatId = c.tmplatId " +
            "WHERE a.blogId = :blogId "
    )
    BlogDTO blogDetail(String blogId);

}
