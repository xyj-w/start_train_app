package com.zhuru.repository;

import com.zhuru.entity.NutritionGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 营养目标Repository接口
 */
@Repository
public interface NutritionGoalRepository extends JpaRepository<NutritionGoal, Long> {

    /**
     * 根据用户ID查询未删除的营养目标
     */
    List<NutritionGoal> findByUserIdAndDeletedFalse(Long userId);
}