package com.zhuru.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * 日志拦截器
 */
@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {

    /**
     * 请求处理前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("===============================================");
        log.info("[请求开始]");
        log.info("请求URL: {}", request.getRequestURL());
        log.info("请求方法: {}", request.getMethod());
        log.info("客户端IP: {}", request.getRemoteAddr());
        
        // 打印请求头
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            log.info("请求头 {}: {}", headerName, headerValue);
        }
        
        return true;
    }

    /**
     * 请求处理后，视图渲染前
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("[请求处理完成]");
    }

    /**
     * 整个请求结束后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("响应状态码: {}", response.getStatus());
        if (ex != null) {
            log.error("请求处理异常: {}", ex.getMessage(), ex);
        }
        log.info("[请求结束]");
        log.info("===============================================");
    }
}