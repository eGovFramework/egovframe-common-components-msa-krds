package egovframework.com.sec.ram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name="ramAuthorRoleRelated")
@Getter
@Setter
@Table(name="MSATNAUTHORROLERELATE")
public class AuthorRoleRelated {

    @EmbeddedId
    private AuthorRoleRelatedId authorRoleRelatedId;

    @Column(name="CREAT_DT")
    private LocalDateTime creatDt;

}
