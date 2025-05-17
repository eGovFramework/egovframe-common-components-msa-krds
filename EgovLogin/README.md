# 로그인

아이디와 비밀번호를 통한 로그인을 진행하고 인증토큰을 받아 처리하는 컴포넌트

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

- 로그인정책관리 (`/com/uat/uap`)
- 로그인 (`/com/uat/uia`)

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


## 유의사항

## 참조
