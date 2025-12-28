package com.zhuru.service.impl;

import com.zhuru.entity.NutritionGoal;
import com.zhuru.repository.NutritionGoalRepository;
import com.zhuru.service.NutritionGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 营养目标服务实现类
 */
@Service
@Transactional
public class NutritionGoalServiceImpl implements NutritionGoalService {

    @Autowired
    private NutritionGoalRepository nutritionGoalRepository;

    /**
     * 根据用户ID查询营养目标
     */
    @Override
    public NutritionGoal getNutritionGoalByUserId(Long userId) {
        List<NutritionGoal> goals = nutritionGoalRepository.findByUserIdAndDeletedFalse(userId);
        return goals != null && !goals.isEmpty() ? goals.get(0) : null;
    }

    /**
     * 保存或更新营养目标
     */
    @Override
    public NutritionGoal saveOrUpdateNutritionGoal(NutritionGoal nutritionGoal) {
        Long currentTime = System.currentTimeMillis();
        if (nutritionGoal.getId() == null) {
            // 新增营养目标
            nutritionGoal.setCreatedAt(currentTime);
        }
        nutritionGoal.setUpdatedAt(currentTime);
        return nutritionGoalRepository.save(nutritionGoal);
    }
}