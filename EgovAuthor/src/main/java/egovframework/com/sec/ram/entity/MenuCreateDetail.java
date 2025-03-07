package egovframework.com.sec.ram.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="ramMenuCreateDetail")
@Getter
@Setter
@Table(name="COMTNMENUCREATDTLS")
public class MenuCreateDetail {

    @EmbeddedId
    private MenuCreateDetailId menuCreateDetailId;

    @Column(name="MAPNG_CREAT_ID")
    private String mapngCreatId;

}
