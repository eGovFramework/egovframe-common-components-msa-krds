package egovframework.com.sym.ccm.cca.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CmmnClCodeVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 1532959585797289738L;

    private String clCode;

    private String clCodeNm;

    private String clCodeDc;

    private String useAt;

    private LocalDateTime frstRegistPnttm;

    private String frstRegisterId;

    private LocalDateTime lastUpdtPnttm;

    private String lastUpdusrId;

}
