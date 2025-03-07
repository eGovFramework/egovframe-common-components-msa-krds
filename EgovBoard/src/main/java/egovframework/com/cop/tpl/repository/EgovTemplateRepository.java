package egovframework.com.cop.tpl.repository;

import egovframework.com.cop.tpl.entity.TmplatInfo;
import egovframework.com.cop.tpl.service.TemplateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("tplEgovTemplateRepository")
public interface EgovTemplateRepository extends JpaRepository<TmplatInfo, String> {

    @Query("SELECT new egovframework.com.cop.tpl.service.TemplateDTO( " +
            "a.tmplatId, " +
            "a.tmplatNm, " +
            "a.tmplatSeCode, " +
            "a.tmplatCours, " +
            "a.useAt, " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "b.userNm, " +
            "c.codeNm " +
            ") " +
            "FROM tplTmplatInfo a " +
            "LEFT OUTER JOIN tplUserMaster b " +
            "ON a.frstRegisterId = b.esnlId " +
            "LEFT OUTER JOIN tplCmmnDetailCode c " +
            "ON a.tmplatSeCode = c.cmmnDetailCodeId.code " +
            "AND c.useAt = 'Y' " +
            "AND c.cmmnDetailCodeId.codeId = 'COM005' " +
            "WHERE ( " +
            "(:searchCondition = '1' AND (:searchKeyword IS NULL OR a.tmplatNm LIKE %:searchKeyword%)) OR " +
            "(:searchCondition = '2' AND (:searchKeyword IS NULL OR c.codeNm LIKE %:searchKeyword%)) " +
            ") " +
            "ORDER BY a.frstRegistPnttm DESC "
    )
    Page<TemplateDTO> templateList(
            @Param("searchCondition") String searchCondition,
            @Param("searchKeyword") String searchKeyword,
            Pageable pageable);

    @Query("SELECT new egovframework.com.cop.tpl.service.TemplateDTO( " +
            "a.tmplatId, " +
            "a.tmplatNm, " +
            "a.tmplatSeCode, " +
            "a.tmplatCours, " +
            "a.useAt, " +
            "a.frstRegisterId, " +
            "COALESCE(FUNCTION('DATE_FORMAT', a.frstRegistPnttm, '%Y-%m-%d'), ''), " +
            "b.userNm, " +
            "c.codeNm " +
            ") " +
            "FROM tplTmplatInfo a " +
            "LEFT OUTER JOIN tplUserMaster b " +
            "ON a.frstRegisterId = b.esnlId " +
            "LEFT OUTER JOIN tplCmmnDetailCode c " +
            "ON a.tmplatSeCode = c.cmmnDetailCodeId.code " +
            "AND c.useAt = 'Y' " +
            "AND c.cmmnDetailCodeId.codeId = 'COM005' " +
            "WHERE a.tmplatId = :tmplatId "
    )
    TemplateDTO templateDetail(String tmplatId);

}
