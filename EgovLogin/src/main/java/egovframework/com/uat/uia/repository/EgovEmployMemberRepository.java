package egovframework.com.uat.uia.repository;

import egovframework.com.uat.uia.entity.EmplyrInfo;
import egovframework.com.uat.uia.service.LoginDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("uiaEgovEmployMemberRepository")
public interface EgovEmployMemberRepository extends JpaRepository<EmplyrInfo, String> {

    @Query("SELECT new egovframework.com.uat.uia.service.LoginDTO( " +
            "a.emplyrId, " +
            "a.userNm, " +
            "a.password, " +
            "a.ihidNum, " +
            "a.emailAdres, " +
            "'USR', " +
            "a.orgnztId, " +
            "a.esntlId, " +
            "'', " +
            "b.authorCode " +
            ") " +
            "FROM uiaEmplyrInfo a " +
            "INNER JOIN uiaEmplyrscrtyestbs b " +
            "ON a.esntlId = b.scrtyDtrmnTrgetId " +
            "WHERE a.emplyrId = :userId " +
            "AND a.password = :password " +
            "AND a.emplyrStusCode = 'P' "
    )
    LoginDTO findByIdAndPassword(String userId, String password);

}
