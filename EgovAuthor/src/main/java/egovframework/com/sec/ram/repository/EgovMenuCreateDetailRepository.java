package egovframework.com.sec.ram.repository;

import egovframework.com.sec.ram.entity.MenuCreateDetail;
import egovframework.com.sec.ram.entity.MenuCreateDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("ramEgovMenuCreateDetailRepository")
public interface EgovMenuCreateDetailRepository extends JpaRepository<MenuCreateDetail, MenuCreateDetailId> {

    @Query("SELECT COUNT(a) " +
            "FROM ramMenuCreateDetail a " +
            "WHERE a.menuCreateDetailId.authorCode = :authorCode "
    )
    long countByAuthorCode(@Param("authorCode") String authorCode);

}
