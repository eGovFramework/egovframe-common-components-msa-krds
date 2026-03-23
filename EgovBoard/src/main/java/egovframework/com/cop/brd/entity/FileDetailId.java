package egovframework.com.cop.brd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class FileDetailId implements Serializable {

    private static final long serialVersionUID = 347030034865510041L;

    @Column(name = "ATCH_FILE_ID")
    private String atchFileId;

    @Column(name = "FILE_SN")
    private String fileSn;

}
