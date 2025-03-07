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
import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        response.forEach(fileVO -> {
            fileVO.setAtchFileId(egovEnvCryptoService.encrypt(decodeId +"|"+ fileVO.getFileSn()));
        });

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
    public void fileDownload(FileVO fileVO, HttpServletResponse response) throws Exception {


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

            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileVO.getOrignlFileNm() + "\"");
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

    private Map<String, String> extracted(HttpServletRequest request) {
        Map<String, String> userInfo = new HashMap<>();

        String encryptUserId = request.getHeader("X-USER-ID");
        String encryptUserNm = request.getHeader("X-USER-NM");
        String encryptUniqId = request.getHeader("X-UNIQ-ID");

        userInfo.put("userId", egovEnvCryptoService.decrypt(encryptUserId));
        userInfo.put("userName", egovEnvCryptoService.decrypt(encryptUserNm));
        userInfo.put("uniqId", egovEnvCryptoService.decrypt(encryptUniqId));

        return userInfo;
    }

}
