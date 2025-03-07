package egovframework.com.uss.olp.qmc.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QustnrTmplatVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 5809492330322184766L;

    private String qustnrTmplatId;

    private String qustnrTmplatTy;

    private String qustnrTmplatDc;

    private String qustnrTmplatPathNm;

    private String frstRegistPnttm;

    private String frstRegisterId;

    private String lastUpdtPnttm;

    private String lastUpdusrId;

    private MultipartFile qustnrTmplatImageInfo;

    private byte[] qustnrTmplatImageByte;

}
