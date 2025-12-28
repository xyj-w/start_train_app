package com.zhuru.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.io.Serializable;

/**
 * 营养目标实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "nutrition_goal")
public class NutritionGoal extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 营养目标ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 蛋白质比例 (%)
     */
    private Integer proteinRatio;

    /**
     * 碳水比例 (%)
     */
    private Integer carbsRatio;

    /**
     * 脂肪比例 (%)
     */
    private Integer fatRatio;
}