package egovframework.com.cop.bls.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BlogDTO {

    private String blogId;
    private String blogNm;
    private String blogIntrcn;
    private String useAt;
    private String registSeCode;
    private String tmplatId;
    private String frstRegistPnttm;
    private String frstRegisterId;
    private String lastUpdtPnttm;
    private String lastUpdusrId;
    private String userNm;
    private String tmplatNm;

}
