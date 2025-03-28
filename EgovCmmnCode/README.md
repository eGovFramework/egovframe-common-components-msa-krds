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
![ccc_List](https://github.com/user-attachments/assets/53f079c2-e9b1-4ba8-9608-615beff2f807)   
공통코드에 대한 목록 조회 가능하며 사용하지 않는 분류코드도 조회된다.

2. 등록/수정   
![ccc_insert](https://github.com/user-attachments/assets/f4a7d27c-c7a5-49ad-8e17-be00b29b3c7d)   
공통코드분류를 등록하고 등록된 공통분류코드는 공통코드와 공통상세코드에서 사용된다.   
- '분류코드'는 공통코드에서 사용되기때문에 중복되지 않고 식별이 쉬운 이름으로 작성
- 수정 시에 분류코드는 변경할 수 없음

3. 삭제 시 사용여부가 "N"으로 변경되고 다시 사용하고자하는 경우에는 '수정'페이지에서 사용여부 변경

### 공통코드

1. 목록 조회   
![cca_list](https://github.com/user-attachments/assets/5b1360fc-79f5-4c96-a603-f6f34a3b0f89)
공통분류코드별로 정렬되어있는 공통코드의 목록 조회   

2. 등록/수정   
![cca_insert](https://github.com/user-attachments/assets/d6e64d18-2c1a-4973-8520-e08e27970304)   
- 공통분류코드에서 등록한 분류코드를 지정
- '코드ID'는 공통상세코드에서도 사용되기때문에 중복되지 않는 ID명으로 작성
- 수정 시 '분류코드명'과 '코드ID'는 수정할 수 없음

3. 삭제 시 사용여부가 "N"으로 변경되고 다시 사용하고자하는 경우에는 '수정'페이지에서 사용여부 변경

### 공통상세코드

1. 목록 조회
![cde_list](https://github.com/user-attachments/assets/1aa6e2bc-55e6-4784-9813-9c53c962dda9)
코드ID를 기준으로 정렬되어있는 공통상세코드 목록 조회
  
2. 등록/수정
- 공통분류코드 선택   
![cde_insert_1](https://github.com/user-attachments/assets/1cecfbd4-97f8-450b-8764-64f54848c20b)   
- 공통분류코드에 포함되어있는 공통코드 목록에서 선택   
![cde_insert_2](https://github.com/user-attachments/assets/9f412d0c-129d-4c31-8f8b-6608a89b9151)

3. 삭제 시 사용여부가 "N"으로 변경되고 다시 사용하고자하는 경우에는 '수정'페이지에서 사용여부 변경

## 유의사항

'NCC-Project의 메뉴 접근 권한은 ConfigServer에서 관리하고 있으므로 이 컴포넌트를 이용해 유저의 권한을 변경하더라도 프로젝트의 접근 권한은 변경되지 않습니다.

## 참조
