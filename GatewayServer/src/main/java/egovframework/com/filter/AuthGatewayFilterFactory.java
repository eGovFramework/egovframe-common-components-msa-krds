package egovframework.com.filter;

import egovframework.com.config.GatewayJwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {

    @Value("${roles.roleAdmin}")
    private String roleAdminPaths;

    @Value("${roles.roleUser}")
    private String roleUserPaths;

    private final WebClient.Builder webClientBuilder;
    private final GatewayJwtProvider gatewayJwtProvider;

    public AuthGatewayFilterFactory(WebClient.Builder webClientBuilder, GatewayJwtProvider gatewayJwtProvider) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
        this.gatewayJwtProvider = gatewayJwtProvider;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();

            if (path.matches(".*\\.(css|fonts|img|js)$")) {
                return chain.filter(exchange);
            }

            String accessToken = gatewayJwtProvider.getCookie(exchange.getRequest(), "accessToken");
            int accessValidationStatus = gatewayJwtProvider.accessValidateToken(accessToken);
            if (accessValidationStatus == 400) {
                log.debug("##### AuthGatewayFilterFactory accessToken invalid (400).");
                return onError(exchange);
            }

            if (accessValidationStatus == 401) {
                log.debug("##### AuthGatewayFilterFactory accessToken expired (401), checking refreshToken...");

                String refreshToken = gatewayJwtProvider.getCookie(exchange.getRequest(), "refreshToken");
                if (ObjectUtils.isEmpty(refreshToken)) {
                    log.debug("##### AuthGatewayFilterFactory refreshToken is null.");
                    return onError(exchange);
                }

                int refreshValidationStatus = gatewayJwtProvider.refreshValidateToken(refreshToken);
                if (refreshValidationStatus == 400 || refreshValidationStatus == 401) {
                    log.debug("##### AuthGatewayFilterFactory refreshToken invalid.");
                    return onError(exchange);
                }

                log.debug("##### AuthGatewayFilterFactory validating refresh token...");
                return webClientBuilder.build().get()
                        .uri("lb://EGOVLOGIN/uat/uia/validateRefreshToken")
                        .header("refreshToken", refreshToken)
                        .retrieve()
                        .bodyToMono(Boolean.class)
                        .flatMap(isValid -> {
                            if (Boolean.TRUE.equals(isValid)) {
                                log.debug("##### Refresh token is valid, generating new access token...");
                                return regenerateAccessToken(exchange, refreshToken, chain);
                            } else {
                                log.debug("##### AuthGatewayFilterFactory refreshToken invalid.");
                                return onError(exchange);
                            }
                        })
                        .onErrorResume(error -> {
                            log.error("##### Error during refresh token validation: ", error);
                            return onError(exchange);
                        });
            }

            // URL별로 접근 가능한 역할 확인
            String role = gatewayJwtProvider.extractAuthId(accessToken);
            String param = getParamFromPath(path);
            log.debug("##### AuthGatewayFilterFactory role {}, param {}", role, param);
            if (!isAuthorized(role, path)) {
                return onForbidden(exchange, param);
            }

            // Proceed with valid accessToken
            ServerHttpRequest request = gatewayJwtProvider.headerSetting(exchange, accessToken);
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    private Mono<Void> regenerateAccessToken(ServerWebExchange exchange, String refreshToken, GatewayFilterChain chain) {
        return webClientBuilder.build().get()
                .uri("lb://EGOVLOGIN/uat/uia/recreateAccessToken")
                .header("refreshToken", refreshToken)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(token -> {
                    log.debug("##### New access token received: {}", token);
                    long accessCookieMaxAge = Duration.ofMillis(Long.parseLong(gatewayJwtProvider.getAccessExpiration())).getSeconds();
                    ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", token)
                            .httpOnly(true)
                            .secure(false)
                            .path("/")
                            .maxAge(accessCookieMaxAge + 10)
                            .sameSite("Strict")
                            .build();
                    exchange.getResponse().addCookie(accessTokenCookie);
                    return chain.filter(exchange);
                })
                .onErrorResume(error -> {
                    log.error("##### Error while regenerating access token: ", error);
                    return onError(exchange);
                });
    }

    private boolean isAuthorized(String role, String path) {
        Map<String, List<String>> roleAccessMap = new HashMap<>();
        roleAccessMap.put("ROLE_ADMIN", Arrays.asList(roleAdminPaths.split(",\\s*")));
        roleAccessMap.put("ROLE_USER", Arrays.asList(roleUserPaths.split(",\\s*")));

        List<String> allowedPaths = roleAccessMap.get(role);
        if (allowedPaths == null) {
            return false;
        }

        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String allowedPath : allowedPaths) {
            if (pathMatcher.match(allowedPath, path)) {
                return true;
            }
        }
        return false;
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        // Remove accessToken, refreshToken cookie
        deleteCookie(exchange, "accessToken");
        deleteCookie(exchange, "refreshToken");
        return redirectToErrorPage(exchange, "/uat/uia/loginView", "0");
    }

    private Mono<Void> onForbidden(ServerWebExchange exchange, String param) {
        return redirectToErrorPage(exchange, "/uat/uia/loginForbidden", param);
    }

    private Mono<Void> redirectToErrorPage(ServerWebExchange exchange, String errorPage, String param) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FOUND); // 302 Redirect

        log.debug("##### Redirect to param: {}", param);
        String redirectUrl = "";
        if ("0".equals(param)) {
            redirectUrl = UriComponentsBuilder.fromUriString(errorPage)
                    .build()
                    .toUriString();
        } else {
            // 쿼리 파라미터 추가
            redirectUrl = UriComponentsBuilder.fromUriString(errorPage)
                    .queryParam("pathCode", param)
                    .build()
                    .toUriString();
        }

        response.getHeaders().set(HttpHeaders.LOCATION, redirectUrl);
        DataBuffer buffer = response.bufferFactory().wrap(("Redirecting to Page").getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    private void deleteCookie(ServerWebExchange exchange, String cookieName) {
        ServerHttpResponse response = exchange.getResponse();
        ResponseCookie deletedRefreshToken = ResponseCookie.from(cookieName, "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0) // Expire immediately
                .sameSite("Strict")
                .build();
        response.addCookie(deletedRefreshToken);
    }

    private String getParamFromPath(String path) {
        if (path.contains("/uat/uap") || path.contains("/mpi")) {
            return "1";
        } else if (path.contains("/sec")) {
            return "2";
        } else if (path.contains("/cop")) {
            return "3";
        } else if (path.contains("/uss")) {
            return "4";
        } else if (path.contains("/sym")) {
            return "5";
        } else if (path.contains("/ext")) {
            return "6";
        }
        return "0";
    }

    public static class Config {
    }

}
