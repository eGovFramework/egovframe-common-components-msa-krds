package egovframework.com.cop.brd.service;

import egovframework.com.cop.bbs.service.EgovDefaultVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.egovframe.rte.ptl.reactive.validation.EgovNullCheck;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BbsVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 2293017208920992923L;

    private Long nttId;

    private String bbsId;

    private int nttNo;

    @EgovNullCheck(message = "{comCopBbs.articleVO.regist.nttSj}{common.required.msg}")
    private String nttSj;

    @EgovNullCheck(message = "{comCopBbs.articleVO.regist.nttCn}{common.required.msg}")
    private String nttCn;

    private String answerAt;

    private int parntscttNo;

    private int answerLc;

    private int sortOrdr;

    private int rdcnt;

    private String useAt;

    private String ntceBgnde;

    private String ntceEndde;

    private String ntcrId;

    private String ntcrNm;

    private String password;

    private String atchFileId;

    private String noticeAt;

    private String sjBoldAt;

    private String secretAt;

    private String frstRegistPnttm;

    private String frstRegisterId;

    private String lastUpdtPnttm;

    private String lastUpdusrId;

    private String blogId;

    private String cmmntyId;

    private String bbsNm;

    private String blogNm;

    private String cmmntyNm;

    private String anonymousAt;

}
