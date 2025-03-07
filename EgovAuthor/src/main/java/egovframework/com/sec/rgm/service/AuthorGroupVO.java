package egovframework.com.sec.rgm.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorGroupVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 8047175900950037191L;

    private String scrtyDtrmnTrgetId;

    private String mberTyCode;

    private String authorCode;

}
