package egovframework.com.cop.brd.util;

import egovframework.com.cop.brd.service.FileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.egovframe.boot.crypto.service.impl.EgovEnvCryptoServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class EgovFileUtility {

    @Value("${file.board.upload-dir:#{systemProperties['user.home'] + '/upload/board'}}")
    private String UPLOAD_DIR;

    @Value("${file.board.allowed-extensions:jpg,jpeg,gif,bmp,png,pdf}")
    private String ALLOWED_EXTENSIONS;

    @Value("${file.max-file-size:1048576}")
    private Long MAX_FILE_SIZE;

    private final EgovEnvCryptoServiceImpl egovEnvCryptoService;

    public List<FileVO> parseFile(List<MultipartFile> files) throws FdlException, IOException {
        List<FileVO> result = new ArrayList<>();

        File uploadDirectory = new File(UPLOAD_DIR);
        if (!uploadDirectory.exists() && !uploadDirectory.mkdirs()) {
            log.error("##### upload directory create Fail");
        } else {
            log.debug("##### upload directory create Success");
        }

        log.debug("##### EgovEnvCryptoServiceImpl files size >>> {}", files.size());

        int fileSn = 0;
        for (MultipartFile file : files) {

            log.debug("##### EgovEnvCryptoServiceImpl files size >>> {}", files.size());

            if (file.isEmpty()) continue;

            if (file.getSize() > MAX_FILE_SIZE) {
                log.error("##### The file size cannot exceed 1 MB");
                continue;
            }

            log.debug("##### EgovFileServiceImpl insertFiles file.getSize() >>> {}", file.getSize());

            String originalFileName = validFileName(Objects.requireNonNull(file.getOriginalFilename()));
            Path targetPath = Paths.get(UPLOAD_DIR).resolve(originalFileName).normalize();
            if (!targetPath.startsWith(Paths.get(UPLOAD_DIR))) {
                log.error("##### Invalid file path");
                continue;
            }

            if (!validFileType(file) || !validFileExtension(originalFileName)) {
                log.error("##### File type not allowed");
                continue;
            }

            String newFileName = newFileName(originalFileName);
            File destinationFile = new File(UPLOAD_DIR, newFileName);
            file.transferTo(destinationFile);

            FileVO fileVO = new FileVO();
            fileVO.setUseAt("Y");
            fileVO.setFileSn(String.valueOf(fileSn));
            fileVO.setFileStreCours(UPLOAD_DIR);
            fileVO.setStreFileNm(newFileName);
            fileVO.setOrignlFileNm(originalFileName);
            fileVO.setFileExtsn(originalFileName.substring(originalFileName.lastIndexOf(".") + 1));
            fileVO.setFileSize(file.getSize());
            result.add(fileVO);
        }

        return result;
    }

    private String validFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    private boolean validFileType(MultipartFile file) throws IOException {
        Tika tika = new Tika();
        String contentType = tika.detect(file.getInputStream());
        return contentType != null && (contentType.startsWith("image/") || contentType.equals("application/pdf"));
    }

    private boolean validFileExtension(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return Arrays.asList(ALLOWED_EXTENSIONS.split(",")).contains(extension);
    }

    private String newFileName(String fileName) {
        String uniqueIdentifier = UUID.randomUUID().toString();
        return "BBS_" + egovEnvCryptoService.encrypt(fileName + uniqueIdentifier);
    }

}
