package com.zhuru.service.impl;

import com.zhuru.entity.TrainingPlan;
import com.zhuru.repository.TrainingPlanRepository;
import com.zhuru.service.TrainingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 训练计划服务实现类
 */
@Service
@Transactional
public class TrainingPlanServiceImpl implements TrainingPlanService {

    @Autowired
    private TrainingPlanRepository trainingPlanRepository;

    /**
     * 根据用户ID查询所有训练计划，按训练日分组
     */
    @Override
    public Map<String, List<TrainingPlan>> getTrainingPlanByUserId(Long userId) {
        List<TrainingPlan> allPlans = trainingPlanRepository.findByUserIdAndDeletedFalse(userId);
        Map<String, List<TrainingPlan>> plansByDay = new HashMap<>();
        
        // 按训练日分组
        for (TrainingPlan plan : allPlans) {
            String dayOfWeek = plan.getDayOfWeek();
            plansByDay.computeIfAbsent(dayOfWeek, k -> trainingPlanRepository.findByUserIdAndDayOfWeekAndDeletedFalseOrderBySortOrderAsc(userId, dayOfWeek));
        }
        
        return plansByDay;
    }

    /**
     * 根据用户ID和训练日查询训练计划
     */
    @Override
    public List<TrainingPlan> getTrainingPlanByUserIdAndDay(Long userId, String dayOfWeek) {
        return trainingPlanRepository.findByUserIdAndDayOfWeekAndDeletedFalseOrderBySortOrderAsc(userId, dayOfWeek);
    }

    /**
     * 保存或更新训练计划
     */
    @Override
    public TrainingPlan saveOrUpdateTrainingPlan(TrainingPlan trainingPlan) {
        Long currentTime = System.currentTimeMillis();
        if (trainingPlan.getId() == null) {
            // 新增训练计划
            trainingPlan.setCreatedAt(currentTime);
        }
        trainingPlan.setUpdatedAt(currentTime);
        return trainingPlanRepository.save(trainingPlan);
    }

    /**
     * 根据ID删除训练计划（软删除）
     */
    @Override
    public void deleteTrainingPlanById(Long id) {
        TrainingPlan trainingPlan = trainingPlanRepository.findById(id).orElse(null);
        if (trainingPlan != null) {
            trainingPlan.setDeleted(true);
            trainingPlan.setUpdatedAt(System.currentTimeMillis());
            trainingPlanRepository.save(trainingPlan);
        }
    }
}