package egovframework.com.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.boot.crypto.service.impl.EgovEnvCryptoServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
@Getter
@Slf4j
public class GatewayJwtProvider {

    @Value("${token.accessSecret}")
    private String accessSecret;

    @Value("${token.refreshSecret}")
    private String refreshSecret;

    @Value("${token.accessExpiration}")
    private String accessExpiration;

    @Value("${token.refreshExpiration}")
    private String refreshExpiration;

    private final EgovEnvCryptoServiceImpl egovEnvCryptoService;

    public GatewayJwtProvider(@Qualifier("egovEnvCryptoService") EgovEnvCryptoServiceImpl egovEnvCryptoService) {
        this.egovEnvCryptoService = egovEnvCryptoService;
    }

    public SecretKey getSigningKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
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
        SecretKey key = getSigningKey(this.accessSecret);
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public Claims refreshExtractClaims(String token) {
        SecretKey key = getSigningKey(this.refreshSecret);
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public String getCookie(ServerHttpRequest request, String coookieName) {
        String cookieValue = "";
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        HttpCookie sessionIdCookie = cookies.getFirst(coookieName);
        if (sessionIdCookie != null) {
            cookieValue = sessionIdCookie.getValue();
        }
        return cookieValue;
    }

    public String extractUserId(String token) {
        return ObjectUtils.isEmpty(accessExtractClaims(token).get("userId")) ?
                "" : accessExtractClaims(token).get("userId").toString();
    }

    public String extractUserNm(String token) {
        return ObjectUtils.isEmpty(accessExtractClaims(token).get("userNm")) ?
                "" : accessExtractClaims(token).get("userNm").toString();
    }

    public String extractUniqId(String token) {
        return ObjectUtils.isEmpty(accessExtractClaims(token).get("uniqId")) ?
                "" : accessExtractClaims(token).get("uniqId").toString();
    }

    public String extractAuthId(String token) {
        return ObjectUtils.isEmpty(accessExtractClaims(token).get("authId")) ?
                "" : UriUtils.encode(decrypt(accessExtractClaims(token).get("authId").toString()), StandardCharsets.UTF_8);
    }

    public ServerHttpRequest headerSetting(ServerWebExchange exchange, String accessToken) {
        return exchange.getRequest().mutate()
                .headers(httpHeaders -> {
                    httpHeaders.set("X-CODE-ID", encrypt("eGovFramework"));
                    httpHeaders.set("X-USER-ID", extractUserId(accessToken));
                    httpHeaders.set("X-USER-NM", extractUserNm(accessToken));
                    httpHeaders.set("X-UNIQ-ID", extractUniqId(accessToken));
                })
                .build();
    }

    public String encrypt(String s) {
        return egovEnvCryptoService.encrypt(s);
    }

    public String decrypt(String s) {
        return egovEnvCryptoService.decrypt(s);
    }

}
