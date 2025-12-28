package com.zhuru.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 餐食记录数据传输对象
 */
@Data
public class MealLogDTO {

    /**
     * 餐食记录ID
     */
    private Long id;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 记录日期（YYYY-MM-DD）
     */
    @NotBlank(message = "记录日期不能为空")
    private String recordDate;

    /**
     * 餐食时间（早餐、午餐、晚餐等）
     */
    @NotBlank(message = "餐食时间不能为空")
    private String mealTime;

    /**
     * 餐食具体时间（HH:MM）
     */
    @NotBlank(message = "餐食具体时间不能为空")
    private String timeDetail;

    /**
     * 餐食内容
     */
    @NotBlank(message = "餐食内容不能为空")
    private String content;

    /**
     * 蛋白质含量 (g)
     */
    @Positive(message = "蛋白质含量必须大于0")
    private Double protein;

    /**
     * 碳水化合物含量 (g)
     */
    @Positive(message = "碳水化合物含量必须大于0")
    private Double carbs;

    /**
     * 脂肪含量 (g)
     */
    @Positive(message = "脂肪含量必须大于0")
    private Double fat;

    /**
     * 热量 (大卡)
     */
    @Positive(message = "热量必须大于0")
    private Integer calories;

    /**
     * 备注信息
     */
    private String notes;
}