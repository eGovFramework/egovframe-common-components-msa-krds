package egovframework.com.uat.uia.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name="uiaEmplyrscrtyestbs")
@Getter
@Setter
@Table(name="COMTNEMPLYRSCRTYESTBS")
public class Emplyrscrtyestbs {

    @Id
    @Column(name="SCRTY_DTRMN_TRGET_ID")
    private String scrtyDtrmnTrgetId;

    @Column(name="MBER_TY_CODE")
    private String mberTyCode;

    @Column(name="AUTHOR_CODE")
    private String authorCode;

}
