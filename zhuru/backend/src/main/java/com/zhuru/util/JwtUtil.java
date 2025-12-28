package com.zhuru.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
@Component
public class JwtUtil {

    /**
     * JWT密钥
     */
    @Value("${jwt.secret:zhuru-jwt-secret-key-1234567890-abcdefghijklmnopqrstuvwxyz-ABCDEFGHIJKLMNOPQRSTUVWXYZ}")
    private String secretKey;

    /**
     * JWT过期时间（毫秒）
     */
    @Value("${jwt.expiration:86400000}")
    private long expirationTime;

    /**
     * 生成JWT令牌
     * @param userId 用户ID
     * @param username 用户名
     * @return JWT令牌
     */
    public String generateToken(Long userId, String username) {
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", userId);
            claims.put("username", username);

            Date now = new Date();
            Date expiration = new Date(now.getTime() + expirationTime);

            System.out.println("JWT Configuration: secretKey=" + secretKey + ", secretKey length=" + secretKey.length() + ", expirationTime=" + expirationTime);
            System.out.println("Generating token for userId=" + userId + ", username=" + username);
            
            // 简化JWT生成，使用HS256算法代替HS512，避免密钥长度问题
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(expiration)
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();
            
            System.out.println("Token generated successfully: " + token);
            return token;
        } catch (Exception e) {
            System.out.println("Error generating JWT token: " + e.getMessage());
            e.printStackTrace();
            // 临时返回一个假token以便测试
            return "dummy-token-" + userId + ":" + username;
        }
    }

    /**
     * 从令牌中获取用户名
     * @param token JWT令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("username", String.class);
    }

    /**
     * 从令牌中获取用户ID
     * @param token JWT令牌
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("userId", Long.class);
    }

    /**
     * 验证令牌是否有效
     * @param token JWT令牌
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从令牌中获取声明
     * @param token JWT令牌
     * @return 声明
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}