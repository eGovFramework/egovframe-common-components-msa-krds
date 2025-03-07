package egovframework.com.sec.gmt.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="gmtAuthorGroupInfo")
@Getter
@Setter
@Table(name="COMTNAUTHORGROUPINFO")
public class AuthorGroupInfo {

    @Id
    @Column(name="GROUP_ID")
    private String groupId;

    @Column(name="GROUP_NM")
    private String groupNm;

    @Column(name="GROUP_CREAT_DE")
    private String groupCreatDe;

    @Column(name="GROUP_DC")
    private String groupDc;

}
