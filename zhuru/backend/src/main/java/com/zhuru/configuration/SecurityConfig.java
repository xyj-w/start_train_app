package com.zhuru.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用CSRF保护
        http.csrf().disable()
                // 允许所有请求通过
                .authorizeRequests()
                .anyRequest().permitAll()
                // 禁用表单登录
                .and().formLogin().disable()
                // 禁用HTTP基本认证
                .httpBasic().disable()
                // 禁用注销功能
                .logout().disable();
    }
}