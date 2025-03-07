package egovframework.com.cop.bls.service;

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
public class BlogVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 4350732052789623064L;

    private String blogId;

    @EgovNullCheck(message="{comCopBlog.blogMasterVO.regist.blogNm}{common.required.msg}")
    private String blogNm;

    @EgovNullCheck(message="{comCopBlog.blogMasterVO.regist.blogIntrcn}{common.required.msg}")
    private String blogIntrcn;

    private String useAt;

    private String registSeCode;

    private String tmplatId;

    private LocalDateTime frstRegistPnttm;

    private String frstRegisterId;

    private LocalDateTime lastUpdtPnttm;

    private String lastUpdusrId;

    private String bbsId;

    private String blogAt;

}
