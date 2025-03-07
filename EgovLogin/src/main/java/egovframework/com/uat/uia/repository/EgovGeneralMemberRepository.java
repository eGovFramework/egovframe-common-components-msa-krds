package egovframework.com.uat.uia.repository;

import egovframework.com.uat.uia.entity.GnrlMber;
import egovframework.com.uat.uia.service.LoginDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("uiaEgovGeneralMemberRepository")
public interface EgovGeneralMemberRepository extends JpaRepository<GnrlMber, String> {

    @Query("SELECT new egovframework.com.uat.uia.service.LoginDTO( " +
            "a.mberId, " +
            "a.mberNm, " +
            "a.password, " +
            "a.ihidNum, " +
            "a.mberEmailAdres, " +
            "'GNR', " +
            "'', " +
            "a.esntlId, " +
            "'', " +
            "b.authorCode " +
            ") " +
            "FROM uiaGnrlMber a " +
            "INNER JOIN uiaEmplyrscrtyestbs b " +
            "ON a.esntlId = b.scrtyDtrmnTrgetId " +
            "WHERE a.mberId = :userId " +
            "AND a.password = :password " +
            "AND a.mberStus = 'P' "
    )
    LoginDTO findByIdAndPassword(String userId, String password);

}
