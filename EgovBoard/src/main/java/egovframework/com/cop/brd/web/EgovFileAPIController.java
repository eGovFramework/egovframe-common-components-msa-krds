package egovframework.com.cop.brd.web;

import egovframework.com.cop.brd.service.EgovFileService;
import egovframework.com.cop.brd.service.FileVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.egovframe.boot.crypto.service.impl.EgovEnvCryptoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;

@Controller("brdEgovFileAPIController")
@RequiredArgsConstructor
public class EgovFileAPIController {

    public final EgovFileService fileMngService;
    private final EgovEnvCryptoServiceImpl egovEnvCryptoService;

    @ResponseBody
    @PostMapping("/cop/brd/selectFileInfs")
    public ResponseEntity<List<FileVO>> selectFileInfs(@RequestBody String atchFileId, HttpServletRequest request) throws Exception {
        String decodeId = egovEnvCryptoService.decrypt(atchFileId);
        String decodeFileId = StringUtils.substringBefore(decodeId,"|");

        List<FileVO> response = fileMngService.selectFileInfs(decodeFileId);
        response.forEach(fileVO -> fileVO.setAtchFileId(egovEnvCryptoService.encrypt(decodeId +"|"+ fileVO.getFileSn())));

        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @PostMapping("/cop/brd/deleteFileInfs")
    public void deleteFileInfs(FileVO fileVO) throws Exception {
        String decodeId = egovEnvCryptoService.decrypt(fileVO.getAtchFileId());
        String decodeFileId = StringUtils.substringBefore(decodeId,"|");
        String decodeFileSn = StringUtils.substringAfterLast(decodeId, "|");

        fileVO.setAtchFileId(decodeFileId);
        fileVO.setFileSn(decodeFileSn);

        fileMngService.deleteFileInfs(fileVO);
    }

    @ResponseBody
    @GetMapping("/cop/brd/fileDownload")
    public void fileDownload(FileVO fileVO,HttpServletRequest request, HttpServletResponse response) throws Exception {
        String decodeId = egovEnvCryptoService.decrypt(fileVO.getAtchFileId());
        String decodeFileId = StringUtils.substringBefore(decodeId,"|");
        String decodeFileSn = StringUtils.substringAfterLast(decodeId, "|");
        fileVO.setAtchFileId(decodeFileId);
        fileVO.setFileSn(decodeFileSn);

        fileVO = fileMngService.detailFileInf(fileVO);
        File file = new File(fileVO.getFileStreCours(), fileVO.getStreFileNm());

        if(!file.exists()){
            return;
        }

        try (InputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {
            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null) {
                contentType = "application/octet-stream"; // 기본값
            }

            // 파일명 인코딩 처리
            String originalFileName = fileVO.getOrignlFileNm();
            String encodedFileName = URLEncoder.encode(originalFileName, "UTF-8").replaceAll("\\+", "%20");
            String userAgent = request.getHeader("User-Agent");

            String contentDisposition;

            if (userAgent != null && userAgent.contains("MSIE")) {
                contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";
            } else if (userAgent != null && userAgent.contains("Trident")) {
                contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";
            } else if (userAgent != null && userAgent.contains("Edge")) {
                contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";
            } else {
                contentDisposition = "attachment; filename*=UTF-8''" + encodedFileName;
            }

            response.setContentType(contentType);
            response.setHeader("Content-Disposition", contentDisposition);
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            response.setContentLengthLong(file.length());

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
