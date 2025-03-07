package egovframework.com.sec.ram.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class MenuCreateDetailId implements Serializable {

    private static final long serialVersionUID = 8720879250970487553L;

    @Column(name="MENU_NO")
    private String menuNo;

    @Column(name="AUTHOR_CODE")
    private String authorCode;

}
