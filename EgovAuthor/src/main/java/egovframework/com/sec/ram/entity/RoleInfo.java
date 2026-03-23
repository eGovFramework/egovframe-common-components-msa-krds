package egovframework.com.sec.ram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name="ramRoleInfo")
@Getter
@Setter
@Table(name="MSATNROLEINFO")
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
