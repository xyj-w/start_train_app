package com.zhuru.controller;

import com.zhuru.common.ApiResponse;
import com.zhuru.dto.AuthDTO;
import com.zhuru.entity.User;
import com.zhuru.exception.BusinessException;
import com.zhuru.service.UserService;
import com.zhuru.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@Valid @RequestBody AuthDTO authDTO) {
        try {
            log.info("Login request received: username={}", authDTO.getUsername());
            User user = userService.login(authDTO.getUsername(), authDTO.getPassword());
            log.info("User found: id={}, username={}", user.getId(), user.getUsername());
            
            // 生成JWT令牌
            log.info("Generating JWT token for user: id={}, username={}", user.getId(), user.getUsername());
            String token = jwtUtil.generateToken(user.getId(), user.getUsername());
            log.info("JWT token generated successfully: {}", token.substring(0, 20) + "...");
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("userId", user.getId());
            response.put("username", user.getUsername());
            
            return ApiResponse.success(response);
        } catch (BusinessException e) {
            log.error("Business error during login: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("System error during login: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ApiResponse<Map<String, Object>> register(@Valid @RequestBody AuthDTO authDTO) {
        User user = new User();
        user.setUsername(authDTO.getUsername());
        user.setPassword(authDTO.getPassword());
        
        User registeredUser = userService.register(user);
        
        // 生成JWT令牌
        String token = jwtUtil.generateToken(registeredUser.getId(), registeredUser.getUsername());
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", registeredUser.getId());
        response.put("username", registeredUser.getUsername());
        
        return ApiResponse.success(response);
    }

    /**
     * 用户退出登录
     */
    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        // 在JWT认证中，退出登录主要由前端处理（清除token）
        // 这里可以添加额外的业务逻辑，如记录退出日志等
        return ApiResponse.success();
    }
}