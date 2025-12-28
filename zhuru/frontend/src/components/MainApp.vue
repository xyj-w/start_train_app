<template>
  <div>
    <div class="app-wrapper">
      <div class="container">
        <Header @logout="handleLogout" />

        <!-- 数据输入与计算结果 -->
        <div class="dashboard">
          <BasicData 
            :userData="user.data" 
            :nutritionGoals="user.nutritionGoals" 
            :trainMacro="user.trainMacro" 
            :showMacroResults="user.showMacroResults"
            @save-recalculate="saveUserDataAndRecalculate"
          />
          
          <MealLog 
            :currentDate="ui.currentDate" 
            :mealLogs="meals.logs" 
            :dailyTotal="meals.dailyTotal"
            @update:currentDate="changeDate"
            @edit-meal="editMeal"
            @delete-meal="deleteMeal"
            @open-meal-modal="openMealModal"
          />
        </div>

        <!-- 导航标签 -->
        <div class="tabs">
          <div 
            v-for="tab in TABS_CONFIG" 
            :key="tab.id"
            class="tab" 
            :class="{ active: ui.activeTab === tab.id }" 
            @click="showTab(tab.id)"
          >
            <i :class="tab.icon"></i>
            {{ tab.label }}
          </div>
        </div>

        <!-- 训练计划 -->
        <div id="training" class="tab-content" :class="{ active: ui.activeTab === TAB_IDS.TRAINING }">
          <TrainingPlan 
            :trainingPlan="training.plan" 
            :days="training.days"
            :trainingDays="user.trainingDays"
            @open-exercise-modal="openExerciseModal"
            @edit-exercise="editExercise"
            @delete-exercise="deleteExercise"
            @upload-exercise-image="uploadExerciseImage"
            @update-training-day="updateTrainingDay"
          />
        </div>

        <!-- 营养目标 -->
        <div id="nutrition" class="tab-content" :class="{ active: ui.activeTab === TAB_IDS.NUTRITION }">
          <NutritionGoal 
            :nutritionGoals="user.nutritionGoals"
            @update-nutrition-goals="handleUpdateNutritionGoals"
          />
        </div>

        <!-- 进度分析 -->
        <div id="progress" class="tab-content" :class="{ active: ui.activeTab === TAB_IDS.PROGRESS }">
          <ProgressAnalysis 
            :userData="user.data" 
            :trainMacro="user.trainMacro" 
            :dailyTotal="meals.dailyTotal"
            :calorieProgress="progress.values.calorie"
            :proteinProgress="progress.values.protein"
            :carbsProgress="progress.values.carbs"
            :calorieProgressOffset="progress.offsets.calorie"
            :proteinProgressOffset="progress.offsets.protein"
            :carbsProgressOffset="progress.offsets.carbs"
          />
        </div>
      </div>

      <!-- 浮动操作按钮 -->
      <ActionButtons 
        @export-to-excel="exportToExcel"
        @print-plan="printPlan"
        @show-help="showHelp"
      />
    </div>

    <!-- 餐食记录模态框 -->
    <MealModal 
      :visible="ui.mealModalVisible"
      :meal="ui.editingMeal"
      @save-meal="saveMeal"
      @close="closeMealModal"
    />
  </div>
</template>

<script>
import { userApi, mealLogApi, nutritionGoalApi, trainingPlanApi } from '../services/api';
import { getDateKey } from '../utils/date';
import { TAB_IDS, TABS_CONFIG } from '../constants/tabs';
import { WEEK_DAYS } from '../constants/days';
import '../styles/modal.css';
import Header from './Header.vue';
import BasicData from './BasicData.vue';
import MealLog from './MealLog.vue';
import TrainingPlan from './TrainingPlan.vue';
import NutritionGoal from './NutritionGoal.vue';
import ProgressAnalysis from './ProgressAnalysis.vue';
import ActionButtons from './ActionButtons.vue';
import MealModal from './MealModal.vue';

