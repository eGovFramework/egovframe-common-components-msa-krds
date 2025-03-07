package egovframework.com.sec.rgm.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="rgmUserMaster")
@Getter
@Setter
@Table(name="COMVNUSERMASTER")
@Immutable
public class UserMaster {

    @Id
    @Column(name="ESNTL_ID")
    private String esntlId;

    @Column(name="USER_ID")
    private String userId;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="USER_NM")
    private String userNm;

    @Column(name="USER_ZIP")
    private String userZip;

    @Column(name="USER_ADRES")
    private String userAdres;

    @Column(name="USER_EMAIL")
    private String userEmail;

    @Column(name="GROUP_ID")
    private String groupId;

    @Column(name="USER_SE")
    private String userSe;

    @Column(name="ORGNZT_ID")
    private String orgNztId;

}
