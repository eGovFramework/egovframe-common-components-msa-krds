# 로그인정책관리

로그인정책관리는 특정 IP에 대한 로그인 제한과 같은 사용자 로그인 정책을 정의하고, 정의된 정책에 맞게 로그인을 제한하는 기능을 제공한다.

## 프로젝트 구성

``` text
  EgovLoginPolicy
    ├ /src/main
    │   ├ java
    │   │   └ egovframework.com
    │   │       ├ config
    │   │       ├ filter
    │   │       ├ pagination
    │   │       ├ uat
    │   │       │  └ uap
    │   │       └ EgovLoginPolicyApplication
    │   │
    │   └ resources
    │       ├ messages.egovframework.com
    │       │  └ uap
    │       ├ static
    │       ├ templates.egovframework.com.uat.uap
    │       └ application.yml
    └ pom.xml
```

## 화면 구성

### 로그인 정책 정의

<img width="923" alt="로그인정책관리" src="https://github.com/user-attachments/assets/2dd740de-70f0-4b4c-bd8e-e79acd4587fe" />

- 특정 사용자의 IP 정보를 입력하고 제한 여부를 'Y'로 구성하면 입력한 IP에 대해서는 로그인을 제한하게 된다.

### 로그인 제한

<img width="923" alt="Image" src="https://github.com/user-attachments/assets/c20b514b-6262-4675-ae42-7474e077454a" />

## 참조
