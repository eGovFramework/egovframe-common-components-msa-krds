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
  - Redis를 이용한 Token 관리 : [Redis 구성방법](#3-redis-구성방법---docker-이미지를-이용)
  - 사용자의 권한 및 역할(Role) 정보를 조회
      ```java
           // 1. Spring Security를 통한 권한 처리
                Authentication authentication;
                try {
                    authentication = new UsernamePasswordAuthenticationToken(loginDTO.getId(), loginDTO.getPassword());
                    // SecurityContextHolder 설정
                    SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(authentication));
                } catch (AuthenticationException e) {
                    log.error("Spring Security authentication error: {}", e.getMessage(), e);
                    return createLoginFailureResponse("권한 처리 중 오류가 발생했습니다. (Spring Security Authentication Error: " + e.getMessage() + ")");
                }

                // 권한에 해당하는 Role 정보를 문자열 형태로 설정
                List<Map.Entry<String, String>> rolePatternList = EgovUserDetailsHelper.getRoleAndPatternList();
                List<String> authorList = EgovUserDetailsHelper.getAuthorities();
                String accessiblePatterns = EgovUserDetailsHelper.getAccessiblePatterns(rolePatternList, authorList);

                // SecurityContextHolder 삭제
                new SecurityContextLogoutHandler().logout(request, response, authentication);

                LoginVO dtoToVo = new LoginVO();
                dtoToVo.setUserId(loginDTO.getId());
                dtoToVo.setName(loginDTO.getName());
                dtoToVo.setUniqId(loginDTO.getUniqId());
                dtoToVo.setAuthorList(accessiblePatterns);
    ```
   
  - 로그인 정보를 이용해 AccessToken을 발급
    - 1) jwtProvider를 통해 토큰 생성 (AccessToken, RefreshToken)
        ```java
            // 2. 토큰 생성
                  String accessToken;
                  String refreshToken;
                  try {
                      accessToken = jwtProvider.createAccessToken(dtoToVo);
                      refreshToken = jwtProvider.createRefreshToken(dtoToVo);
                  } catch (JwtException e) {
                      log.error("JWT token creation error: {}", e.getMessage(), e);
                      return createLoginFailureResponse("토큰 생성 중 오류가 발생했습니다. (Token Creation Error: " + e.getMessage() + ")");
                  } catch (IllegalArgumentException e) {
                      log.error("JWT token creation argument error: {}", e.getMessage(), e);
                      return createLoginFailureResponse("토큰 생성 중 오류가 발생했습니다. (Token Creation Argument Error: " + e.getMessage() + ")");
                  } catch (Exception e) {
                      log.error("Unexpected error during token creation: {}", e.getMessage(), e);
                      return createLoginFailureResponse("토큰 생성 중 예기치 않은 오류가 발생했습니다. (Unexpected Token Creation Error: " + e.getMessage() + ")");
                  }
      ```
    - 2) Redis를 이용해 토큰 관리
        ```java
                // 3. Redis에 토큰 저장
                try {
                    // Delete old token data of redis
                    AuthorizeToken oldAuthToken = authorizeTokenService.findToken(dtoToVo.getUserId());
                    if (!ObjectUtils.isEmpty(oldAuthToken)) {
                        authorizeTokenService.deleteTokenById(oldAuthToken.getTokenKey());
                        authorizeTokenService.deleteToken(dtoToVo.getUserId());
                    }

                    // Save token data of redis
                    String tokenKey = jwtProvider.generateHash(accessToken);
                    authorizeTokenService.saveTokenById(tokenKey, dtoToVo.getUserId());
                    authorizeTokenService.saveToken(dtoToVo.getUserId(), tokenKey, refreshToken, jwtProvider.getRefreshExpiration());
                } catch (DataAccessResourceFailureException e) {
                    log.error("Redis connection failure error: {}", e.getMessage(), e);
                    return createLoginFailureResponse("Redis 연결 오류가 발생했습니다. (Redis Connection Error: " + e.getMessage() + ")");
                } catch (DataAccessException e) {
                    log.error("Redis data access error: {}", e.getMessage(), e);
                    return createLoginFailureResponse("Redis 토큰 저장 중 오류가 발생했습니다. (Redis Data Access Error: " + e.getMessage() + ")");
                } catch (Exception e) {
                    log.error("Unexpected error during Redis token storage: {}", e.getMessage(), e);
                    return createLoginFailureResponse("Redis 토큰 저장 중 예기치 않은 오류가 발생했습니다. (Unexpected Redis Error: " + e.getMessage() + ")");
                }
        ```

    - 3) Cookie에 AccessToken 저장
        ```java
                // Create access token cookie
                long accessTokenMaxAge = Long.parseLong(jwtProvider.getAccessExpiration()) / 1000 + 60;
                ResponseCookie accessTokenCookie = jwtProvider.createCookie("accessToken", accessToken, accessTokenMaxAge);
                response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());

                message.put("status", "loginSuccess");
                message.put("userInfo", loginDTO.getName() + "(" + loginDTO.getId() + ")");
                message.put("userId", loginDTO.getId());
                message.put("accessToken", accessToken);
                message.put("refreshToken", refreshToken);
                message.put("errors", "");

                return ResponseEntity.ok(message);
      ```

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

## 참조
