package egovframework.com.cop.brd.service.impl;

import egovframework.com.cop.brd.entity.File;
import egovframework.com.cop.brd.entity.FileDetail;
import egovframework.com.cop.brd.entity.FileDetailId;
import egovframework.com.cop.brd.repository.EgovFileDetailRepository;
import egovframework.com.cop.brd.repository.EgovFileRepository;
import egovframework.com.cop.brd.service.EgovFileService;
import egovframework.com.cop.brd.service.FileVO;
import egovframework.com.cop.brd.util.EgovBoardUtility;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("brdEgovFileServiceImpl")
@Slf4j
public class EgovFileServiceImpl extends EgovAbstractServiceImpl implements EgovFileService {

    private final EgovFileRepository fileRepository;
    private final EgovFileDetailRepository fileDetailRepository;
    private final EgovIdGnrService idGnrService;

    public EgovFileServiceImpl(
            EgovFileRepository fileRepository,
            EgovFileDetailRepository fileDetailRepository,
            @Qualifier("egovFileIdGnrService") EgovIdGnrService idGnrService
    ) {
        this.fileRepository = fileRepository;
        this.fileDetailRepository = fileDetailRepository;
        this.idGnrService = idGnrService;
    }


    @Override
    public List<FileVO> selectFileInfs(String atchFileId) throws FdlException {
        return fileDetailRepository.findAllByFileDetailId_AtchFileId(atchFileId).stream().map(EgovBoardUtility::fileDeatailEntityToVO).collect(Collectors.toList());
    }

    @Override
    public String insertFileInf(FileVO fvo) throws Exception {
        int fileCount = fileDetailRepository.findAllByFileDetailId_AtchFileId(fvo.getAtchFileId()).size();

        if (fileCount > 0) {
            fvo.setFileSn(String.valueOf(fileCount));
        }

        File file = new File();
        FileDetail filedetail = new FileDetail();
        BeanUtils.copyProperties(fvo, file);
        BeanUtils.copyProperties(fvo, filedetail);

        file.setAtchFileId(fvo.getAtchFileId());
        file.setUseAt("Y");
        file.setCreatDt(LocalDateTime.now());

        FileDetailId filedetailId = new FileDetailId();
        filedetailId.setAtchFileId(fvo.getAtchFileId());
        if (fvo.getFileSn().isEmpty()) {
            filedetailId.setFileSn("0");
        } else {
            filedetailId.setFileSn(fvo.getFileSn());
        }
        filedetail.setFileDetailId(filedetailId);

        file.setCreatDt(LocalDateTime.now());

        fileRepository.save(file);
        fileDetailRepository.save(filedetail);

        return fvo.getAtchFileId();
    }

    @Transactional
    @Override
    public String insertFiles(List<FileVO> fileList) throws Exception {
        String attachFileId = idGnrService.getNextStringId();
        for (int i = 0; i < fileList.size(); i++) {
            if (fileList.get(i).getAtchFileId() != null) {
                // 추가등록
                attachFileId = fileList.get(i).getAtchFileId();
                fileList.get(i).setAtchFileId(attachFileId);
                fileList.get(i).setFileSn(String.valueOf(i));
            } else {
                // 신규등록
                fileList.get(i).setAtchFileId(attachFileId);
                fileList.get(i).setFileSn(String.valueOf(i));
            }
            attachFileId = insertFileInf(fileList.get(i));
        }
        return attachFileId;
    }

    @Override
    public void deleteFileInfs(FileVO fileVO) throws FdlException {

        for (String num : fileVO.getDeleteFileSn()) {
            FileDetailId filedetailId = new FileDetailId();
            filedetailId.setAtchFileId(fileVO.getAtchFileId());
            filedetailId.setFileSn(num);
            fileDetailRepository.deleteById(filedetailId);
        }

        List<FileVO> flist = selectFileInfs(fileVO.getAtchFileId());
        if (!flist.isEmpty()) {
            for (int i = 0; i < flist.size(); i++) {
                FileVO fvo = flist.get(i);
                FileDetailId fileDetailId = new FileDetailId();
                fileDetailId.setAtchFileId(fvo.getAtchFileId());
                fileDetailId.setFileSn(fvo.getFileSn());
                Optional<FileDetail> optionalFileDetail = fileDetailRepository.findById(fileDetailId);

                if (optionalFileDetail.isPresent()) {
                    FileDetail fileDetail = optionalFileDetail.get();

                    // 기존 엔티티 삭제
                    fileDetailRepository.delete(fileDetail);

                    String fileSn = String.valueOf(i);

                    // 새로운 엔티티 생성
                    FileDetail newFileDetail = new FileDetail();
                    BeanUtils.copyProperties(fvo, newFileDetail);

                    FileDetailId newFileDetailId = new FileDetailId();
                    newFileDetailId.setAtchFileId(fvo.getAtchFileId());
                    newFileDetailId.setFileSn(fileSn);

                    newFileDetail.setFileDetailId(newFileDetailId);

                    // 저장
                    fileDetailRepository.save(newFileDetail);
                }
            }
        }
    }

    @Override
    public FileVO detailFileInf(FileVO fileVO) throws Exception {
        FileDetailId fileDetailId = new FileDetailId();
        fileDetailId.setAtchFileId(fileVO.getAtchFileId());
        fileDetailId.setFileSn(fileVO.getFileSn());
        fileVO = EgovBoardUtility.fileDeatailEntityToVO(fileDetailRepository.findById(fileDetailId).get());
        return fileVO;
    }
}
