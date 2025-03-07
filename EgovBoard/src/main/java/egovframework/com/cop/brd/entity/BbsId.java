package egovframework.com.cop.brd.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class BbsId implements Serializable {

    private static final long serialVersionUID = -5614848211267359747L;

    @Column(name = "NTT_ID")
    private Long nttId;

    @Column(name = "BBS_ID")
    private String bbsId;

}
