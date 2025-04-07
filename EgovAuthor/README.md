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

![ram_list](https://github.com/user-attachments/assets/b3c87344-4f60-4755-8ace-45213d1ad46a)   
- 사용자 (User)에 부여할 권한을 등록할 수 있다.
- 권한정보를 등록 후 '롤 정보'를 클릭하여 해당 권한에 필요한 롤을 등록 할 수 있음.
<br/>

- 롤 정보
  ![rmt_list](https://github.com/user-attachments/assets/f2c01c9d-06b8-44f2-8467-10a95b024ec6)   
  '롤관리'에서 등록한 모든 롤의 목록 조회

- 롤 등록
  - select 선택을 이용해 등록/미등록 선택 (초기값은 '미등록')
  ![rmt_select](https://github.com/user-attachments/assets/b546de34-4e3d-46ea-aab2-ffad39f2b9b3)     
  - 저장이 필요한 checkbox에 체크 (select문을 이용해 등록 상태를 변경하였으나 checkbox에 체크하지 않으면 저장이 되지 않는다)
  ![rmt_check](https://github.com/user-attachments/assets/3c32ab8e-8265-4e66-af1f-c2ead67bbc9a)      
    
    - 예를들어 1,3번은 미등록 → 등록으로 변경하고 체크박스는 2,3번만 체크한 경우
        ![rmt_example_1](https://github.com/user-attachments/assets/8f89ff6b-aec5-4da6-b482-b2cabbaa10a9)   
      - 3번만 등록상태로 변경 및 등록일 추가   
        ![rmt_example_2](https://github.com/user-attachments/assets/f6135e8f-879c-4720-8855-27a389d15f7e)   
- 권한그룹관리   
    ![rgm_list](https://github.com/user-attachments/assets/4d5c448f-2e9b-461d-ad9f-d51d4112005b)
    - 권한관리에서 등록한 권한을 유저에게 등록
    - 권한관리 때와 동일하게 수정 후 체크박스 체크 후 → 등록을 눌러야 저장이 됨   
    

## 유의사항

## 참조
