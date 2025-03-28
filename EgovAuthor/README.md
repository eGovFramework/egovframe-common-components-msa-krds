# 보안 - 권한관리

## 프로젝트 소개

시스템 구축시 스프링의 보안 메커니즘을 적용하기 위해 Spring Security 에서 관리하는 권한(Authority)을 정의하는 컴포넌트

## 프로젝트 구성

``` text
  EgovAuthor
    ├ /src/main
    │   ├ java
    │   │   └ egovframework.com
    │   │       ├ config
    │   │       ├ filter
    │   │       ├ pagination
    │   │       ├ sec
    │   │       │  ├ gmt
    │   │       │  ├ ram
    │   │       │  ├ rgm
    │   │       │  └ rmt
    │   │       └ EgovAuthorApplication
    │   │
    │   └ resources
    │       ├ messages.egovframework.com
    │       ├ static
    │       ├ templates.egovframework.com.sec
    │       │   ├ gmt
    │       │   ├ ram
    │       │   ├ rgm
    │       │   └ rmt
    │       └ application.yml
    └ pom.xml
```

- 그룹 관리(`/com/sec/gmt`)
- 권한 관리(`/com/sec/ram`) : 특정 role이 부여된 권한을 관리
- 권한그룹 관리(`/com/sec/rgm`) : 유저에게 '권한관리'에서 등록한 권한을 부여할 수 있음
- 롤 관리(`/com/sec/rmt`) : 패턴에 따른 접근 제한 롤 등록 및 관리

## 화면 구성

### 1. 권한 관리

![ram_list](https://github.com/user-attachments/assets/eb5d9dfe-6671-4cc9-8d4e-713cb13c6b1f)   
- 사용자 (User)에 부여할 권한을 등록할 수 있다.
- 권한정보를 등록 후 '롤 정보'를 클릭하여 해당 권한에 필요한 롤을 등록 할 수 있음.
<br/>

- 롤 정보
  ![rmt_list](https://github.com/user-attachments/assets/c898e3dc-5445-4ff8-9a85-71845fdde9a0)   
  '롤관리'에서 등록한 모든 롤의 목록 조회

- 롤 등록
  - select 선택을 이용해 등록/미등록 선택 (초기값은 '미등록')
  ![rmt_select](https://github.com/user-attachments/assets/b8c9f6fb-b7c9-4b65-aaad-6ed11986a6f5)    
  - 저장이 필요한 checkbox에 체크 (select문을 이용해 등록 상태를 변경하였으나 checkbox에 체크하지 않으면 저장이 되지 않는다)
  ![rmt_check](https://github.com/user-attachments/assets/b4eecdf4-2bdb-44c1-8d8a-e9610f9479df)   
    
    - 예를들어 1,3번은 미등록 → 등록으로 변경하고 체크박스는 2,3번만 체크한 경우
        ![rmt_example_1](https://github.com/user-attachments/assets/0ccdfb99-8142-4c52-9b36-46ff318d899a)   
      - 3번만 등록상태로 변경 및 등록일 추가   
        ![rmt_example_2](https://github.com/user-attachments/assets/17fa5f10-9b54-47a0-b7c4-23add5247736)   
- 권한그룹관리   
    ![rgm_list](https://github.com/user-attachments/assets/cbc03049-d44f-466d-ba5f-f6f823965deb)
    - 권한관리에서 등록한 권한을 유저에게 등록
    - 권한관리 때와 동일하게 수정 후 체크박스 체크 후 → 등록을 눌러야 저장이 됨   
    

## 유의사항

'NCC-Project의 메뉴 접근 권한은 ConfigServer에서 관리하고 있으므로 이 컴포넌트를 이용해 유저의 권한을 변경하더라도 프로젝트의 접근 권한은 변경되지 않습니다.

## 참조
