package egovframework.com.cop.brd.service;

import egovframework.com.cop.bls.service.EgovDefaultVO;
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
public class BbsMasterVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 1111252257667952049L;

    private String bbsId;

    private String bbsNm;

    private String bbsIntrcn;

    private String bbsTyCode;

    private String replyPosblAt;

    private String fileAtchPosblAt;

    private Integer atchPosblFileNumber;

    private Long atchPosblFileSize;

    private String useAt;

    private String tmplatId;

    private String cmmntyId;

    private String frstRegisterId;

    private LocalDateTime frstRegistPnttm;

    private String lastUpdusrId;

    private LocalDateTime lastUpdtPnttm;

    private String blogId;

    private String blogAt;

    private String bbsOption;

    private String blogNm;

    private String cmmntyNm;

}
