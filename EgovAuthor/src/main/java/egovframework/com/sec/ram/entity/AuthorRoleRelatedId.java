package egovframework.com.sec.ram.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class AuthorRoleRelatedId implements Serializable {

    private static final long serialVersionUID = -3512558062697944834L;

    @Column(name="AUTHOR_CODE")
    private String authorCode;

    @Column(name="ROLE_CODE")
    private String roleCode;

}
