package egovframework.com.cop.brd.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "brdUserMaster")
@Getter
@Setter
@Table(name = "COMVNUSERMASTER")
@Immutable
public class UserMaster {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ESNTL_ID")
    private String esnlId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USER_NM")
    private String userNm;

    @Column(name = "USER_ZIP")
    private String userZip;

    @Column(name = "AUSER_ADRES")
    private String userAdres;

    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Column(name = "AGROUP_ID")
    private String groupId;

    @Column(name = "USER_SE")
    private String userSe;

    @Column(name = "AORGNZT_ID")
    private String orgNztId;

}
