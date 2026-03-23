package egovframework.com.sec.rmt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name="rmtCmmnDetailCode")
@Getter
@Setter
@Table(name="COMTCCMMNDETAILCODE")
public class CmmnDetailCode {

    @EmbeddedId
    private CmmnDetailCodeId cmmnDetailCodeId;

    @Column(name="CODE_NM")
    private String codeNm;

    @Column(name="CODE_DC")
    private String codeDc;

    @Column(name="USE_AT", length=1)
    private String useAt;

}
