package egovframework.com.cop.brd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
