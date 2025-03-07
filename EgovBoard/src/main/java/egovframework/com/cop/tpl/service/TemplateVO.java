package egovframework.com.cop.tpl.service;

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
public class TemplateVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 4905819045851161994L;

    private String tmplatId;

    @EgovNullCheck(message="{comCopTpl.template.name}{common.required.msg}")
    private String tmplatNm;

    @EgovNullCheck(message="{comCopTpl.template.path}{common.required.msg}")
    private String tmplatCours;

    private String useAt;

    @EgovNullCheck(message="{comCopTpl.template.type}{common.required.msg}")
    private String tmplatSeCode;

    private String frstRegisterId;

    private LocalDateTime frstRegistPnttm;

    private String lastUpdusrId;

    private LocalDateTime lastUpdtPnttm;

}
