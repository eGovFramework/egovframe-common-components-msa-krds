package egovframework.com.cop.brd.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name = "brdBbsSyncLog")
@Getter
@Setter
@Table(name = "COMTNBBSSYNCLOG")
public class BbsSyncLog {

    @Id
    @Column(name = "SYNC_ID", length = 20)
    private String syncId;

    @Column(name = "NTT_ID", length = 20)
    private Long nttId;

    @Column(name = "BBS_ID", length = 40)
    private String bbsId;

    @Column(name = "SYNC_STTUS_CODE", length = 1)
    private String syncSttusCode;

    @Column(name = "REGIST_PNTTM")
    private LocalDateTime registPnttm;

    @Column(name = "SYNC_PNTTM")
    private LocalDateTime syncPnttm;

    @Column(name = "ERROR_PNTTM")
    private LocalDateTime errorPnttm;

}
