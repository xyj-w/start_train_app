package com.zhuru.repository;

import com.zhuru.entity.MealLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 餐食记录Repository接口
 */
@Repository
public interface MealLogRepository extends JpaRepository<MealLog, Long> {

    /**
     * 根据用户ID和记录日期查询未删除的餐食记录
     */
    List<MealLog> findByUserIdAndRecordDateAndDeletedFalse(Long userId, String recordDate);

    /**
     * 根据用户ID查询未删除的餐食记录
     */
    List<MealLog> findByUserIdAndDeletedFalse(Long userId);
}