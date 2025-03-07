package egovframework.com.uss.olp.qmc.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name="qmcQustnrQesitm")
@Getter
@Setter
@Table(name="COMTNQUSTNRQESITM")
public class QustnrQesitm {

    /** 설문문항아이디 */
    @EmbeddedId
    private QustnrQesitmId qustnrQesitmId;

    /** 질문순번 */
    @Column(name="QESTN_SN")
    private String qestnSn;

    /** 질문유형코드 */
    @Column(name="QESTN_TY_CODE")
    private String qestnTyCode;

    /** 질문내용 */
    @Column(name="QESTN_CN")
    private String qestnCn;

    /** 최대선택개수 */
    @Column(name="MXMM_CHOISE_CO")
    private String mxmmChoiseCo;

    /** 최초등록시점 */
    @Column(name="FRST_REGIST_PNTTM")
    private LocalDateTime frstRegistPnttm;

    /** 최초등록자아이디 */
    @Column(name="FRST_REGISTER_ID")
    private String frstRegisterId;

    /** 최종수정시점 */
    @Column(name="LAST_UPDT_PNTTM")
    private LocalDateTime lastUpdtPnttm;

    /** 최종수정시점아이디  */
    @Column(name="LAST_UPDUSR_ID")
    private String lastUpdusrId;

}
