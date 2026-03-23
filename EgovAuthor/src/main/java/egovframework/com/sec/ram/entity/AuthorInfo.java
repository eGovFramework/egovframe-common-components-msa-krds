package egovframework.com.sec.ram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name="ramAuthorInfo")
@Getter
@Setter
@Table(name="MSATNAUTHORINFO")
public class AuthorInfo {

    @Id
    @Column(name="AUTHOR_CODE")
    private String authorCode;

    @Column(name="AUTHOR_NM")
    private String authorNm;

    @Column(name="AUTHOR_DC")
    private String authorDc;

    @Column(name="AUTHOR_CREAT_DE")
    private String authorCreatDe;

}