export default {
  components: {
    Header,
    BasicData,
    MealLog,
    TrainingPlan,
    NutritionGoal,
    ProgressAnalysis,
    ActionButtons,
    MealModal
  },
  data() {
    return {
      // 常量
      TAB_IDS,
      TABS_CONFIG,
      // 当前用户ID（从localStorage获取）
      userId: parseInt(localStorage.getItem('userId')) || 6,
      
      // 用户相关数据
      user: {
        data: {
          height: 160,
          weight: 54,
          bmi: 21.1,
          gender: 'female',
          age: 30,
          trainingIntensity: 'moderate',
          bmr: 0,
          tdee: 0,
          trainCalories: 1750,
          restCalories: 1650
        },
        // 训练日配置，true表示训练日，false表示休息日
        trainingDays: {
          '周一': true,
          '周二': true,
          '周三': true,
          '周四': true,
          '周五': true,
          '周六': false,
          '周日': false
        },
        nutritionGoals: {
          proteinRatio: 27,
          carbsRatio: 48,
          fatRatio: 25
        },
        trainMacro: {
          protein: 119,
          carbs: 193,
          fat: 49
        },
        showMacroResults: true
      },
      
      // 界面状态
      ui: {
        activeTab: TAB_IDS.TRAINING,
        currentDate: new Date(),
        mealModalVisible: false,
        editingMeal: null
      },
      
      // 训练计划
      training: {
        plan: {
          "周一": [
            { id: 1, name: "杠铃深蹲", sets: 4, reps: "6-8次", weight: "20-30kg", tips: "保持背部挺直，膝盖不超过脚尖" },
            { id: 2, name: "罗马尼亚硬拉", sets: 4, reps: "8-10次", weight: "15-25kg", tips: "感受腘绳肌拉伸" },
            { id: 3, name: "臀推", sets: 4, reps: "10-12次", weight: "30-40kg", tips: "顶峰收缩2秒" }
          ],
          "周二": [
            { id: 4, name: "杠铃卧推", sets: 4, reps: "8-10次", weight: "15-25kg", tips: "沉肩收紧肩胛" },
            { id: 5, name: "上斜哑铃卧推", sets: 3, reps: "10-12次", weight: "8-12kg/只", tips: "上胸发力感" }
          ],
          "周三": [
            { id: 6, name: "引体向上(辅助)", sets: 4, reps: "6-8次", weight: "辅助带", tips: "完全伸展到完全收缩" },
            { id: 7, name: "杠铃划船", sets: 4, reps: "8-10次", weight: "15-25kg", tips: "背部驱动" }
          ],
          "周四": [
            { id: 8, name: "哑铃肩推", sets: 4, reps: "8-10次", weight: "6-10kg/只", tips: "避免腰部反弓" },
            { id: 9, name: "侧平举", sets: 4, reps: "12-15次", weight: "3-5kg/只", tips: "小重量高质量" }
          ],
          "周五": [
            { id: 10, name: "保加利亚分腿蹲", sets: 3, reps: "10-12次/腿", weight: "8-12kg/只", tips: "控制平衡" },
            { id: 11, name: "臀桥", sets: 4, reps: "12-15次", weight: "30-40kg", tips: "顶峰暂停" }
          ],
          "周六": [],
          "周日": []
        },
        days: WEEK_DAYS
      },
      
      // 餐食记录
      meals: {
        logs: {
          [getDateKey(new Date())]: [
            { id: 1, time: "早餐", timeDetail: "08:00", content: "燕麦50g + 鸡蛋2个 + 牛奶200ml", protein: 25, carbs: 30, fat: 12, calories: 328, notes: "" },
            { id: 2, time: "午餐", timeDetail: "12:30", content: "糙米100g + 鸡胸肉150g + 西兰花200g", protein: 45, carbs: 35, fat: 8, calories: 392, notes: "" },
            { id: 3, time: "晚餐", timeDetail: "19:00", content: "三文鱼120g + 红薯150g + 蔬菜沙拉", protein: 32, carbs: 40, fat: 15, calories: 443, notes: "" }
          ]
        },
        dailyTotal: {
          calories: 0,
          protein: 0,
          carbs: 0,
          fat: 0
        }
      },
      
      // 进度分析
      progress: {
        // 进度百分比
        values: {
          calorie: 50,
          protein: 50,
          carbs: 50
        },
        // 进度圆环偏移量
        offsets: {
          calorie: 132,
          protein: 132,
          carbs: 132
        }
      }
    }
  },
  
  mounted() {
    this.loadUserData();
    this.loadNutritionGoals();
    this.loadMealLogs();
    this.loadTrainingPlan();
  },
  
  methods: {
    // 显示对应标签页
    showTab(tabId) {
      this.ui.activeTab = tabId;
    },
    
    // 加载用户数据
    loadUserData() {
      userApi.getUserInfo(this.userId)
        .then(response => {
          if (response.code === 0 && response.data) {
            this.user.data = response.data;
            this.calculateMacros();
          } else {
            console.error('获取用户数据失败:', response.message);
            // 用户不存在时，自动创建新用户
            this.createNewUser();
          }
        })
        .catch(error => {
          console.error('获取用户数据失败:', error);
          // API调用失败时，也尝试创建新用户
          this.createNewUser();
        });
    },
    
    // 创建新用户
    createNewUser() {
      // 使用当前的默认用户数据创建新用户
      const newUserData = this.user.data;
      // 调用创建用户信息的API
      userApi.createUserInfo(newUserData)
        .then(response => {
          if (response.code === 0 && response.data) {
            this.user.data = response.data;
            this.calculateMacros();
            console.log('新用户创建成功');
          } else {
            console.error('创建新用户失败:', response.message);
            this.initDefaultUserData();
          }
        })
        .catch(error => {
          console.error('创建新用户失败:', error);
          this.initDefaultUserData();
        });
    },
    
    // 初始化默认用户数据
    initDefaultUserData() {
      this.user.data = {
        height: 175,
        weight: 70,
        gender: 'male',
        age: 30,
        trainingIntensity: 'moderate',
        bmi: 0,
        bmr: 0,
        tdee: 0,
        trainCalories: 0,
        restCalories: 0
      };
      this.calculateBMI();
      this.calculateBMR();
      this.calculateTDEE();
      this.calculateMacros();
    },
    
    // 加载营养目标
    loadNutritionGoals() {
      nutritionGoalApi.getNutritionGoal(this.userId)
        .then(response => {
          if (response.code === 0 && response.data) {
            this.user.nutritionGoals = response.data;
            this.calculateMacros();
          } else if (response.code === 0 && !response.data) {
            console.warn('营养目标数据为空，使用默认值');
            // 保持默认值不变
          } else {
            console.error('获取营养目标失败:', response.message);
          }
        })
        .catch(error => {
          console.error('获取营养目标失败:', error);
        });
    },
    
    // 计算BMI
    calculateBMI() {
      const { height, weight } = this.user.data;
      if (height > 0 && weight > 0) {
        const heightInMeters = height / 100;
        this.user.data.bmi = Math.round((weight / (heightInMeters * heightInMeters)) * 10) / 10;
      }
    },
    
    // 计算BMR (基础代谢率) - 使用Mifflin-St Jeor公式
    calculateBMR() {
      const { weight, height, age, gender } = this.user.data;
      let bmr = 0;
      
      if (gender === 'male') {
        bmr = 10 * weight + 6.25 * height - 5 * age + 5;
      } else if (gender === 'female') {
        bmr = 10 * weight + 6.25 * height - 5 * age - 161;
      }
      
      this.user.data.bmr = Math.round(bmr);
    },
    
    // 计算TDEE (总消耗热量)
    calculateTDEE() {
      const { trainingIntensity, bmr } = this.user.data;
      const activityFactors = {
        'sedentary': 1.2,
        'light': 1.375,
        'moderate': 1.55,
        'active': 1.725,
        'very-active': 1.9
      };
      
      const factor = activityFactors[trainingIntensity] || 1.2;
      this.user.data.tdee = Math.round(bmr * factor);
    },
    
    // 计算训练日和休息日的热量摄入
    calculateCalories() {
      const { tdee } = this.user.data;
      
      // 训练日摄入热量 = TDEE
      this.user.data.trainCalories = tdee;
      
      // 休息日摄入热量 = TDEE * 0.9
      this.user.data.restCalories = Math.round(tdee * 0.9);
    },
    
    // 计算宏量营养素
    calculateMacros() {
      // 先计算BMI、BMR和TDEE
      this.calculateBMI();
      this.calculateBMR();
      this.calculateTDEE();
      this.calculateCalories();
      
      const trainCalories = this.user.data.trainCalories || 0;
      // 确保nutritionGoals不为null，使用默认值作为备选
      const nutritionGoals = this.user.nutritionGoals || { proteinRatio: 25, carbsRatio: 50, fatRatio: 25 };
      const proteinRatio = nutritionGoals.proteinRatio || 25;
      const carbsRatio = nutritionGoals.carbsRatio || 50;
      const fatRatio = nutritionGoals.fatRatio || 25;
      
      this.user.trainMacro.protein = Math.round(trainCalories * proteinRatio / 100 / 4);
      this.user.trainMacro.carbs = Math.round(trainCalories * carbsRatio / 100 / 4);
      this.user.trainMacro.fat = Math.round(trainCalories * fatRatio / 100 / 9);
      
      this.user.showMacroResults = true;
      this.updateProgress();
    },
    
    // 处理来自组件的营养目标更新
    handleUpdateNutritionGoals(updatedGoals) {
      if (updatedGoals) {
        this.user.nutritionGoals = updatedGoals;
        this.updateNutritionGoals();
      } else {
        console.error('更新营养目标失败：传入的数据为null');
      }
    },

    // 更新营养目标
    updateNutritionGoals() {
      nutritionGoalApi.updateNutritionGoal(this.userId, this.user.nutritionGoals)
        .then(response => {
          if (response.code === 0) {
            this.calculateMacros();
            alert('营养目标已更新！');
          } else {
            alert('更新失败: ' + response.message);
          }
        })
        .catch(error => {
          console.error('更新营养目标失败:', error);
          alert('更新失败');
        });
    },
    
    // 保存用户数据并重新计算营养素
    saveUserDataAndRecalculate() {
      userApi.updateUserInfo(this.userId, this.user.data)
        .then(response => {
          if (response.code === 0) {
            this.calculateMacros();
            alert('用户数据已保存并重新计算营养素');
          } else {
            console.error('保存用户数据失败:', response.message);
            this.calculateMacros(); // 即使保存失败，也要重新计算营养素
          }
        })
        .catch(error => {
          console.error('保存用户数据失败:', error);
          this.calculateMacros(); // 即使保存失败，也要重新计算营养素
        });
    },
    
    // 训练计划功能
    // 加载训练计划
    loadTrainingPlan() {
      trainingPlanApi.getTrainingPlan(this.userId)
        .then(response => {
          if (response.code === 0) {
            this.training.plan = response.data;
          } else {
            console.error('获取训练计划失败:', response.message);
          }
        })
        .catch(error => {
          console.error('获取训练计划失败:', error);
        });
    },

    // 打开训练动作编辑模态框
    editExercise(exerciseId) {
      console.log('编辑训练动作:', exerciseId);
      // 这里可以实现编辑模态框逻辑
      // 首先根据exerciseId查找对应的训练动作
      let exerciseToEdit = null;
      for (const day in this.training.plan) {
        exerciseToEdit = this.training.plan[day].find(exercise => exercise.id === exerciseId);
        if (exerciseToEdit) {
          break;
        }
      }
      
      if (exerciseToEdit) {
        console.log('找到训练动作:', exerciseToEdit);
        // 这里可以打开编辑模态框并填充数据
        alert(`编辑训练动作: ${exerciseToEdit.name}`);
        // 实际应用中应该打开一个模态框
      }
    },

    // 删除训练动作
    deleteExercise(exerciseId) {
      console.log('删除训练动作:', exerciseId);
      if (confirm('确定要删除这个训练动作吗？')) {
        trainingPlanApi.deleteExercise(exerciseId)
          .then(response => {
            if (response.code === 0) {
              // 删除成功后重新加载训练计划
              this.loadTrainingPlan();
              alert('训练动作已删除');
            } else {
              console.error('删除训练动作失败:', response.message);
              alert('删除失败: ' + response.message);
            }
          })
          .catch(error => {
            console.error('删除训练动作失败:', error);
            alert('删除失败');
          });
      }
    },

    // 上传训练动作图片
    uploadExerciseImage(exerciseId) {
      console.log('上传训练动作图片:', exerciseId);
      // 这里可以实现图片上传逻辑
      alert(`上传训练动作图片: ${exerciseId}`);
      // 实际应用中应该打开文件选择对话框
    },

    // 打开训练动作添加模态框
    openExerciseModal() {
      console.log('打开添加训练动作模态框');
      alert('添加训练动作');
      // 实际应用中应该打开一个模态框
    },

    changeDate(days) {
      this.ui.currentDate.setDate(this.ui.currentDate.getDate() + days);
      this.loadMealLogs();
    },
    
    // 餐食记录功能
    loadMealLogs() {
      const date = getDateKey(this.ui.currentDate);
      mealLogApi.getMealLogs(this.userId, date)
        .then(response => {
          if (response.code === 0) {
            const meals = response.data || [];
            let totalCalories = 0, totalProtein = 0, totalCarbs = 0, totalFat = 0;
            
            meals.forEach(meal => {
              totalCalories += meal.calories;
              totalProtein += meal.protein;
              totalCarbs += meal.carbs;
              totalFat += meal.fat;
            });
            
            this.meals.dailyTotal = {
              calories: totalCalories,
              protein: totalProtein,
              carbs: totalCarbs,
              fat: totalFat
            };
            
            // 保存到本地用于显示
            this.$set(this.meals.logs, date, meals);
            this.updateProgress();
          } else {
            console.error('获取餐食记录失败:', response.message);
          }
        })
        .catch(error => {
          console.error('获取餐食记录失败:', error);
          // 使用本地数据作为备用
          this.loadLocalMealLogs();
        });
    },
    
    // 使用本地餐食记录（作为API调用失败的备用）
    loadLocalMealLogs() {
      const dateKey = getDateKey(this.ui.currentDate);
      const meals = this.meals.logs[dateKey] || [];
      
      let totalCalories = 0, totalProtein = 0, totalCarbs = 0, totalFat = 0;
      
      meals.forEach(meal => {
        totalCalories += meal.calories;
        totalProtein += meal.protein;
        totalCarbs += meal.carbs;
        totalFat += meal.fat;
      });
      
      this.meals.dailyTotal = {
        calories: totalCalories,
        protein: totalProtein,
        carbs: totalCarbs,
        fat: totalFat
      };
      
      this.updateProgress();
    },
    
    // 打开编辑餐食模态框
    editMeal(mealId) {
      const dateKey = getDateKey(this.ui.currentDate);
      const meals = this.meals.logs[dateKey] || [];
      const meal = meals.find(m => m.id === mealId);
      if (meal) {
        this.ui.editingMeal = meal;
        this.ui.mealModalVisible = true;
      }
    },
    
    // 删除餐食
    deleteMeal(mealId) {
      if (confirm('确定要删除这条记录吗？')) {
        mealLogApi.deleteMealLog(mealId)
          .then(response => {
            if (response.code === 0) {
              this.loadMealLogs();
            } else {
              alert('删除失败: ' + response.message);
            }
          })
          .catch(error => {
            console.error('删除餐食记录失败:', error);
            // 使用本地删除作为备用
            const dateKey = getDateKey(this.ui.currentDate);
            this.meals.logs[dateKey] = this.meals.logs[dateKey].filter(m => m.id !== mealId);
            this.loadMealLogs();
          });
      }
    },
    
    // 打开添加餐食模态框
    openMealModal() {
      this.ui.editingMeal = null;
      this.ui.mealModalVisible = true;
    },
    
    // 关闭餐食模态框
    closeMealModal() {
      this.ui.mealModalVisible = false;
      this.ui.editingMeal = null;
    },
    
    // 保存餐食记录
    saveMeal(mealData) {
      const dateKey = getDateKey(this.ui.currentDate);
      
      // 转换餐食数据以匹配后端API要求
      const transformMealData = (data) => {
        return {
          id: data.id,
          userId: this.userId,
          recordDate: dateKey, // 使用recordDate而不是date
          mealTime: data.time,  // 使用mealTime而不是time
          timeDetail: data.timeDetail,
          content: data.content,
          calories: Math.round(data.calories),
          protein: data.protein,
          carbs: data.carbs,
          fat: data.fat,
          notes: data.notes
        };
      };
      
      const transformedData = transformMealData(mealData);
      
      if (this.ui.editingMeal) {
        // 更新现有餐食记录
        mealLogApi.updateMealLog(transformedData)
          .then(response => {
            if (response.code === 0) {
              this.loadMealLogs();
              this.closeMealModal();
            } else {
              alert('更新失败: ' + response.message);
            }
          })
          .catch(error => {
            console.error('更新餐食记录失败:', error);
            // 使用本地更新作为备用
            const meals = this.meals.logs[dateKey] || [];
            const index = meals.findIndex(m => m.id === mealData.id);
            if (index !== -1) {
              meals[index] = { ...mealData };
              this.$set(this.meals.logs, dateKey, meals);
              this.loadMealLogs();
              this.closeMealModal();
            }
          });
      } else {
        // 添加新餐食记录
        const newMeal = {
          ...transformedData,
          id: Date.now() // 临时ID，实际由后端生成
        };
        
        mealLogApi.saveMealLog(newMeal)
          .then(response => {
            if (response.code === 0) {
              this.loadMealLogs();
              this.closeMealModal();
            } else {
              alert('添加失败: ' + response.message);
            }
          })
          .catch(error => {
            console.error('添加餐食记录失败:', error);
            // 使用本地添加作为备用
            const meals = this.meals.logs[dateKey] || [];
            // 转换回前端显示格式
            const frontEndMeal = {
              ...mealData,
              id: newMeal.id,
              date: dateKey
            };
            meals.push(frontEndMeal);
            this.$set(this.meals.logs, dateKey, meals);
            this.loadMealLogs();
            this.closeMealModal();
          });
      }
    },
    
    // 打开训练动作添加模态框
    openExerciseModal() {
      // 实际项目中这里会打开添加模态框
      console.log('打开添加训练动作模态框');
    },
    
    // 更新训练日配置
    updateTrainingDay(day, isTrainingDay) {
      this.$set(this.user.trainingDays, day, isTrainingDay);
      console.log(`更新${day}为${isTrainingDay ? '训练日' : '休息日'}`);
    },
    
    // 更新进度分析
    updateProgress() {
      // 计算进度百分比
      this.progress.values.calorie = Math.min(Math.round((this.meals.dailyTotal.calories / this.user.data.trainCalories) * 100), 100);
      this.progress.values.protein = Math.min(Math.round((this.meals.dailyTotal.protein / this.user.trainMacro.protein) * 100), 100);
      this.progress.values.carbs = Math.min(Math.round((this.meals.dailyTotal.carbs / this.user.trainMacro.carbs) * 100), 100);
      
      // 计算圆环偏移量 (周长 = 2 * π * r, r = 42)
      const circumference = 2 * Math.PI * 42;
      this.progress.offsets.calorie = circumference - (this.progress.values.calorie / 100) * circumference;
      this.progress.offsets.protein = circumference - (this.progress.values.protein / 100) * circumference;
      this.progress.offsets.carbs = circumference - (this.progress.values.carbs / 100) * circumference;
    },
    
    // 导出Excel
    exportToExcel() {
      alert('导出功能开发中');
    },
    
    // 打印计划
    printPlan() {
      window.print();
    },
    
    // 显示帮助
    showHelp() {
      alert('帮助功能开发中');
    },
    
    // 退出登录
    handleLogout() {
      this.$emit('logout');
    }
  }
}
</script>

<style>
@import '../styles/index.css';
</style>