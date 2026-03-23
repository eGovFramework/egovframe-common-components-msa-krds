package egovframework.com.sec.rgm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name="rgmAuthorGroupInfo")
@Getter
@Setter
@Table(name="MSATNAUTHORGROUPINFO")
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
