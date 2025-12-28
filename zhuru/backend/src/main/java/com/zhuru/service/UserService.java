package com.zhuru.service;

import com.zhuru.entity.User;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 根据ID查询用户
     */
    User getUserById(Long id);

    /**
     * 根据用户名查询用户
     */
    User getUserByUsername(String username);

    /**
     * 保存或更新用户信息
     */
    User saveOrUpdateUser(User user);

    /**
     * 用户登录
     */
    User login(String username, String password);

    /**
     * 用户注册
     */
    User register(User user);
}