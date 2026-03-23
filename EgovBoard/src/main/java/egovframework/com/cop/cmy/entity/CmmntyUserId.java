package egovframework.com.cop.cmy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CmmntyUserId implements Serializable  {

    private static final long serialVersionUID = -7509780987964897434L;

    @Column(name="CMMNTY_ID")
    private String cmmntyId;

    @Column(name="EMPLYR_ID")
    private String emplyrId;

}
