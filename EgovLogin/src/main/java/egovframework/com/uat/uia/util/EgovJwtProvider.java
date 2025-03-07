package egovframework.com.uat.uia.util;

import egovframework.com.uat.uia.service.LoginVO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.egovframe.boot.crypto.service.impl.EgovEnvCryptoServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseCookie;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Configuration
@Getter
public class EgovJwtProvider {

    @Value("${token.accessSecret}")
    private String accessSecret;

    @Value("${token.refreshSecret}")
    private String refreshSecret;

    @Value("${token.accessExpiration}")
    private String accessExpiration;

    @Value("${token.refreshExpiration}")
    private String refreshExpiration;

    private final EgovEnvCryptoServiceImpl egovEnvCryptoService;

    public EgovJwtProvider(@Qualifier("egovEnvCryptoService") EgovEnvCryptoServiceImpl egovEnvCryptoService) {
        this.egovEnvCryptoService = egovEnvCryptoService;
    }

    public SecretKey getSigningKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(LoginVO loginVO) {
        SecretKey key = getSigningKey(accessSecret);
        Claims claims = createClaims(loginVO, accessExpiration);
        return Jwts.builder().claims(claims).signWith(key).compact();
    }

    public String createRefreshToken(LoginVO loginVO) {
        SecretKey key = getSigningKey(refreshSecret);
        Claims claims = createClaims(loginVO, refreshExpiration);
        return Jwts.builder().claims(claims).signWith(key).compact();
    }

    public Claims createClaims(LoginVO loginVO, String expiration) {
        ClaimsBuilder builder = Jwts.claims()
                .subject("Token")
                .add("userId", ObjectUtils.isEmpty(loginVO.getUserId()) ? "" : encrypt(loginVO.getUserId()))
                .add("userNm", ObjectUtils.isEmpty(loginVO.getName()) ? "" : encrypt(loginVO.getName()))
                .add("uniqId", ObjectUtils.isEmpty(loginVO.getUniqId()) ? "" : encrypt(loginVO.getUniqId()))
                .add("authId", ObjectUtils.isEmpty(loginVO.getAuthorCode()) ? "" : encrypt(loginVO.getAuthorCode()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration)));

        return builder.build();
    }

    public int accessValidateToken(String token) {
        try {
            accessExtractClaims(token);
            return 200;
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return 400;
        } catch (ExpiredJwtException e) {
            return 401;
        }
    }

    public int refreshValidateToken(String token) {
        try {
            refreshExtractClaims(token);
            return 200;
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return 400;
        } catch (ExpiredJwtException e) {
            return 401;
        }
    }

    public Claims accessExtractClaims(String token) {
        SecretKey key = getSigningKey(accessSecret);
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public Claims refreshExtractClaims(String token) {
        SecretKey key = getSigningKey(refreshSecret);
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public String extractUserId(String token) {
        return ObjectUtils.isEmpty(refreshExtractClaims(token).get("userId")) ?
                "" : refreshExtractClaims(token).get("userId").toString();
    }

    public String extractUserNm(String token) {
        return ObjectUtils.isEmpty(refreshExtractClaims(token).get("userNm")) ?
                "" : refreshExtractClaims(token).get("userNm").toString();
    }

    public String extractUniqId(String token) {
        return ObjectUtils.isEmpty(refreshExtractClaims(token).get("uniqId")) ?
                "" : refreshExtractClaims(token).get("uniqId").toString();
    }

    public String extractAuthId(String token) {
        return ObjectUtils.isEmpty(refreshExtractClaims(token).get("authId")) ?
                "" : refreshExtractClaims(token).get("authId").toString();
    }

    public String encrypt(String s) {
        return egovEnvCryptoService.encrypt(s);
    }

    public String decrypt(String s) {
        return egovEnvCryptoService.decrypt(s);
    }

    public ResponseCookie createCookie(String tokenName, String tokenValue, long tokenMaxAge) {
        return ResponseCookie.from(tokenName, tokenValue)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(tokenMaxAge + 10)
                .sameSite("Strict")
                .build();
    }

    public String getCookie(HttpServletRequest request, String cookieName) {
        String cookieValue = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    cookieValue = cookie.getValue();
                }
            }
        }
        return cookieValue;
    }

    public void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }

}
