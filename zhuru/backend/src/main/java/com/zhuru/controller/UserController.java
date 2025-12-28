package com.zhuru.controller;

import com.zhuru.common.ApiResponse;
import com.zhuru.dto.UserDTO;
import com.zhuru.entity.User;
import com.zhuru.service.UserService;
import com.zhuru.util.BeanConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户Controller
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     */
    @GetMapping("/{id}")
    public ApiResponse<UserDTO> getUserInfo(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ApiResponse.fail(1, "用户不存在");
        }
        UserDTO userDTO = BeanConvertUtil.convert(user, UserDTO.class);
        return ApiResponse.success(userDTO);
    }

    /**
     * 保存用户信息
     */
    @PostMapping
    public ApiResponse<UserDTO> saveUserInfo(@Valid @RequestBody UserDTO userDTO) {
        User user = BeanConvertUtil.convert(userDTO, User.class);
        User savedUser = userService.saveOrUpdateUser(user);
        UserDTO savedUserDTO = BeanConvertUtil.convert(savedUser, UserDTO.class);
        return ApiResponse.success(savedUserDTO);
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/{id}")
    public ApiResponse<UserDTO> updateUserInfo(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        User user = BeanConvertUtil.convert(userDTO, User.class);
        User savedUser = userService.saveOrUpdateUser(user);
        UserDTO savedUserDTO = BeanConvertUtil.convert(savedUser, UserDTO.class);
        return ApiResponse.success(savedUserDTO);
    }
}