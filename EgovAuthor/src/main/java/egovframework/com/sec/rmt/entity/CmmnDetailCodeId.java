package egovframework.com.sec.rmt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CmmnDetailCodeId implements Serializable {

    private static final long serialVersionUID = 2953635947066704753L;

    @Column(name="CODE_ID")
    private String codeId;

    @Column(name="CODE")
    private String code;

}
