package egovframework.com.sec.rmt.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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
