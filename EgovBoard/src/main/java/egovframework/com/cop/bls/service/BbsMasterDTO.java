package egovframework.com.cop.bls.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BbsMasterDTO {

    private String bbsId;
    private String bbsNm;
    private String bbsIntrcn;
    private String bbsTyCode;
    private String replyPosblAt;
    private String fileAtchPosblAt;
    private int atchPosblFileNumber;
    private Long atchPosblFileSize;
    private String useAt;
    private String tmplatId;
    private String cmmntyId;
    private String frstRegisterId;
    private String frstRegistPnttm;
    private String lastUpdusrId;
    private String lastUpdtPnttm;
    private String blogId;
    private String blogAt;
    private String answerAt;
    private String stsfdgAt;
    private String tmplatNm;
    private String userNm;
    private String codeNm;

}
