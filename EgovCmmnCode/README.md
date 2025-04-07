# 공통코드관리

## 프로젝트 소개

공통코드는 동일한 특성을 가진 데이터들을 특성별로 분류하여 지정하는 기능이다.

## 프로젝트 구성

``` text
  EgovCmmnCode
    ├ /src/main
    │   ├ java
    │   │   └ egovframework.com
    │   │       ├ config
    │   │       ├ filter
    │   │       ├ pagination
    │   │       ├ sym.ccm
    │   │       │  ├ cca
    │   │       │  ├ ccc
    │   │       │  └ cde
    │   │       └ EgovAuthorApplication
    │   │
    │   └ resources
    │       ├ messages.egovframework.com
    │       ├ static
    │       ├ templates.egovframework.com.sym.ccm
    │       │   ├ cca
    │       │   ├ ccc
    │       │   └ cde
    │       └ application.yml
    └ pom.xml
```

- 공통코드(`/com/sym/ccm/cca`)
- 공통분류코드 (`/com/sym/ccm/ccc`)
- 공통상세코드(`/com/sym/ccm/cde`)


## 화면 구성

각 기능마다 목록조회, 등록, 수정, 삭제 기능 제공

### 공통분류코드

1. 목록 조회   
![ccc_List](https://github.com/user-attachments/assets/4289e33e-3bae-4f63-bbbb-349039ccc458)   
공통코드에 대한 목록 조회 가능하며 사용하지 않는 분류코드도 조회된다.

2. 등록/수정   
![ccc_insert](https://github.com/user-attachments/assets/c9cc3e97-ec07-4eb4-80db-8c401d75581e)   
공통코드분류를 등록하고 등록된 공통분류코드는 공통코드와 공통상세코드에서 사용된다.   
- '분류코드'는 공통코드에서 사용되기때문에 중복되지 않고 식별이 쉬운 이름으로 작성
- 수정 시에 분류코드는 변경할 수 없음

3. 삭제 시 사용여부가 "N"으로 변경되고 다시 사용하고자하는 경우에는 '수정'페이지에서 사용여부 변경

### 공통코드

1. 목록 조회   
![cca_list](https://github.com/user-attachments/assets/fef28601-587f-4470-8761-c922f039d102)   
공통분류코드별로 정렬되어있는 공통코드의 목록 조회   

2. 등록/수정   
![cca_insert](https://github.com/user-attachments/assets/cbe2d641-58a5-43ad-8e39-4259e6110b0c)     
- 공통분류코드에서 등록한 분류코드를 지정
- '코드ID'는 공통상세코드에서도 사용되기때문에 중복되지 않는 ID명으로 작성
- 수정 시 '분류코드명'과 '코드ID'는 수정할 수 없음

3. 삭제 시 사용여부가 "N"으로 변경되고 다시 사용하고자하는 경우에는 '수정'페이지에서 사용여부 변경

### 공통상세코드

1. 목록 조회
![cde_list](https://github.com/user-attachments/assets/04d373e0-9592-4e75-ac7c-b31b15362062)   
코드ID를 기준으로 정렬되어있는 공통상세코드 목록 조회
  
2. 등록/수정
- 공통분류코드 선택   
![cde_insert_1](https://github.com/user-attachments/assets/3c6ffbbf-ec89-4d79-873f-d079775e14bc)   
- 공통분류코드에 포함되어있는 공통코드 목록에서 선택   
![cde_insert_2](https://github.com/user-attachments/assets/c531c31b-eac1-43e9-a2ea-88b1a766cfbe)   

3. 삭제 시 사용여부가 "N"으로 변경되고 다시 사용하고자하는 경우에는 '수정'페이지에서 사용여부 변경

## 유의사항

## 참조
