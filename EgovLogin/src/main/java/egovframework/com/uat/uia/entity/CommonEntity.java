package egovframework.com.uat.uia.entity;

import java.time.LocalDateTime;

public interface CommonEntity {

    void setLockAt(String lockAt);

    void setLockCnt(Integer lockCnt);

    void setLockLastPnttm(LocalDateTime lockLastPnttm);

}
