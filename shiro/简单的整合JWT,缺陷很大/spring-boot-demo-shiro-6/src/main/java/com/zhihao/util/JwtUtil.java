package com.zhihao.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@ConfigurationProperties(prefix = "jwt.config")
public class JwtUtil {

    @Value("${jwt.config.key}")
    private String key;

    private long jwtTimeout;//一个小时

    private String anonUrl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getJwtTimeout() {
        return jwtTimeout;
    }

    public void setJwtTimeout(long jwtTimeout) {
        this.jwtTimeout = jwtTimeout;
    }

    public String getAnonUrl() {
        return anonUrl;
    }

    public void setAnonUrl(String anonUrl) {
        this.anonUrl = anonUrl;
    }

    /**
     * 生成JWT
     *
     * @param id      用户id
     * @param subject 用户名
     * @return java.lang.String
     */
    public String createJWT(String id, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setId(id) //id
                .setSubject(subject) //主题
                .setIssuedAt(now) //签发时间
                .signWith(SignatureAlgorithm.HS256, key); //加密
        //超时大于0 设置token超时
        if (jwtTimeout > 0) {
            //转换成超时毫秒
            long timeout = nowMillis + (jwtTimeout * 1000);
            builder.setExpiration(new Date(timeout));
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     *
     * @param jwtStr
     * @return
     */
    public Claims parseJWT(String jwtStr){
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();

    }
}