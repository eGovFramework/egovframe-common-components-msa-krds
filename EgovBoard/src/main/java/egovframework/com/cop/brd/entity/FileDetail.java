package egovframework.com.cop.brd.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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
