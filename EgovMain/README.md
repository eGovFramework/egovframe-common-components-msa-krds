# EgovMain

## 서비스 상태 (현재 모듈 기준)

| 항목 | 내용 |
|------|------|
| **역할** | 포털 **메인 레이아웃**(KRDS 기반 헤더·사이드·푸터) 및 메뉴에서 각 마이크로서비스 화면을 iframe 등으로 연결 |
| **애플리케이션명** | `EgovMain` (Eureka 등록 ID: 보통 `EGOVMAIN`) |
| **포트** | `0` — **랜덤**. 외부에서는 `http://localhost:9000/main/...` (Gateway)로 접근 |
| **Config Server** | 사용 안 함 (`config.import` 없음) |
| **Eureka** | 등록 |
| **선행 조건** | EurekaServer. 화면 연동은 Gateway·타 서비스 기동 후 |

## 프로젝트 소개

MSA 구성에서 사용자가 마주하는 첫 화면 셸을 제공한다. 실제 업무 기능은 `EgovLogin`, `EgovBoard` 등 개별 서비스가 담당한다.

## 유의사항

- 본 모듈 `pom.xml` 기준 **Java 17**.
- 직접 서비스 포트로 접속하지 말고 **Gateway 9000** 기준 URL을 사용한다.

## 참조

- 루트 `README.md` — 전체 구동·화면 설명
