package egovframework.com.cop.cmy.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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
