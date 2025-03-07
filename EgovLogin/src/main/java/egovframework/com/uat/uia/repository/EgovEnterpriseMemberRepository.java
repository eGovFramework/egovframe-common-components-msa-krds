package egovframework.com.uat.uia.repository;

import egovframework.com.uat.uia.entity.EntrprsMber;
import egovframework.com.uat.uia.service.LoginDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("uiaEgovEnterpriseMemberRepository")
public interface EgovEnterpriseMemberRepository extends JpaRepository<EntrprsMber, String> {

    @Query("SELECT new egovframework.com.uat.uia.service.LoginDTO( " +
            "a.entrprsMberId, " +
            "a.cmpnyNm, " +
            "a.entrprsMberPassword, " +
            "a.bizrno, " +
            "a.applcntEmailAdres, " +
            "'ENT', " +
            "'', " +
            "a.esntlId, " +
            "'', " +
            "b.authorCode " +
            ") " +
            "FROM uiaEntrprsMber a " +
            "INNER JOIN uiaEmplyrscrtyestbs b " +
            "ON a.esntlId = b.scrtyDtrmnTrgetId " +
            "WHERE a.entrprsMberId = :userId " +
            "AND a.entrprsMberPassword = :password " +
            "AND a.entrprsMberStus = 'P' "
    )
    LoginDTO findByIdAndPassword(String userId, String password);

}
