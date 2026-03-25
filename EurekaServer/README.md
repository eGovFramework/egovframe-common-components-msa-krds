# EurekaServer

## 서비스 상태 (현재 모듈 기준)

| 항목 | 내용 |
|------|------|
| **역할** | Netflix Eureka **서비스 레지스트리**. 게이트웨이와 마이크로서비스가 여기에 등록·조회 |
| **애플리케이션명** | `EurekaServer` |
| **포트** | `8761` (고정). 대시보드: `http://localhost:8761` |
| **Eureka 클라이언트** | 자기 자신은 레지스트리에 등록하지 않음 (`register-with-eureka: false`) |
| **선행 조건** | 없음 — MSA 기동 시 **가장 먼저** 기동하는 것을 권장 |

## 프로젝트 소개

Spring Cloud Netflix Eureka Server 인스턴스. 비즈니스 서비스는 `server.port: 0`(랜덤 포트)으로 뜨는 경우가 많아, **실제 호출 URL은 항상 Gateway(9000)** 또는 Eureka에 표시되는 호스트/포트를 참고한다.

## 유의사항

- 본 모듈 `pom.xml` 기준 **Java 17**.
- 이후 **ConfigServer(8888)**, **GatewayServer(9000)** 순으로 기동한다.

## 참조

- 루트 `README.md` — 전체 구동 순서
