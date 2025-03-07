package egovframework.com.uss.olp.qtm.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QustnrTmplatDTO {

    private String qustnrTmplatId;
    private String qustnrTmplatTy;
    private String qustnrTmplatDc;
    private String qustnrTmplatPathNm;
    private String frstRegistPnttm;
    private String frstRegisterId;
    private String lastUpdtPnttm;
    private String lastUpdusrId;
    private byte[] qustnrTmplatImageByte;
    private String userNm;

}
