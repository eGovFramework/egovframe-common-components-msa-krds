package egovframework.com.cop.brd.web;

import lombok.extern.slf4j.Slf4j;
import org.egovframe.boot.crypto.service.impl.EgovEnvCryptoServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class EgovBoardCKEditorController {

    @Value("${file.ck.upload-dir:#{systemProperties['user.home'] + '/upload/ckeditor'}}")
    private String uploadDir;

    @Value("${file.ck.allowed-extensions:jpg,jpeg,gif,bmp,png}")
    private String allowedExtensions;

    @Value("${file.max-file-size:1048576}")
    private Long maxFileSize;

    private Set<String> allowedExtensionSet;
    private final EgovEnvCryptoServiceImpl egovEnvCryptoService;

    public EgovBoardCKEditorController(@Qualifier("egovEnvCryptoService") EgovEnvCryptoServiceImpl egovEnvCryptoService) {
        this.egovEnvCryptoService = egovEnvCryptoService;
    }

    @PostConstruct // 객체 생성 후 실행
    public void init() {
        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists() && !uploadDirectory.mkdirs()) {
            throw new RuntimeException("Failed to create upload directory: " + uploadDir);
        }

        // 확장자 목록을 설정 값에서 가져와 Set으로 변환
        allowedExtensionSet = Arrays.stream(allowedExtensions.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    @PostMapping("/cop/brd/ckImageUpload")
    public Map<String, Object> ckImageUpload(MultipartRequest request) throws IOException {
        Map<String, Object> response = new HashMap<>();

        MultipartFile uploadFile = request.getFile("upload");

        // 파일 Null 검사
        if (ObjectUtils.isEmpty(uploadFile)) {
            response.put("uploaded", 0);
            response.put("error", "No files uploaded.");
            return response;
        }

        // 파일 크기 검사 (1MB 초과 시 업로드 불가)
        if (uploadFile.getSize() > maxFileSize) {
            response.put("uploaded", 0);
            response.put("error", "File size exceeds the maximum limit of 1MB.");
            return response;
        }

        String originalFilename = uploadFile.getOriginalFilename();
        String newFileName = getFileName(uploadFile);

        // 파일 확장자 검사
        if (!isValidImageFile(newFileName)) {
            response.put("uploaded", 0);
            response.put("error", "Invalid file type.");
            return response;
        }

        File destinationFile = new File(uploadDir, newFileName);
        uploadFile.transferTo(destinationFile);

        String fileUrl = "/cop/brd/ckeditor/" + newFileName;
        response.put("url", fileUrl);

        return response;
    }

    @GetMapping("/cop/brd/ckeditor/{filename}")
    public void ckeditorImage(@PathVariable String filename, HttpServletResponse response) {
        log.debug("##### EgovBoardCKEditorController filename : " + filename);

        String uploadDir = System.getProperty("user.home") + "/upload/ckeditor/";
        Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
        File file = new File(filePath.toFile().getAbsolutePath());

        // 파일을 읽어서 응답 스트림으로 전송
        try (FileInputStream fis = new FileInputStream(file);
             ServletOutputStream outStream = response.getOutputStream()) {
            byte[] buffer = new byte[4096]; // 4KB 버퍼
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            outStream.flush(); // 스트림 비우기
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileName(MultipartFile uploadFile) {
        String originalFileName = uploadFile.getOriginalFilename();
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueIdentifier = UUID.randomUUID().toString();
        String fileName = egovEnvCryptoService.encrypt(originalFileName + uniqueIdentifier);
        return fileName + ext;
    }

    private boolean isValidImageFile(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return allowedExtensionSet.contains(extension);
    }

}
