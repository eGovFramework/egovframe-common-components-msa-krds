package egovframework.com.cop.brd.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileVO extends EgovDefaultVO implements Serializable {

    private static final long serialVersionUID = 4589734384669701049L;

    private String atchFileId;

    private String creatDt;

    private String useAt;

    private String fileSn;

    private String fileStreCours;

    private String streFileNm;

    private String orignlFileNm;

    private String fileExtsn;

    private String fileCn;

    private Long fileSize;

    public String[] deleteFileSn;

}
