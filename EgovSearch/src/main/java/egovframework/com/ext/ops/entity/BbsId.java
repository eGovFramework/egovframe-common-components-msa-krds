package egovframework.com.ext.ops.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class BbsId implements Serializable {

    private static final long serialVersionUID = 5031418862454086844L;

    @Column(name="NTT_ID")
    private Long nttId;

    @Column(name="BBS_ID")
    private String bbsId;

}
