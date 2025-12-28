package com.zhuru.controller;

import com.zhuru.common.ApiResponse;
import com.zhuru.dto.NutritionGoalDTO;
import com.zhuru.entity.NutritionGoal;
import com.zhuru.service.NutritionGoalService;
import com.zhuru.util.BeanConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 营养目标Controller
 */
@RestController
@RequestMapping("/api/nutrition/goal")
public class NutritionGoalController {

    @Autowired
    private NutritionGoalService nutritionGoalService;

    /**
     * 根据用户ID获取营养目标
     */
    @GetMapping("/user/{userId}")
    public ApiResponse<NutritionGoalDTO> getNutritionGoal(@PathVariable Long userId) {
        NutritionGoal goal = nutritionGoalService.getNutritionGoalByUserId(userId);
        if (goal == null) {
            // 如果没有找到营养目标，返回成功响应但数据为null
            return ApiResponse.success(null);
        }
        NutritionGoalDTO goalDTO = BeanConvertUtil.convert(goal, NutritionGoalDTO.class);
        return ApiResponse.success(goalDTO);
    }

    /**
     * 保存营养目标
     */
    @PostMapping
    public ApiResponse<NutritionGoalDTO> saveNutritionGoal(@Valid @RequestBody NutritionGoalDTO goalDTO) {
        NutritionGoal goal = BeanConvertUtil.convert(goalDTO, NutritionGoal.class);
        NutritionGoal savedGoal = nutritionGoalService.saveOrUpdateNutritionGoal(goal);
        NutritionGoalDTO savedGoalDTO = BeanConvertUtil.convert(savedGoal, NutritionGoalDTO.class);
        return ApiResponse.success(savedGoalDTO);
    }
    
    /**
     * 更新营养目标
     */
    @PutMapping("/{id}")
    public ApiResponse<NutritionGoalDTO> updateNutritionGoal(@PathVariable Long id, @Valid @RequestBody NutritionGoalDTO goalDTO) {
        goalDTO.setId(id);
        NutritionGoal goal = BeanConvertUtil.convert(goalDTO, NutritionGoal.class);
        NutritionGoal savedGoal = nutritionGoalService.saveOrUpdateNutritionGoal(goal);
        NutritionGoalDTO savedGoalDTO = BeanConvertUtil.convert(savedGoal, NutritionGoalDTO.class);
        return ApiResponse.success(savedGoalDTO);
    }
}