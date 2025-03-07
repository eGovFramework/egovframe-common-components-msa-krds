package egovframework.com.sec.rgm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="rgmAuthorInfo")
@Getter
@Setter
@Table(name="COMTNAUTHORINFO")
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
