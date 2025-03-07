package egovframework.com.cop.bls.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BbsMasterOptnVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = -2567761482537358298L;

    private String bbsId;

    private String answerAt;

    private String stsfdgAt;

    private LocalDateTime frstRegistPnttm;

    private String frstRegisterId;

    private LocalDateTime lastUpdtPnttm;

    private String lastUpdusrId;

}
