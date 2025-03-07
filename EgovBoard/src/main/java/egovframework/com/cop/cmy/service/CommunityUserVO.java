package egovframework.com.cop.cmy.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommunityUserVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 5335871043524025868L;

    private String cmmntyId;

    private String emplyrId;

    private String mngrAt;

    private String mberSttus;

    private LocalDateTime sbscrbDe;

    private String secsnDe;

    private String useAt;

    private LocalDateTime frstRegistPnttm;

    private String frstRegisterId;

    private LocalDateTime lastUpdtPnttm;

    private String lastUpdusrId;

}
