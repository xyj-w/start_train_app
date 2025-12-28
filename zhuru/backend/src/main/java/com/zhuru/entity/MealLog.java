package com.zhuru.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.io.Serializable;

/**
 * 餐食记录实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "meal_log")
public class MealLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 餐食记录ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 记录日期（YYYY-MM-DD）
     */
    private String recordDate;

    /**
     * 餐食时间（早餐、午餐、晚餐等）
     */
    private String mealTime;

    /**
     * 餐食具体时间（HH:MM）
     */
    private String timeDetail;

    /**
     * 餐食内容
     */
    private String content;

    /**
     * 蛋白质含量 (g)
     */
    private Double protein;

    /**
     * 碳水化合物含量 (g)
     */
    private Double carbs;

    /**
     * 脂肪含量 (g)
     */
    private Double fat;

    /**
     * 热量 (大卡)
     */
    private Integer calories;

    /**
     * 备注信息
     */
    private String notes;

    /**
     * 排序顺序
     */
    private Integer sortOrder;
}