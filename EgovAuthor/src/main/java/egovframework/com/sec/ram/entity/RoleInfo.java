package egovframework.com.sec.ram.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="ramRoleInfo")
@Getter
@Setter
@Table(name="COMTNROLEINFO")
public class RoleInfo {

    @Id
    @Column(name="ROLE_CODE")
    private String roleCode;

    @Column(name="ROLE_NM")
    private String roleNm;

    @Column(name="ROLE_PTTRN")
    private String rolePttrn;

    @Column(name="ROLE_DC")
    private String roleDc;

    @Column(name="ROLE_TY")
    private String roleTy;

    @Column(name="ROLE_SORT")
    private String roleSort;

    @Column(name="ROLE_CREAT_DE")
    private String roleCreatDe;

}
