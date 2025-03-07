package egovframework.com.ext.ops.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name="opsBbsSyncLog")
@Getter
@Setter
@Table(name="COMTNBBSSYNCLOG")
public class BbsSyncLog {

    @Id
    @Column(name="SYNC_ID", length=20)
    private String syncId;

    @Column(name="NTT_ID", length=20)
    private Long nttId;

    @Column(name="BBS_ID", length=40)
    private String bbsId;

    @Column(name="SYNC_STTUS_CODE", length=1)
    private String syncSttusCode;

    @Column(name="REGIST_PNTTM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registPnttm;

    @Column(name="SYNC_PNTTM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date syncPnttm;

    @Column(name="ERROR_PNTTM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date errorPnttm;

}
