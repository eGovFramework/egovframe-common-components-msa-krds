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
public class CmmnClCodeVO implements Serializable {

    private static final long serialVersionUID = 6056072928832805875L;

    private String clCode;

    private String clCodeNm;

}
