# ConfigServer

## 서비스 상태 (현재 모듈 기준)

| 항목 | 내용 |
|------|------|
| **역할** | Spring Cloud Config **native** 저장소. JAR 내 `classpath:/config` 의 YAML을 각 비즈니스 서비스에 제공 |
| **애플리케이션명** | `ConfigServer` |
| **포트** | `8888` (고정) |
| **Eureka** | 미등록 (디스커버리 대상 아님) |
| **프로필** | `application.yml` 기준 `spring.profiles.active=native`, 검색 위치 `classpath:/config` |
| **연동** | Gateway·대부분 마이크로서비스가 `optional:configserver:http://localhost:8888` 로 설정 로드 |

## 프로젝트 소개

Spring Cloud Config Server로, 로컬·개발 시 Git 없이 **내장 설정 파일**(`src/main/resources/config/`)만으로 DB·JPA·JWT 등 공통 설정을 한곳에서 관리한다. 운영 환경에서는 저장소 방식·보안을 별도 검토한다.

## 프로젝트 구성

``` text
  ConfigServer
    ├ /src/main
    │   ├ java
    │   └ resources
    │       ├ config
    │       │   └ application-local.yml
    │       └ application.yml
    └ pom.xml
```

## 설정파일 안내

`/src/main/resources/config/application-local.yml`

### 1. DB 설정

``` yaml
datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/com
    username: com
    password: com01
    hikari:
        maximum-pool-size: 20 # 각 서비스의 최대 연결 수 제한
        connection-timeout: 20000 # 연결을 얻기 위한 최대 대기 시간 (밀리초)
        idle-timeout: 30000 # 유휴 상태에서 연결을 유지할 시간 (밀리초)
        minimum-idle: 5 # 유휴 상태로 유지할 연결 수
        max-lifetime: 1800000 # 연결의 최대 수명 (밀리초)
```

- 데이터베이스 설정 (기본 - mysql / localhost )
- ※ 기존 공통컴포넌트와 동일한 테이블 및 데이터 사용

### 2. 인증 Token 설정

``` yaml
token:
  accessSecret: "..."   # 예시값. 운영 시 반드시 교체
  refreshSecret: "..."
  accessExpiration: 7200000    # TTL(ms) — 현재 샘플 기준 약 2시간
  refreshExpiration: 86400000  # TTL(ms) — 현재 샘플 기준 약 1일
```

- Token Secret은 샘플용이므로 **실서비스에서는 반드시 변경**한다.
- Access 만료 후 Refresh 유효 기간 내에 Access 토큰을 재발급받는 구조는 `EgovLogin` 과 연동된다.

## 유의사항

- 기동 순서: **EurekaServer → ConfigServer → GatewayServer** 이후 비즈니스 서비스.
- 본 모듈 `pom.xml` 기준 **Java 17**.

## 참조
