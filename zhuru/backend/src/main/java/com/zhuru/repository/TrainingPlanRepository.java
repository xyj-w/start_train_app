package com.zhuru.repository;

import com.zhuru.entity.TrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 训练计划Repository接口
 */
@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long> {

    /**
     * 根据用户ID查询未删除的训练计划
     */
    List<TrainingPlan> findByUserIdAndDeletedFalse(Long userId);

    /**
     * 根据用户ID和训练日查询未删除的训练计划，并按排序序号排序
     */
    List<TrainingPlan> findByUserIdAndDayOfWeekAndDeletedFalseOrderBySortOrderAsc(Long userId, String dayOfWeek);
}