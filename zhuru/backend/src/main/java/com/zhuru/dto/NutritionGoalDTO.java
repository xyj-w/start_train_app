package com.zhuru.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * 营养目标数据传输对象
 */
@Data
public class NutritionGoalDTO {

    /**
     * 营养目标ID
     */
    private Long id;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 蛋白质比例 (%)
     */
    @PositiveOrZero(message = "蛋白质比例不能小于0")
    @NotNull(message = "蛋白质比例不能为空")
    private Integer proteinRatio;

    /**
     * 碳水比例 (%)
     */
    @PositiveOrZero(message = "碳水比例不能小于0")
    @NotNull(message = "碳水比例不能为空")
    private Integer carbsRatio;

    /**
     * 脂肪比例 (%)
     */
    @PositiveOrZero(message = "脂肪比例不能小于0")
    @NotNull(message = "脂肪比例不能为空")
    private Integer fatRatio;
}