# 로그인

아이디와 비밀번호를 통한 로그인을 진행하고 인증토큰을 받아 처리하는 컴포넌트

## 서비스 상태 (현재 모듈 기준)

| 항목 | 내용 |
|------|------|
| **역할** | 로그인·JWT(Access/Refresh) 쿠키 발급·재발급·로그아웃 |
| **애플리케이션명** | `EgovLogin` → Eureka `EGOVLOGIN` |
| **포트** | `0`(랜덤). **Gateway** `http://localhost:9000/uat/uia/...` |
| **Config Server** | 사용 |
| **Eureka** | 등록 |
| **필수** | **Redis** (`spring.data.redis`, 기본 `localhost:6379`) — 로그인·세션 연동 |

※ **로그인정책(IP 제한 등)** 은 별도 모듈 `EgovLoginPolicy` (`/uat/uap`) 이다.

## 프로젝트 구성

``` text
  EgovLogin
    ├ /src/main
    │   ├ java
    │   │   └ egovframework.com
    │   │       ├ config
    │   │       ├ filter
    │   │       ├ pagination
    │   │       ├ uat
    │   │       │  └ uia
    │   │       └ EgovLoginApplication
    │   │
    │   └ resources
    │       ├ messages.egovframework.com
    │       │   └ uat.uia
    │       ├ static
    │       ├ templates.egovframework.com.uat
    │       │   └ uia
    │       └ application.yml
    └ pom.xml
```

- 로그인 (`/uat/uia`)

## 화면 구성

### 1. 로그인
  - userID와 userPW를 이용해 로그인
   ![login](https://github.com/user-attachments/assets/1253495b-7a9d-485a-82f2-e45da880ff4d)

  - 일반(GNR), 기업(ENT), 업무(USR) 회원별 로그인 가능

  - 사용자의 권한 및 역할(Role) 정보를 조회
      ```java
          // 사용자의 ID와 비밀번호로 인증 토큰을 생성
          Authentication authentication = new UsernamePasswordAuthenticationToken(loginDTO.getId(), loginDTO.getPassword());

          //WebApplicationContextUtils를 통해 현재 웹 애플리케이션의 Spring ApplicationContext를 가져옴
          ApplicationContext act = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());

          // Security 간소화 서비스를 통해 설정한 AuthenticationManager 빈을 가져옴
          AuthenticationManager authenticationManager = act.getBean("authenticationManager", AuthenticationManager.class);

          // SecurityContextHolder 설정
          SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(authentication));

          // 권한 및 Role 정보 조회
          List<Map.Entry<String, String>> rolePatternList = EgovUserDetailsHelper.getRoleAndPatternList();
          List<String> authorList = EgovUserDetailsHelper.getAuthorities();

          // 권한에 해당하는 Role 정보를 문자열 형태로 설정 >> token에서 사용
          String accessiblePatterns = EgovUserDetailsHelper.getAccessiblePatterns(rolePatternList, authorList);

          // SecurityContextHolder 삭제
          new SecurityContextLogoutHandler().logout(request, response, authentication);     
      ```
   
  - 로그인 정보를 이용해 AccessToken을 발급
      ```java
          @PostMapping("/actionLogin")
          public ResponseEntity<?> actionLogin(@RequestBody LoginVO loginVO, HttpServletRequest request, HttpServletResponse response) {
                  .
                  .
                  .
                  String accessToken = jwtProvider.createAccessToken(dtoToVo);
                  String refreshToken = jwtProvider.createRefreshToken(dtoToVo);
      
                  long accessCookieMaxAge = Duration.ofMillis(Long.parseLong(jwtProvider.getAccessExpiration())).getSeconds();
                  long refreshCookieMaxAge = Duration.ofMillis(Long.parseLong(jwtProvider.getRefreshExpiration())).getSeconds();
      
                  ResponseCookie accessTokenCookie = jwtProvider.createCookie("accessToken", accessToken, accessCookieMaxAge);
                  response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
      
                  ResponseCookie refreshTokenCookie = jwtProvider.createCookie("refreshToken", refreshToken,refreshCookieMaxAge);
                  response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
      
                  message.put("status", "loginSuccess");
                  message.put("userInfo", loginDTO.getName() + "(" + loginDTO.getId() + ")");
                  message.put("errors", "");
                  return ResponseEntity.ok(message);
              }
          }
      ```
      1. jwtProvider를 이용해 accessToken과 refreshToken을 발급      
      2. 발급된 Token은 cookie에 저장   
          * 브라우저 > 개발자모드(F12) > 애플리케이션 > 쿠키 > 페이지 URL 에서 확인 가능   
        ![cookie_token](https://github.com/user-attachments/assets/aed367b1-b4da-423b-a600-2e260951b2e8)
      3. accessToken의 지정된 시간이 종료되면 refreshToken에 의해 다시 accessToken이 재발급된다.   
          
          ```java
            @GetMapping("/recreateAccessToken")
            @ResponseBody
            public String recreateAccessToken(@RequestHeader String refreshToken) {
                Claims claims = Jwts.claims()
                        .subject("Token")
                        .add("userId", jwtProvider.extractUserId(refreshToken))
                        .add("userNm", jwtProvider.extractUserNm(refreshToken))
                        .add("uniqId", jwtProvider.extractUniqId(refreshToken))
                        .add("authId", jwtProvider.extractAuthId(refreshToken))
                        .issuedAt(new Date(System.currentTimeMillis()))
                        .expiration(new Date(System.currentTimeMillis() + Long.parseLong(jwtProvider.getAccessExpiration())))
                        .build();
                SecretKey key = jwtProvider.getSigningKey(jwtProvider.getRefreshSecret());
                return Jwts.builder().claims(claims).signWith(key).compact();
            }
          ```

          *accessToken과 refreshToken의 시간은 ConfigServer에서 지정할 수 있음   
          
### 2. 로그아웃

로그아웃 시 cookie에 저장된 accessToken과 refreshToken 삭제   
  ``` java
        @PostMapping("/logout")
        @ResponseBody
        public Mono<Boolean> logout(HttpServletRequest request, HttpServletResponse response) {
            jwtProvider.deleteCookie(request, response, "accessToken");
            jwtProvider.deleteCookie(request, response, "refreshToken");
            return Mono.just(true);
        }
  ```

### 3. Redis 구성방법 - Docker 이미지를 이용
- Docker Desktop을 이용중이라면 검색창에서 'redis'검색 후 Image Pull
- 필요한 docker 설정으로 docker run 명령어 실행</br>
  `docker run -d --name egov-redis -p 6379:6379 -e REDIS_PASSWORD=rhdxhd12 redis:7-alpine redis-server --requirepass rhdxhd12`
    - --name <컨테이너명>
    - -p <포트>
    - -e REDIS_PASSWORD=<Redis에서 사용할 비밀번호>
    - <redis 이미지 이름>
- AuthorizeTokenRedisConfig에서 설정한 값과 동일하게 서버 실행 필요
- 로그인 후 redis-cli를 통해 등록된 key값을 확인할 수 있다.
    - docker redis의 cli 접속 : `docker exec -it egov-redis redis-cli -a rhdxhd12`
    - 등록된 모든 키 값 확인 : `keys *`
    - 특정 Key의 값 확인 : `get <KEY값>`

## 유의사항

- 본 모듈 `pom.xml` 기준 **Java 17**. Redis 미기동 시 로그인 관련 기능이 실패할 수 있다.

## 참조
