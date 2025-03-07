package egovframework.com.sym.ccm.cde.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CmmnCodeVO implements Serializable {

    private static final long serialVersionUID = -8951146618999186077L;

    private String codeId;

    private String codeIdNm;

}
