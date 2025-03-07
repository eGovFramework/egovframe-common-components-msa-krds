package egovframework.com.uss.olp.qri.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="qriQustnrTmplat")
@Getter
@Setter
@Table(name="COMTNQUSTNRTMPLAT")
@Immutable
public class QustnrTmplat {

    /** 설문템플릿아이디 */
    @Id
    @Column(name="QUSTNR_TMPLAT_ID")
    private String qustnrTmplatId;

    /** 설문템플릿유형 */
    @Column(name="QUSTNR_TMPLAT_TY")
    private String qustnrTmplatTy;

    /** 설문템플릿설명 */
    @Column(name="QUSTNR_TMPLAT_DC")
    private String qustnrTmplatDc;

    /** 설문템플릿경로명 */
    @Column(name="QUSTNR_TMPLAT_PATH_NM")
    private String qustnrTmplatPathNm;

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

    /** 설문템플릿이미지정보 */
    @Lob
    @Column(name="QUSTNR_TMPLAT_IMAGE_INFO", columnDefinition="BLOB")
    private byte[] qustnrTmplatImageInfo;

}
