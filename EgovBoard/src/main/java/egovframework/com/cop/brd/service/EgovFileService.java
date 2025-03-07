package egovframework.com.cop.brd.service;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;

import java.util.List;

public interface EgovFileService {

    List<FileVO> selectFileInfs(String atchFileId) throws FdlException;

    public String insertFileInf(FileVO fvo) throws Exception;

    String insertFiles(List<FileVO> fileList) throws Exception;

    void deleteFileInfs(FileVO fileVO) throws FdlException;
    FileVO detailFileInf(FileVO fileVO) throws Exception;

}
