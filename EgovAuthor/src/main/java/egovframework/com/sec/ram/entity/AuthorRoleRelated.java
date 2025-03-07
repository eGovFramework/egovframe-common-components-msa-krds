package egovframework.com.sec.ram.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name="ramAuthorRoleRelated")
@Getter
@Setter
@Table(name="COMTNAUTHORROLERELATE")
public class AuthorRoleRelated {

    @EmbeddedId
    private AuthorRoleRelatedId authorRoleRelatedId;

    @Column(name="CREAT_DT")
    private LocalDateTime creatDt;

}
