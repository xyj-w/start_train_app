package com.zhuru.controller;

import com.zhuru.common.ApiResponse;
import com.zhuru.dto.MealLogDTO;
import com.zhuru.entity.MealLog;
import com.zhuru.service.MealLogService;
import com.zhuru.util.BeanConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 餐食记录Controller
 */
@RestController
@RequestMapping("/api/meal")
public class MealLogController {

    @Autowired
    private MealLogService mealLogService;

    /**
     * 根据用户ID和记录日期查询餐食记录
     */
    @GetMapping("/log/{userId}")
    public ApiResponse<List<MealLogDTO>> getMealLogs(@PathVariable Long userId, @RequestParam String date) {
        List<MealLog> logs = mealLogService.getMealLogsByUserIdAndDate(userId, date);
        List<MealLogDTO> logDTOs = BeanConvertUtil.convertList(logs, MealLogDTO.class);
        return ApiResponse.success(logDTOs);
    }

    /**
     * 保存餐食记录
     */
    @PostMapping("/log")
    public ApiResponse<MealLogDTO> saveMealLog(@Valid @RequestBody MealLogDTO logDTO) {
        MealLog log = BeanConvertUtil.convert(logDTO, MealLog.class);
        MealLog savedLog = mealLogService.saveOrUpdateMealLog(log);
        MealLogDTO savedLogDTO = BeanConvertUtil.convert(savedLog, MealLogDTO.class);
        return ApiResponse.success(savedLogDTO);
    }

    /**
     * 更新餐食记录
     */
    @PutMapping("/log/{id}")
    public ApiResponse<MealLogDTO> updateMealLog(@PathVariable Long id, @Valid @RequestBody MealLogDTO logDTO) {
        logDTO.setId(id);
        MealLog log = BeanConvertUtil.convert(logDTO, MealLog.class);
        MealLog savedLog = mealLogService.saveOrUpdateMealLog(log);
        MealLogDTO savedLogDTO = BeanConvertUtil.convert(savedLog, MealLogDTO.class);
        return ApiResponse.success(savedLogDTO);
    }

    /**
     * 根据ID删除餐食记录
     */
    @DeleteMapping("/log/{id}")
    public ApiResponse<Void> deleteMealLog(@PathVariable Long id) {
        mealLogService.deleteMealLogById(id);
        return ApiResponse.success();
    }
}