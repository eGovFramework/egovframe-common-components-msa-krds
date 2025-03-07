package egovframework.com.cop.cmy.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.egovframe.rte.ptl.reactive.validation.EgovNullCheck;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommunityVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 4543004462719365814L;

    private String cmmntyId;

    @EgovNullCheck(message="{comCopCmy.commuMasterVO.regist.cmmntyNm}{common.required.msg}")
    private String cmmntyNm;

    @EgovNullCheck(message="{comCopCmy.commuMasterVO.regist.cmmntyIntrcn}{common.required.msg}")
    private String cmmntyIntrcn;

    private String useAt;

    private String registSeCode;

    private String tmplatId;

    private LocalDateTime frstRegistPnttm;

    private String frstRegisterId;

    private LocalDateTime lastUpdtPnttm;

    private String lastUpdusrId;

}
