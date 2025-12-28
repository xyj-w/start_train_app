package com.zhuru.service.impl;

import com.zhuru.entity.User;
import com.zhuru.exception.BusinessException;
import com.zhuru.repository.UserRepository;
import com.zhuru.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现类
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 根据ID查询用户
     */
    @Override
    public User getUserById(Long id) {
        return userRepository.findByIdAndDeletedFalse(id);
    }

    /**
     * 根据用户名查询用户
     */
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsernameAndDeletedFalse(username);
    }

    /**
     * 保存或更新用户信息
     */
    @Override
    public User saveOrUpdateUser(User user) {
        // 计算营养素数据
        calculateNutrition(user);
        
        Long currentTime = System.currentTimeMillis();
        if (user.getId() == null) {
            // 新增用户
            user.setCreatedAt(currentTime);
        } else {
            // 更新用户，保留原有用户名和密码
            User existingUser = userRepository.findByIdAndDeletedFalse(user.getId());
            if (existingUser != null) {
                // 保留原有用户名
                if (user.getUsername() == null || user.getUsername().isEmpty()) {
                    user.setUsername(existingUser.getUsername());
                }
                // 保留原有密码
                if (user.getPassword() == null || user.getPassword().isEmpty()) {
                    user.setPassword(existingUser.getPassword());
                }
            }
        }
        user.setUpdatedAt(currentTime);
        return userRepository.save(user);
    }

    /**
     * 用户登录
     */
    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsernameAndDeletedFalse(username);
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        return user;
    }

    /**
     * 用户注册
     */
    @Override
    public User register(User user) {
        // 检查用户名是否已存在
        User existingUser = userRepository.findByUsernameAndDeletedFalse(user.getUsername());
        if (existingUser != null) {
            throw new BusinessException("用户名已存在");
        }
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 设置默认值
        user.setDeleted(false);
        // 保存用户
        return userRepository.save(user);
    }
    
    /**
     * 计算营养素数据
     */
    private void calculateNutrition(User user) {
        // 计算BMI指数: 体重(kg) / (身高(m))²
        if (user.getHeight() != null && user.getWeight() != null) {
            double heightInMeters = user.getHeight() / 100.0;
            double bmi = user.getWeight() / (heightInMeters * heightInMeters);
            // 保留一位小数
            user.setBmi(Math.round(bmi * 10.0) / 10.0);
        }
        
        // 计算基础代谢率(BMR)使用Mifflin-St Jeor公式
        if (user.getGender() != null && user.getWeight() != null && user.getHeight() != null && user.getAge() != null) {
            double bmr;
            // 同时支持英文和中文性别值
            if ("男".equals(user.getGender()) || "male".equals(user.getGender())) {
                bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() + 5;
            } else {
                bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() - 161;
            }
            user.setBmr((int) Math.round(bmr));
        }
        
        // 计算总消耗热量(TDEE) = BMR × 活动系数
        if (user.getBmr() != null && user.getTrainingIntensity() != null) {
            double activityFactor = getActivityFactor(user.getTrainingIntensity());
            int tdee = (int) Math.round(user.getBmr() * activityFactor);
            user.setTdee(tdee);
            
            // 计算训练日和休息日摄入热量
            user.setTrainCalories(tdee);
            user.setRestCalories((int) Math.round(tdee * 0.9));
        }
    }
    
    /**
     * 获取训练强度对应的活动系数
     */
    private double getActivityFactor(String trainingIntensity) {
        switch (trainingIntensity) {
            // 支持中文训练强度值
            case "久坐不动":
            // 支持英文训练强度值
            case "sedentary":
                return 1.2;
            case "轻度活动":
            case "light":
                return 1.375;
            case "中度活动":
            case "moderate":
                return 1.55;
            case "高度活动":
            case "active":
                return 1.725;
            case "极高度活动":
            case "very-active":
                return 1.9;
            default:
                return 1.55; // 默认中度活动
        }
    }
}