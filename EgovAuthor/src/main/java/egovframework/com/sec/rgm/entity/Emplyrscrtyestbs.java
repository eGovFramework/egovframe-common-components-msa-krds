package egovframework.com.sec.rgm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="rgmEmplyrscrtyestbs")
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
