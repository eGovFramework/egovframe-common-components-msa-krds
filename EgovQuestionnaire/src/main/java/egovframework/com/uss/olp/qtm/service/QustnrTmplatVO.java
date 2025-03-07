package egovframework.com.uss.olp.qtm.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.egovframe.rte.ptl.reactive.validation.EgovNullCheck;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QustnrTmplatVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = -4693194001929115140L;

    private String qustnrTmplatId;

    @EgovNullCheck(message="{comUssOlpQtm.regist.qestnrTmplatTy}{common.required.msg}")
    private String qustnrTmplatTy;

    @EgovNullCheck(message="{comUssOlpQtm.regist.qestnrTmplatCn}{common.required.msg}")
    private String qustnrTmplatDc;

    @EgovNullCheck(message="{comUssOlpQtm.regist.qestnrTmplatCours}{common.required.msg}")
    private String qustnrTmplatPathNm;

    private String frstRegistPnttm;

    private String frstRegisterId;

    private String lastUpdtPnttm;

    private String lastUpdusrId;

    private MultipartFile qustnrTmplatImageInfo;

    private byte[] qustnrTmplatImageByte;

    private String qustnrTmplatImageState;

}
