package com.zhuru.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * 用户数据传输对象
 */
@Data
public class UserDTO {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 身高 (cm)
     */
    @Positive(message = "身高必须大于0")
    private Double height;

    /**
     * 体重 (kg)
     */
    @Positive(message = "体重必须大于0")
    private Double weight;

    /**
     * BMI指数
     */
    private Double bmi;

    /**
     * 性别
     */
    private String gender;

    /**
     * 年龄
     */
    @Positive(message = "年龄必须大于0")
    private Integer age;

    /**
     * 训练强度
     */
    private String trainingIntensity;

    /**
     * 基础代谢率
     */
    private Integer bmr;

    /**
     * 总消耗热量
     */
    private Integer tdee;

    /**
     * 训练日摄入热量 (大卡)
     */
    private Integer trainCalories;

    /**
     * 休息日摄入热量 (大卡)
     */
    private Integer restCalories;
}