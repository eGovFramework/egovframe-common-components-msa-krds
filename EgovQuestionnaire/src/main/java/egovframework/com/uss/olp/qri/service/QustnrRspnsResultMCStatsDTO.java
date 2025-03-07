package egovframework.com.uss.olp.qri.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QustnrRspnsResultMCStatsDTO {

    private String qustnrTmplatId;
    private String qestnrId;
    private String qustnrQesitmId;
    private String qustnrIemId;
    private String iemCn;
    private Long qustnrIemIdCount;
    private Long qustnrIemIdPercent;


}
