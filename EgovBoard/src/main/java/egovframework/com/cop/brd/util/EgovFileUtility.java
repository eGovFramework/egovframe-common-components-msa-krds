package egovframework.com.cop.brd.util;

import egovframework.com.cop.brd.service.FileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.egovframe.boot.crypto.service.impl.EgovEnvCryptoServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class EgovFileUtility {

    @Value("${file.board.upload-dir:#{systemProperties['user.home'] + '/upload/board'}}")
    private String uploadDir;

    @Value("${file.board.allowed-extensions:jpg,jpeg,gif,bmp,png,pdf}")
    private String allowedExtensions;

    @Value("${file.max-file-size:1048576}")
    private Long maxFileSize;

    private final EgovEnvCryptoServiceImpl egovEnvCryptoService;

    public List<FileVO> parseFile(List<MultipartFile> files) throws IOException {
        List<FileVO> result = new ArrayList<>();

        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists() && !uploadDirectory.mkdirs()) {
            log.error("##### Upload directory creation failed");
        } else {
            log.debug("##### Upload directory created successfully");
        }

        log.debug("##### EgovEnvCryptoServiceImpl files size >>> {}", files.size());

        int fileSn = 0;
        for (MultipartFile file : files) {
            log.debug("##### Processing file: {}", file.getOriginalFilename());

            boolean isValid = true;
            String originalFileName = file.getOriginalFilename();
            Path targetPath = Paths.get(uploadDir).resolve(originalFileName).normalize();

            if (file.isEmpty()) {
                log.warn("##### Skipping empty file");
                isValid = false;
            } else if (file.getSize() > maxFileSize) {
                log.error("##### The file size cannot exceed 1 MB");
                isValid = false;
            } else if (!targetPath.startsWith(Paths.get(uploadDir))) {
                log.error("##### Invalid file path");
                isValid = false;
            } else if (!validFileType(file) || !validFileExtension(originalFileName)) {
                log.error("##### File type not allowed");
                isValid = false;
            }

            // If the file is not valid, move to the next iteration
            if (!isValid) {
                continue;
            }

            // Proceed with file processing
            String newFileName = newFileName(originalFileName);
            File destinationFile = new File(uploadDir, newFileName);
            file.transferTo(destinationFile);

            FileVO fileVO = new FileVO();
            fileVO.setUseAt("Y");
            fileVO.setFileSn(String.valueOf(fileSn++));
            fileVO.setFileStreCours(uploadDir);
            fileVO.setStreFileNm(newFileName);
            fileVO.setOrignlFileNm(originalFileName);
            fileVO.setFileExtsn(originalFileName.substring(originalFileName.lastIndexOf(".") + 1));
            fileVO.setFileSize(file.getSize());
            result.add(fileVO);
        }

        return result;
    }

    private boolean validFileType(MultipartFile file) throws IOException {
        Tika tika = new Tika();
        String contentType = tika.detect(file.getInputStream());
        return contentType != null && (contentType.startsWith("image/") || contentType.equals("application/pdf"));
    }

    private boolean validFileExtension(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return Arrays.asList(allowedExtensions.split(",")).contains(extension);
    }

    private String newFileName(String fileName) {
        String uniqueIdentifier = UUID.randomUUID().toString();
        return "BBS_" + egovEnvCryptoService.encrypt(fileName + uniqueIdentifier);
    }

}
