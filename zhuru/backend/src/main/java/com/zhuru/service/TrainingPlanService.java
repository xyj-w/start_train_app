package com.zhuru.service;

import com.zhuru.entity.TrainingPlan;

import java.util.List;
import java.util.Map;

/**
 * 训练计划服务接口
 */
public interface TrainingPlanService {

    /**
     * 根据用户ID查询所有训练计划，按训练日分组
     */
    Map<String, List<TrainingPlan>> getTrainingPlanByUserId(Long userId);

    /**
     * 根据用户ID和训练日查询训练计划
     */
    List<TrainingPlan> getTrainingPlanByUserIdAndDay(Long userId, String dayOfWeek);

    /**
     * 保存或更新训练计划
     */
    TrainingPlan saveOrUpdateTrainingPlan(TrainingPlan trainingPlan);

    /**
     * 根据ID删除训练计划
     */
    void deleteTrainingPlanById(Long id);
}