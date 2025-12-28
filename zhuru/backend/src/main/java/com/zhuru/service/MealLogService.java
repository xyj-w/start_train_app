package com.zhuru.service;

import com.zhuru.entity.MealLog;

import java.util.List;

/**
 * 餐食记录服务接口
 */
public interface MealLogService {

    /**
     * 根据用户ID和记录日期查询餐食记录
     */
    List<MealLog> getMealLogsByUserIdAndDate(Long userId, String recordDate);

    /**
     * 保存或更新餐食记录
     */
    MealLog saveOrUpdateMealLog(MealLog mealLog);

    /**
     * 根据ID删除餐食记录
     */
    void deleteMealLogById(Long id);
}