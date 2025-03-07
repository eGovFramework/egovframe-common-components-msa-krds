package egovframework.com.cop.bls.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class BlogUserId implements Serializable {

    private static final long serialVersionUID = 737628966302235714L;

    @Column(name="BLOG_ID")
    private String blogId;

    @Column(name="EMPLYR_ID")
    private String emplyrId;

}
