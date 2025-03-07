package egovframework.com.cop.brd.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long nttId;
    private String bbsId;
    private Integer nttNo;
    private String nttSj;
    private String nttCn;
    private String frstRegisterId;
    private String userNm;
    private String frstRegistPnttm;
    private Integer rdcnt;
    private Integer parntscttNo;
    private String answerAt;
    private Integer answerLc;
    private Integer sortOrdr;
    private String useAt;
    private String atchFileId;
    private String ntceBgnde;
    private String ntceEndde;
    private String ntcrId;
    private String ntcrNm;
    private String sjBoldAt;
    private String noticeAt;
    private String secretAt;
    private Integer commentCnt;
    private String bbsNm;

}
