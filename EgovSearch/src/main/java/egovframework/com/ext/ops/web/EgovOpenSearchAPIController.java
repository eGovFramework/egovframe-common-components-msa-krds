package egovframework.com.ext.ops.web;

import egovframework.com.ext.ops.service.EgovOpenSearchManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ext/ops")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "EgovOpenSearchAPIController", description = "Open Search CONTROLLER(인덱스 및 데이터 생성용)")
public class EgovOpenSearchAPIController {

    private final EgovOpenSearchManageService openSearchManageService;

    @Operation(
            summary = "텍스트 인덱스 생성",
            description = "mysql 테이블과 연동되는 OpenSearch(text) 인덱스를 생성",
            tags = {"EgovOpenSearchAPIController"}
    )
    @GetMapping("/createTextIndex")
    public ResponseEntity<?> createTextIndex() {
        Map<String, Object> response = new HashMap<>();

        try {
            openSearchManageService.createTextIndex();
            response.put("status", "success");
        } catch (IOException e) {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "임베딩 값이 포함된 인덱스 생성",
            description = "mysql 테이블과 연동되는 OpenSearch(vector) 인덱스를 생성",
            tags = {"EgovOpenSearchAPIController"}
    )
    @GetMapping("/createVectorIndex")
    public ResponseEntity<?> createVectorIndex() {
        Map<String, Object> response = new HashMap<>();

        try {
            openSearchManageService.createVectorIndex();
            response.put("status", "success");
        } catch (IOException e) {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "데이터 추가",
            description = "OpenSearch 인덱스(text)에 mySql 테이블의 데이터를 추가(벌크 insert)",
            tags = {"EgovOpenSearchAPIController"}
    )
    @GetMapping("/insertTextData")
    public ResponseEntity<?> insertTextData() {
        Map<String, Object> response = new HashMap<>();

        try {
            openSearchManageService.insertTotalData();
            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "임베딩 값이 포함된 데이터를 전부 추가",
            description = "OpenSearch 인덱스(vector)에 mySql 테이블의 데이터를 추가(분할 insert)",
            tags = {"EgovOpenSearchAPIController"}
    )
    @GetMapping("/insertVectorData")
    public ResponseEntity<?> insertVectorData() {
        Map<String, Object> response = new HashMap<>();

        try {
            openSearchManageService.insertTotalVectorData();
            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "인덱스 삭제",
            description = "OpenSearch 인덱스(vector) 삭제",
            tags = {"EgovOpenSearchAPIController"}
    )
    @GetMapping("/deleteIndex/{indexName}")
    public ResponseEntity<?> deleteIndex(@PathVariable String indexName) {
        Map<String, Object> response = new HashMap<>();

        try {
            openSearchManageService.deleteIndex(indexName);
            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "동기화 처리",
            description = "이력 테이블에 미동기로 남은 데이터들을 OpenSearch 인덱스(text, vector) 에 추가 및 완료 처리",
            tags = {"EgovOpenSearchAPIController"}
    )

    @PostMapping("/reprocess/{syncSttusCode}")
    public ResponseEntity<String> reprocessFailedSync(@PathVariable String syncSttusCode) {
        try {
            openSearchManageService.reprocessFailedSync(syncSttusCode);
            return ResponseEntity.ok("Reprocess completed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to reprocess: " + e.getMessage());
        }
    }
}
