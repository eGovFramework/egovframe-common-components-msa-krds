package egovframework.com.uat.uia.web;

import egovframework.com.uat.uia.service.*;
import egovframework.com.uat.uia.util.EgovClientIP;
import egovframework.com.uat.uia.util.EgovJwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.boot.security.bean.EgovReloadableFilterInvocationSecurityMetadataSource;
import org.egovframe.boot.security.userdetails.util.EgovUserDetailsHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.WebApplicationContextUtils;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("uiaEgovLoginManageAPIController")
@RequestMapping("/uat/uia")
@RequiredArgsConstructor
@Slf4j
public class EgovLoginManageAPIController {

    @Value("${egov.login.lock}")
    private String lock;

    @Value("${egov.login.lockCount}")
    private String lockCount;

    private final EgovLoginManageService service;
    private final EgovJwtProvider jwtProvider;
    private final ReloadableResourceBundleMessageSource messageSource;
    private final EgovReloadableFilterInvocationSecurityMetadataSource securityMetadataSource;

    @PostMapping("/actionLogin")
    public ResponseEntity<?> actionLogin(@RequestBody LoginVO loginVO, HttpServletRequest request, HttpServletResponse response) {
        if (ObjectUtils.isEmpty(loginVO)) {
            return ResponseEntity.ok(messageSource.getMessage("fail.common.login", null, request.getLocale()));
        }

        Map<String, Object> message = new HashMap<>();

        Map<String, Object> incorrect = loginIncorrect(loginVO, request);
        if (!incorrect.isEmpty()) {
            return ResponseEntity.ok(incorrect);
        }

        LoginDTO loginDTO = service.actionLogin(loginVO);

        if (ObjectUtils.isEmpty(loginDTO)) {
            message.put("status", "loginFailure");
            message.put("errors", messageSource.getMessage("fail.common.login", null, request.getLocale()));
            return ResponseEntity.ok(message);
        } else {
            // 권한 가져오기
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginDTO.getId(), loginDTO.getPassword());
            ApplicationContext act = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
            AuthenticationManager authenticationManager = act.getBean("authenticationManager", AuthenticationManager.class);
            // SecurityContextHolder 설정
            SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(authentication));
            log.debug("EgovLoginManageAPIController actionLogin isAuthenticated >>> {}", EgovUserDetailsHelper.isAuthenticated());

            List<Map.Entry<String, String>> rolePatternList = EgovUserDetailsHelper.getRoleAndPatternList();
            List<String> authorList = EgovUserDetailsHelper.getAuthorities();
            // 권한에 해당하는 Role 정보를 문자열 형태로 설정
            String accessiblePatterns = EgovUserDetailsHelper.getAccessiblePatterns(rolePatternList, authorList);
            log.debug("EgovLoginManageAPIController actionLogin accessiblePatterns >>> {}", accessiblePatterns);
            // SecurityContextHolder 삭제
            new SecurityContextLogoutHandler().logout(request, response, authentication);

            LoginVO dtoToVo = new LoginVO();
            dtoToVo.setUserId(loginDTO.getId());
            dtoToVo.setName(loginDTO.getName());
            dtoToVo.setUniqId(loginDTO.getUniqId());
            dtoToVo.setAuthorList(accessiblePatterns);

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

    public Map<String, Object> loginIncorrect(LoginVO loginVO, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        if (!Boolean.parseBoolean(this.lock)) {
            return response;
        }

        String clientIp = EgovClientIP.getClientIp();
        LoginPolicyVO loginPolicyVO = new LoginPolicyVO();
        loginPolicyVO.setEmployerId(loginVO.getUserId());
        loginPolicyVO = service.loginPolicy(loginPolicyVO);
        if (!ObjectUtils.isEmpty(loginPolicyVO)) {
            if ("Y".equals(loginPolicyVO.getLmttAt()) && clientIp.equals(loginPolicyVO.getIpInfo())) {
                response.put("status", "loginFailure");
                response.put("errors", messageSource.getMessage("fail.common.login.ip", null, request.getLocale()));
                return response;
            }
        }

        LoginIncorrectVO loginIncorrectVO = service.loginIncorrectList(loginVO);
        if (ObjectUtils.isEmpty(loginIncorrectVO)) {
            response.put("status", "loginFailure");
            response.put("errors", messageSource.getMessage("fail.common.login", null, request.getLocale()));
            return response;
        }

        String incorrectCode = service.loginIncorrectProcess(loginVO, loginIncorrectVO, lockCount);
        if (!"E".equals(incorrectCode)) {
            if ("L".equals(incorrectCode)) {
                response.put("status", "loginFailure");
                response.put("errors", messageSource.getMessage("fail.common.loginIncorrect",
                        new Object[]{lockCount, request.getLocale()}, request.getLocale()));
            } else if ("C".equals(incorrectCode)) {
                response.put("status", "loginFailure");
                response.put("errors", messageSource.getMessage("fail.common.login", null, request.getLocale()));
            }
        }

        return response;
    }

    @GetMapping("/validateRefreshToken")
    @ResponseBody
    public Mono<Boolean> validateRefreshToken(@RequestHeader String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        String username = jwtProvider.decrypt(jwtProvider.extractUserId(refreshToken));
        if (ObjectUtils.isEmpty(username)) {
            jwtProvider.deleteCookie(request, response, "accessToken");
            jwtProvider.deleteCookie(request, response, "refreshToken");
            return Mono.just(false);
        } else {
            return Mono.just(true);
        }
    }

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

    @PostMapping("/reload")
    public ResponseEntity<String> reloadSecurityMetadata() {
        EgovUserDetailsHelper.reloadSecurityMetadata(securityMetadataSource);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/actionLogout")
    public ResponseEntity<?> actionLogout(HttpServletRequest request, HttpServletResponse response) {
        log.debug("##### EgovLoginManageAPIController logout started #####");

        jwtProvider.deleteCookie(request, response, "accessToken");
        jwtProvider.deleteCookie(request, response, "refreshToken");

        Map<String, Object> message = new HashMap<>();
        message.put("status", "success");
        message.put("error", "");
        return ResponseEntity.ok(message);
    }

    @RequestMapping("/loginFailure")
    public String loginFailure() {
        return "login Failure!";
    }

    @RequestMapping("/accessDenied")
    public String accessDenied() {
        return "Access Denied!";
    }

    @RequestMapping("/consurentExpired")
    public String consurentExpired() {
        return "consurent Expired!";
    }

    @RequestMapping("/defaultTarget")
    public String defaultTarget() {
        return "default Target!";
    }

    @RequestMapping("/csrfAccessDenied")
    public String csrfAccessDenied() {
        return "csrf Access Denied!";
    }

}
