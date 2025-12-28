package com.zhuru.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * 训练计划数据传输对象
 */
@Data
public class TrainingPlanDTO {

    /**
     * 训练计划ID
     */
    private Long id;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 训练日（周一至周日）
     */
    @NotBlank(message = "训练日不能为空")
    private String dayOfWeek;

    /**
     * 训练动作名称
     */
    @NotBlank(message = "训练动作名称不能为空")
    private String exerciseName;
    
    /**
     * 训练动作名称（前端兼容字段）
     */
    private String name;

    /**
     * 组数
     */
    @Positive(message = "组数必须大于0")
    @NotNull(message = "组数不能为空")
    private Integer sets;

    /**
     * 次数
     */
    @NotBlank(message = "次数不能为空")
    private String reps;

    /**
     * 重量
     */
    private String weight;

    /**
     * 动作要点
     */
    private String tips;

    /**
     * 排序序号
     */
    @PositiveOrZero(message = "排序序号不能小于0")
    private Integer sortOrder;
}