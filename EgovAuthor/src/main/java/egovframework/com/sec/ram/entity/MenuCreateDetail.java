package egovframework.com.sec.ram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name="ramMenuCreateDetail")
@Getter
@Setter
@Table(name="MSATNMENUCREATDTLS")
public class MenuCreateDetail {

    @EmbeddedId
    private MenuCreateDetailId menuCreateDetailId;

    @Column(name="MAPNG_CREAT_ID")
    private String mapngCreatId;

}
