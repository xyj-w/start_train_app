package com.zhuru.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * 密码
     */
    @Column(nullable = false)
    private String password;

    /**
     * 身高 (cm)
     */
    private Double height;

    /**
     * 体重 (kg)
     */
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