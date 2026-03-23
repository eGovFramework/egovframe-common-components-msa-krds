package egovframework.com.cop.brd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "brdFileDetail")
@Getter
@Setter
@Table(name = "COMTNFILEDETAIL")
public class FileDetail {

    @EmbeddedId
    private FileDetailId fileDetailId;

    @Column(name = "FILE_STRE_COURS")
    private String fileStreCours;

    @Column(name = "STRE_FILE_NM")
    private String streFileNm;

    @Column(name = "ORIGNL_FILE_NM")
    private String orignlFileNm;

    @Column(name = "FILE_EXTSN")
    private String fileExtsn;

    @Column(name = "FILE_CN")
    private String fileCn;

    @Column(name = "FILE_SIZE")
    private Long fileSize;

}
