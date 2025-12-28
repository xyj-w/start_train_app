package com.zhuru.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.io.Serializable;

/**
 * 训练计划实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "training_plan")
public class TrainingPlan extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 训练计划ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 训练日（周一至周日）
     */
    private String dayOfWeek;

    /**
     * 训练动作名称
     */
    private String exerciseName;

    /**
     * 组数
     */
    private Integer sets;

    /**
     * 次数
     */
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
    private Integer sortOrder;
}