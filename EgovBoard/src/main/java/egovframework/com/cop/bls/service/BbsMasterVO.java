package egovframework.com.cop.bls.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.egovframe.rte.ptl.reactive.validation.EgovNullCheck;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BbsMasterVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 1111252257667952049L;

    private String bbsId;

    @EgovNullCheck(message="{comCopBbs.boardMasterVO.detail.bbsNm}{common.required.msg}")
    private String bbsNm;

    @EgovNullCheck(message="{comCopBbs.boardMasterVO.detail.bbsIntrcn}{common.required.msg}")
    private String bbsIntrcn;

    @EgovNullCheck(message="{comCopBbs.boardMasterVO.detail.bbsTyCode}{common.required.msg}")
    private String bbsTyCode;

    @EgovNullCheck(message="{comCopBbs.boardMasterVO.detail.replyPosblAt}{common.required.msg}")
    private String replyPosblAt;

    @EgovNullCheck(message="{comCopBbs.boardMasterVO.detail.fileAtchPosblAt}{common.required.msg}")
    private String fileAtchPosblAt;

    @NotNull(message="{comCopBbs.boardMasterVO.detail.atchPosblFileNumber}{common.required.msg}")
    private Integer atchPosblFileNumber;

    private Long atchPosblFileSize;

    @EgovNullCheck(message="{comCopBbs.boardMasterVO.detail.useAt}{common.required.msg}")
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

}
