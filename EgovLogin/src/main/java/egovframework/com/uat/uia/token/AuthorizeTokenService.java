package egovframework.com.uat.uia.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class AuthorizeTokenService {

    private final Date date = new Date();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final RedisTemplate<String, AuthorizeToken> authRedisTemplate;
    private final RedisTemplate<String, String> tokenRedisTemplate;

    public AuthorizeTokenService(RedisTemplate<String, AuthorizeToken> authRedisTemplate, RedisTemplate<String, String> tokenRedisTemplate) {
        this.authRedisTemplate = authRedisTemplate;
        this.tokenRedisTemplate = tokenRedisTemplate;
    }

    @Cacheable(value="authRedis", key="#username")
    public AuthorizeToken findToken(String username) {
        return authRedisTemplate.opsForValue().get(username);
    }

    @CachePut(value="authRedis", key="#username")
    public void saveToken(String username, String tokenKey, String refreshToken, String expDate) {
        AuthorizeToken AuthorizeToken = new AuthorizeToken(
                username, tokenKey, refreshToken, dateFormat.format(date), dateFormat.format(System.currentTimeMillis()+Long.parseLong(expDate)));
        authRedisTemplate.opsForValue().set(username, AuthorizeToken);
    }

    @CacheEvict(value="authRedis", key="#username")
    public void deleteToken(String username) {
        authRedisTemplate.delete(username);
    }

    @Cacheable(value="tokenRedis", key="#tokenKey")
    public String findTokenById(String tokenKey) {
        return tokenRedisTemplate.opsForValue().get(tokenKey);
    }

    @CachePut(value="tokenRedis", key="#tokenKey")
    public void saveTokenById(String tokenKey, String username) {
        tokenRedisTemplate.opsForValue().set(tokenKey, username);
    }

    @CacheEvict(value="tokenRedis", key="#tokenKey")
    public void deleteTokenById(String tokenKey) {
        tokenRedisTemplate.delete(tokenKey);
    }

}
