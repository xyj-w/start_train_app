package com.zhuru.service.impl;

import com.zhuru.entity.MealLog;
import com.zhuru.repository.MealLogRepository;
import com.zhuru.service.MealLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 餐食记录服务实现类
 */
@Service
@Transactional
public class MealLogServiceImpl implements MealLogService {

    @Autowired
    private MealLogRepository mealLogRepository;

    /**
     * 根据用户ID和记录日期查询餐食记录
     */
    @Override
    public List<MealLog> getMealLogsByUserIdAndDate(Long userId, String recordDate) {
        return mealLogRepository.findByUserIdAndRecordDateAndDeletedFalse(userId, recordDate);
    }

    /**
     * 保存或更新餐食记录
     */
    @Override
    public MealLog saveOrUpdateMealLog(MealLog mealLog) {
        return mealLogRepository.save(mealLog);
    }

    /**
     * 根据ID删除餐食记录（软删除）
     */
    @Override
    public void deleteMealLogById(Long id) {
        MealLog mealLog = mealLogRepository.findById(id).orElse(null);
        if (mealLog != null) {
            mealLog.setDeleted(true);
            mealLogRepository.save(mealLog);
        }
    }
}