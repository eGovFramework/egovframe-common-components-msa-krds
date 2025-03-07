package egovframework.com.uss.olp.qri.service;

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
public class CmmnDetailCodeVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 3810677034875969068L;

    private String codeId;

    private String code;

    private String codeNm;

    private String codeDc;

    private String useAt;

    private LocalDateTime frstRegistPnttm;

    private String frstRegisterId;

    private LocalDateTime lastUpdtPnttm;

    private String lastUpdusrId;

}
