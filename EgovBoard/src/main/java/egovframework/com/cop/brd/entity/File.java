package egovframework.com.cop.brd.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name = "brdFile")
@Getter
@Setter
@Table(name = "COMTNFILE")
public class File {

    @Id
    @Column(name = "ATCH_FILE_ID")
    private String atchFileId;

    @Column(name = "CREAT_DT")
    private LocalDateTime creatDt;

    @Column(name = "USE_AT")
    private String useAt;

}
