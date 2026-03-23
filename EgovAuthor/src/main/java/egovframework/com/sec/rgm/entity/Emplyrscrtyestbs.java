package egovframework.com.sec.rgm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name="rgmEmplyrscrtyestbs")
@Getter
@Setter
@Table(name="MSATNEMPLYRSCRTYESTBS")
public class Emplyrscrtyestbs {

    @Id
    @Column(name="SCRTY_DTRMN_TRGET_ID")
    private String scrtyDtrmnTrgetId;

    @Column(name="MBER_TY_CODE")
    private String mberTyCode;

    @Column(name="AUTHOR_CODE")
    private String authorCode;

}
