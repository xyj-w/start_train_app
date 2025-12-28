package com.zhuru.util;

import com.zhuru.entity.MealLog;
import com.zhuru.entity.NutritionGoal;
import com.zhuru.entity.TrainingPlan;
import com.zhuru.entity.User;
import com.zhuru.repository.MealLogRepository;
import com.zhuru.repository.NutritionGoalRepository;
import com.zhuru.repository.TrainingPlanRepository;
import com.zhuru.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据初始化工具类
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NutritionGoalRepository nutritionGoalRepository;

    @Autowired
    private TrainingPlanRepository trainingPlanRepository;

    @Autowired
    private MealLogRepository mealLogRepository;

    private static final Long CURRENT_TIME = System.currentTimeMillis();
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        System.out.println("========== 开始初始化模拟数据 ==========");
        
        // 清空现有数据（开发环境下可以这样做，生产环境不要使用）
        System.out.println("正在清空现有数据...");
        mealLogRepository.deleteAll();
        trainingPlanRepository.deleteAll();
        nutritionGoalRepository.deleteAll();
        userRepository.deleteAll();
        System.out.println("清空现有数据完成！");

        // 创建测试用户
        System.out.println("正在创建测试用户...");
        User user = createTestUser();
        User savedUser = userRepository.save(user);
        System.out.println("创建用户完成！用户ID: " + savedUser.getId());
        System.out.println("用户信息: 身高=" + savedUser.getHeight() + "cm, 体重=" + savedUser.getWeight() + "kg");

        // 创建营养目标
        System.out.println("正在创建营养目标...");
        NutritionGoal nutritionGoal = createNutritionGoal(savedUser.getId());
        NutritionGoal savedGoal = nutritionGoalRepository.save(nutritionGoal);
        System.out.println("创建营养目标完成！目标ID: " + savedGoal.getId());

        // 创建训练计划
        System.out.println("正在创建训练计划...");
        List<TrainingPlan> trainingPlans = createTrainingPlans(savedUser.getId());
        List<TrainingPlan> savedPlans = trainingPlanRepository.saveAll(trainingPlans);
        System.out.println("创建训练计划完成！共创建 " + savedPlans.size() + " 条训练计划");

        // 创建餐食记录
        System.out.println("正在创建餐食记录...");
        List<MealLog> mealLogs = createMealLogs(savedUser.getId());
        List<MealLog> savedLogs = mealLogRepository.saveAll(mealLogs);
        System.out.println("创建餐食记录完成！共创建 " + savedLogs.size() + " 条餐食记录");

        System.out.println("========== 模拟数据初始化完成 ==========");
    }

    /**
     * 创建测试用户
     */
    private User createTestUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setHeight(175.0);
        user.setWeight(70.0);
        user.setBmi(22.9);
        user.setGender("male");
        user.setAge(28);
        user.setTrainingIntensity("moderate");
        user.setBmr(1750);
        user.setTdee(2713);
        user.setTrainCalories(2713);
        user.setRestCalories(2442);
        user.setCreatedAt(CURRENT_TIME);
        user.setUpdatedAt(CURRENT_TIME);
        user.setDeleted(false);
        return user;
    }

    /**
     * 创建营养目标
     */
    private NutritionGoal createNutritionGoal(Long userId) {
        NutritionGoal nutritionGoal = new NutritionGoal();
        nutritionGoal.setUserId(userId);
        nutritionGoal.setProteinRatio(30);
        nutritionGoal.setCarbsRatio(50);
        nutritionGoal.setFatRatio(20);
        nutritionGoal.setCreatedAt(CURRENT_TIME);
        nutritionGoal.setUpdatedAt(CURRENT_TIME);
        nutritionGoal.setDeleted(false);
        return nutritionGoal;
    }

    /**
     * 创建训练计划
     */
    private List<TrainingPlan> createTrainingPlans(Long userId) {
        List<TrainingPlan> trainingPlans = new ArrayList<>();

        // 周一：胸部训练
        trainingPlans.add(createTrainingPlan(userId, "周一", "杠铃卧推", 4, "8-12", "60kg", "保持核心收紧，缓慢下放杠铃", 1));
        trainingPlans.add(createTrainingPlan(userId, "周一", "哑铃飞鸟", 3, "12-15", "15kg", "感受胸部肌肉的拉伸和收缩", 2));
        trainingPlans.add(createTrainingPlan(userId, "周一", "俯卧撑", 3, "15-20", "自重", "保持身体呈直线", 3));

        // 周二：背部训练
        trainingPlans.add(createTrainingPlan(userId, "周二", "引体向上", 3, "8-12", "自重", "感受背部肌肉的收缩", 1));
        trainingPlans.add(createTrainingPlan(userId, "周二", "杠铃划船", 4, "8-10", "70kg", "保持背部挺直", 2));
        trainingPlans.add(createTrainingPlan(userId, "周二", "高位下拉", 3, "12-15", "40kg", "下拉至锁骨位置", 3));

        // 周三：肩部训练
        trainingPlans.add(createTrainingPlan(userId, "周三", "坐姿哑铃推举", 4, "8-10", "20kg", "保持背部挺直", 1));
        trainingPlans.add(createTrainingPlan(userId, "周三", "侧平举", 3, "12-15", "10kg", "缓慢抬起，感受肩部外侧肌肉", 2));
        trainingPlans.add(createTrainingPlan(userId, "周三", "前平举", 3, "12-15", "10kg", "缓慢抬起，感受肩部前侧肌肉", 3));

        // 周四：手臂训练
        trainingPlans.add(createTrainingPlan(userId, "周四", "杠铃弯举", 4, "8-10", "30kg", "保持肘部固定", 1));
        trainingPlans.add(createTrainingPlan(userId, "周四", "三头肌下压", 3, "12-15", "40kg", "保持肘部固定", 2));
        trainingPlans.add(createTrainingPlan(userId, "周四", "哑铃锤式弯举", 3, "10-12", "15kg", "保持手腕固定", 3));

        // 周五：腿部训练
        trainingPlans.add(createTrainingPlan(userId, "周五", "杠铃深蹲", 4, "8-10", "80kg", "保持背部挺直，膝盖不超过脚尖", 1));
        trainingPlans.add(createTrainingPlan(userId, "周五", "硬拉", 3, "6-8", "90kg", "保持背部挺直，缓慢抬起", 2));
        trainingPlans.add(createTrainingPlan(userId, "周五", "腿举", 3, "12-15", "150kg", "膝盖不超过脚尖", 3));

        // 周六：全身训练
        trainingPlans.add(createTrainingPlan(userId, "周六", "波比跳", 3, "15-20", "自重", "快速完成，保持节奏", 1));
        trainingPlans.add(createTrainingPlan(userId, "周六", "哑铃箭步蹲", 3, "10-12", "15kg", "保持身体平衡", 2));
        trainingPlans.add(createTrainingPlan(userId, "周六", "平板支撑", 3, "60秒", "自重", "保持核心收紧", 3));

        // 周日：休息日
        // 休息日可以不添加任何训练动作

        return trainingPlans;
    }

    /**
     * 创建单个训练计划
     */
    private TrainingPlan createTrainingPlan(Long userId, String dayOfWeek, String exerciseName, int sets, String reps, String weight, String tips, int sortOrder) {
        TrainingPlan trainingPlan = new TrainingPlan();
        trainingPlan.setUserId(userId);
        trainingPlan.setDayOfWeek(dayOfWeek);
        trainingPlan.setExerciseName(exerciseName);
        trainingPlan.setSets(sets);
        trainingPlan.setReps(reps);
        trainingPlan.setWeight(weight);
        trainingPlan.setTips(tips);
        trainingPlan.setSortOrder(sortOrder);
        trainingPlan.setCreatedAt(CURRENT_TIME);
        trainingPlan.setUpdatedAt(CURRENT_TIME);
        trainingPlan.setDeleted(false);
        return trainingPlan;
    }

    /**
     * 创建餐食记录
     */
    private List<MealLog> createMealLogs(Long userId) {
        List<MealLog> mealLogs = new ArrayList<>();

        // 早餐
        mealLogs.add(createMealLog(userId, "2025-12-28", "早餐", "07:30", "燕麦粥 + 鸡蛋2个 + 香蕉", 25.0, 60.0, 10.0, 400, "营养均衡的早餐", 1));

        // 午餐
        mealLogs.add(createMealLog(userId, "2025-12-28", "午餐", "12:00", "鸡胸肉 + 糙米饭 + 西兰花", 30.0, 50.0, 15.0, 450, "高蛋白午餐", 2));

        // 晚餐
        mealLogs.add(createMealLog(userId, "2025-12-28", "晚餐", "18:30", "鱼 + 红薯 + 青菜", 25.0, 40.0, 15.0, 400, "低脂肪晚餐", 3));

        return mealLogs;
    }

    /**
     * 创建单个餐食记录
     */
    private MealLog createMealLog(Long userId, String recordDate, String mealTime, String timeDetail, String content, double protein, double carbs, double fat, int calories, String notes, int sortOrder) {
        MealLog mealLog = new MealLog();
        mealLog.setUserId(userId);
        mealLog.setRecordDate(recordDate);
        mealLog.setMealTime(mealTime);
        mealLog.setTimeDetail(timeDetail);
        mealLog.setContent(content);
        mealLog.setProtein(protein);
        mealLog.setCarbs(carbs);
        mealLog.setFat(fat);
        mealLog.setCalories(calories);
        mealLog.setNotes(notes);
        mealLog.setSortOrder(sortOrder);
        mealLog.setCreatedAt(CURRENT_TIME);
        mealLog.setUpdatedAt(CURRENT_TIME);
        mealLog.setDeleted(false);
        return mealLog;
    }
}