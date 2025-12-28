package com.zhuru.controller;

import com.zhuru.common.ApiResponse;
import com.zhuru.dto.TrainingPlanDTO;
import com.zhuru.entity.TrainingPlan;
import com.zhuru.service.TrainingPlanService;
import com.zhuru.util.BeanConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 训练计划Controller
 */
@RestController
@RequestMapping("/api/training/plan")
public class TrainingPlanController {

    @Autowired
    private TrainingPlanService trainingPlanService;

    /**
     * 根据用户ID查询所有训练计划，按训练日分组
     */
    @GetMapping("/user/{userId}")
    public ApiResponse<Map<String, List<TrainingPlanDTO>>> getTrainingPlan(@PathVariable Long userId) {
        Map<String, List<TrainingPlan>> plans = trainingPlanService.getTrainingPlanByUserId(userId);
        Map<String, List<TrainingPlanDTO>> planDTOs = new HashMap<>();
        
        // 转换并处理前端兼容字段
        for (Map.Entry<String, List<TrainingPlan>> entry : plans.entrySet()) {
            List<TrainingPlanDTO> dtos = entry.getValue().stream()
                    .map(plan -> {
                        TrainingPlanDTO dto = BeanConvertUtil.convert(plan, TrainingPlanDTO.class);
                        // 设置前端兼容的name字段
                        dto.setName(plan.getExerciseName());
                        return dto;
                    })
                    .collect(Collectors.toList());
            planDTOs.put(entry.getKey(), dtos);
        }
        
        return ApiResponse.success(planDTOs);
    }

    /**
     * 根据用户ID和训练日查询训练计划
     */
    @GetMapping("/user/{userId}/{dayOfWeek}")
    public ApiResponse<List<TrainingPlanDTO>> getTrainingPlanByDay(@PathVariable Long userId, @PathVariable String dayOfWeek) {
        List<TrainingPlan> plans = trainingPlanService.getTrainingPlanByUserIdAndDay(userId, dayOfWeek);
        List<TrainingPlanDTO> planDTOs = plans.stream()
                .map(plan -> {
                    TrainingPlanDTO dto = BeanConvertUtil.convert(plan, TrainingPlanDTO.class);
                    // 设置前端兼容的name字段
                    dto.setName(plan.getExerciseName());
                    return dto;
                })
                .collect(Collectors.toList());
        return ApiResponse.success(planDTOs);
    }

    /**
     * 保存训练计划
     */
    @PostMapping
    public ApiResponse<TrainingPlanDTO> saveTrainingPlan(@Valid @RequestBody TrainingPlanDTO planDTO) {
        TrainingPlan plan = BeanConvertUtil.convert(planDTO, TrainingPlan.class);
        TrainingPlan savedPlan = trainingPlanService.saveOrUpdateTrainingPlan(plan);
        TrainingPlanDTO savedPlanDTO = BeanConvertUtil.convert(savedPlan, TrainingPlanDTO.class);
        return ApiResponse.success(savedPlanDTO);
    }

    /**
     * 更新训练计划
     */
    @PutMapping("/{id}")
    public ApiResponse<TrainingPlanDTO> updateTrainingPlan(@PathVariable Long id, @Valid @RequestBody TrainingPlanDTO planDTO) {
        planDTO.setId(id);
        TrainingPlan plan = BeanConvertUtil.convert(planDTO, TrainingPlan.class);
        TrainingPlan savedPlan = trainingPlanService.saveOrUpdateTrainingPlan(plan);
        TrainingPlanDTO savedPlanDTO = BeanConvertUtil.convert(savedPlan, TrainingPlanDTO.class);
        return ApiResponse.success(savedPlanDTO);
    }

    /**
     * 根据ID删除训练计划
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTrainingPlan(@PathVariable Long id) {
        trainingPlanService.deleteTrainingPlanById(id);
        return ApiResponse.success();
    }
}