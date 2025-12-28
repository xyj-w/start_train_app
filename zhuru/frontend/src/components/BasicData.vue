<template>
  <div class="card">
    <div class="card-header">
      <div class="card-title">
        <i class="fas fa-calculator"></i>
        <span>基础数据与计算</span>
      </div>
    </div>
    
    <div class="input-group">
      <div class="input-label">
        <i class="fas fa-ruler-vertical"></i>
        <span>身高 (cm)</span>
      </div>
      <input type="number" v-model.number="userData.height" class="elegant-input" min="100" max="220">
    </div>
    
    <div class="input-group">
      <div class="input-label">
        <i class="fas fa-weight-scale"></i>
        <span>体重 (kg)</span>
      </div>
      <input type="number" v-model.number="userData.weight" class="elegant-input" min="30" max="150">
    </div>
    
    <div class="input-group">
      <div class="input-label">
        <i class="fas fa-venus-mars"></i>
        <span>性别</span>
      </div>
      <select v-model="userData.gender" class="elegant-input">
        <option value="male">男</option>
        <option value="female">女</option>
      </select>
    </div>
    
    <div class="input-group">
      <div class="input-label">
        <i class="fas fa-birthday-cake"></i>
        <span>年龄</span>
      </div>
      <input type="number" v-model.number="userData.age" class="elegant-input" min="18" max="70">
    </div>
    
    <div class="input-group">
      <div class="input-label">
        <i class="fas fa-fire"></i>
        <span>训练强度</span>
      </div>
      <select v-model="userData.trainingIntensity" class="elegant-input">
        <option value="sedentary">久坐不动</option>
        <option value="light">轻度活动（每周1-3次）</option>
        <option value="moderate">中度活动（每周3-5次）</option>
        <option value="active">高度活动（每周6-7次）</option>
        <option value="very-active">非常活跃（体力工作或高强度训练）</option>
      </select>
    </div>
    
    <div class="input-group">
      <div class="input-label">
        <i class="fas fa-bullseye"></i>
        <span>BMI指数</span>
      </div>
      <input type="number" v-model.number="userData.bmi" class="elegant-input" step="0.1" readonly>
    </div>
    
    <div class="input-group">
      <div class="input-label">
        <i class="fas fa-heartbeat"></i>
        <span>基础代谢率 (BMR)</span>
      </div>
      <input type="number" v-model.number="userData.bmr" class="elegant-input" readonly>
    </div>
    
    <div class="input-group">
      <div class="input-label">
        <i class="fas fa-running"></i>
        <span>总消耗热量 (TDEE)</span>
      </div>
      <input type="number" v-model.number="userData.tdee" class="elegant-input" readonly>
    </div>
    
    <div class="input-group">
      <div class="input-label">
        <i class="fas fa-fire"></i>
        <span>训练日摄入热量 (大卡)</span>
      </div>
      <input type="number" v-model.number="userData.trainCalories" class="elegant-input" min="1000" max="3000">
    </div>
    
    <div class="input-group">
      <div class="input-label">
        <i class="fas fa-bed"></i>
        <span>休息日摄入热量 (大卡)</span>
      </div>
      <input type="number" v-model.number="userData.restCalories" class="elegant-input" min="1000" max="3000">
    </div>
    
    <button class="btn btn-primary" style="margin-top: 20px; width: 100%;" @click="saveUserDataAndRecalculate">
      <i class="fas fa-sync-alt"></i>
      重新计算营养素
    </button>
    
    <div id="macroResults" v-if="showMacroResults" style="margin-top: 25px;">
      <div class="macro-visual">
        <div class="macro-bars">
          <div class="macro-protein" :style="{ width: (nutritionGoals?.proteinRatio || 25) + '%' }"></div>
          <div class="macro-carbs" :style="{ width: (nutritionGoals?.carbsRatio || 50) + '%' }"></div>
          <div class="macro-fat" :style="{ width: (nutritionGoals?.fatRatio || 25) + '%' }"></div>
        </div>
        <div class="macro-labels">
          <span>蛋白质 <span>{{ nutritionGoals?.proteinRatio || 25 }}%</span></span>
          <span>碳水 <span>{{ nutritionGoals?.carbsRatio || 50 }}%</span></span>
          <span>脂肪 <span>{{ nutritionGoals?.fatRatio || 25 }}%</span></span>
        </div>
      </div>
      
      <div class="stats-grid">
        <div class="stat-item">
          <div class="stat-value">{{ trainMacro.protein }}</div>
          <div class="stat-label">训练日蛋白质(g)</div>
        </div>
        <div class="stat-item">
          <div class="stat-value">{{ trainMacro.carbs }}</div>
          <div class="stat-label">训练日碳水(g)</div>
        </div>
        <div class="stat-item">
          <div class="stat-value">{{ trainMacro.fat }}</div>
          <div class="stat-label">训练日脂肪(g)</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BasicData',
  props: {
    userData: {
      type: Object,
      required: true,
      default: () => ({
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
      }),
      validator: (value) => {
        return ['height', 'weight', 'bmi', 'gender', 'age', 'trainingIntensity', 'bmr', 'tdee', 'trainCalories', 'restCalories'].every(key => key in value);
      }
    },
    nutritionGoals: {
      type: Object,
      required: true,
      default: () => ({
        proteinRatio: 25,
        carbsRatio: 50,
        fatRatio: 25
      }),
      validator: (value) => {
        return ['proteinRatio', 'carbsRatio', 'fatRatio'].every(key => key in value);
      }
    },
    trainMacro: {
      type: Object,
      required: true,
      default: () => ({
        protein: 100,
        carbs: 200,
        fat: 50
      }),
      validator: (value) => {
        return ['protein', 'carbs', 'fat'].every(key => key in value);
      }
    },
    showMacroResults: {
      type: Boolean,
      default: true
    }
  },

  methods: {
    saveUserDataAndRecalculate() {
      this.$emit('save-recalculate');
    }
  }
}
</script>