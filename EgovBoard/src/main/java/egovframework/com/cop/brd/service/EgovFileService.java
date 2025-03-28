package egovframework.com.cop.brd.service;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;

import java.util.List;

public interface EgovFileService {

    List<FileVO> selectFileInfs(String atchFileId) throws FdlException;

    String insertFileInf(FileVO fvo);

    String insertFiles(List<FileVO> fileList) throws FdlException;

    void deleteFileInfs(FileVO fileVO);

    FileVO detailFileInf(FileVO fileVO);

}
