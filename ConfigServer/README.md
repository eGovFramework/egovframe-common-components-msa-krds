# ConfigServer

## 프로젝트 소개

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
accessSecret: "7FB814B9D7FFB3D675EF1F525C1D61B254227B3B0A771DDDBDFE4112A1F42F66" # sha256(egovframework)
refreshSecret: "7FB814B9D7FFB3D675EF1F525C1D61B254227B3B0A771DDDBDFE4112A1F42F66" # sha256(egovframework)
accessExpiration: 1200000 # TTL (millisecond, 1 Min)
refreshExpiration: 3600000 # TTL (millisecond, 5 Min)
```

- Token의 SecretKey의 경우 'egovframework'를 암호화하여 사용한 값으로 실사용시에는 수정이 필요
- accessExpiration 종료 후 refreshExpiration동안 refreshToken을 이용해 accessToken 재발급

## 유의사항

구동 시 EurekaServer → ConfigServer → GatewayServer 순으로 실행

## 참조
