package com.zhuru.service;

import com.zhuru.entity.NutritionGoal;

/**
 * 营养目标服务接口
 */
public interface NutritionGoalService {

    /**
     * 根据用户ID查询营养目标
     */
    NutritionGoal getNutritionGoalByUserId(Long userId);

    /**
     * 保存或更新营养目标
     */
    NutritionGoal saveOrUpdateNutritionGoal(NutritionGoal nutritionGoal);
}