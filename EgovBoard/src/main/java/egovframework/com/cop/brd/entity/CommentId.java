package egovframework.com.cop.brd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CommentId implements Serializable {

    private static final long serialVersionUID = 2810720632002356259L;

    @Column(name = "NTT_ID")
    private Long nttId;

    @Column(name = "BBS_ID")
    private String bbsId;

    @Column(name = "ANSWER_NO")
    private Long answerNo;

}
