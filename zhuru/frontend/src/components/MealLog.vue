<template>
  <div class="card">
    <div class="card-header">
      <div class="card-title">
        <i class="fas fa-chart-line"></i>
        <span>今日摄入记录</span>
      </div>
      <div class="current-date">{{ formatDate(new Date()) }}</div>
    </div>
    
    <div class="date-picker">
      <button class="date-btn" @click="changeDate(-1)">
        <i class="fas fa-chevron-left"></i>
      </button>
      <span>{{ formatDate(currentDate) }}</span>
      <button class="date-btn" @click="changeDate(1)">
        <i class="fas fa-chevron-right"></i>
      </button>
    </div>
    
    <div id="mealLogContainer">
      <div v-if="currentMeals.length === 0" class="empty-state">
        <i class="fas fa-utensils"></i>
        <p>今天还没有记录餐食</p>
        <p style="font-size: 0.9rem; color: var(--text-light); margin-top: 10px;">点击下方按钮开始记录</p>
      </div>
      <div v-else>
        <div v-for="meal in currentMeals" :key="meal.id" class="meal-item">
          <div class="meal-time">{{ meal.time }}<br><small>{{ meal.timeDetail }}</small></div>
          <div class="meal-content">
            <div style="font-weight: 500; margin-bottom: 5px;">{{ meal.content }}</div>
            <div v-if="meal.notes" style="color: var(--text-light); font-size: 0.9rem;">{{ meal.notes }}</div>
          </div>
          <div class="meal-macros">
            <div>{{ meal.protein }}g 蛋白质</div>
            <div>{{ meal.carbs }}g 碳水</div>
            <div>{{ meal.fat }}g 脂肪</div>
          </div>
          <div class="meal-actions">
            <div class="action-icon action-edit" @click="editMeal(meal.id)">
              <i class="fas fa-edit"></i>
            </div>
            <div class="action-icon action-delete" @click="deleteMeal(meal.id)">
              <i class="fas fa-trash"></i>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <div class="daily-total">
      <div class="total-grid">
        <div class="total-item">
          <div class="total-value">{{ dailyTotal.calories }}</div>
          <div class="total-label">总热量</div>
        </div>
        <div class="total-item">
          <div class="total-value">{{ dailyTotal.protein.toFixed(1) }}</div>
          <div class="total-label">蛋白质</div>
        </div>
        <div class="total-item">
          <div class="total-value">{{ dailyTotal.carbs.toFixed(1) }}</div>
          <div class="total-label">碳水化合物</div>
        </div>
        <div class="total-item">
          <div class="total-value">{{ dailyTotal.fat.toFixed(1) }}</div>
          <div class="total-label">脂肪</div>
        </div>
      </div>
    </div>
    
    <button class="btn btn-primary" style="width: 100%; margin-top: 20px;" @click="openMealModal()">
      <i class="fas fa-plus"></i>
      添加餐食记录
    </button>
  </div>
</template>

<script>
import { getDateKey, formatDate } from '../utils/date';
import { MEAL_TIME_ORDER } from '../constants/meal';

export default {
  name: 'MealLog',
  props: {
    currentDate: {
      type: Date,
      required: true
    },
    mealLogs: {
      type: Object,
      required: true
    },
    dailyTotal: {
      type: Object,
      required: true,
      validator: (value) => {
        return ['calories', 'protein', 'carbs', 'fat'].every(key => key in value);
      }
    }
  },
  computed: {
    currentMeals() {
      const dateKey = getDateKey(this.currentDate);
      const meals = this.mealLogs[dateKey] || [];
      return meals.sort((a, b) => {
        return MEAL_TIME_ORDER[a.time] - MEAL_TIME_ORDER[b.time];
      });
    }
  },
  methods: {
    formatDate,
    changeDate(direction) {
      const newDate = new Date(this.currentDate);
      newDate.setDate(newDate.getDate() + direction);
      this.$emit('update:currentDate', newDate);
    },
    editMeal(mealId) {
      this.$emit('edit-meal', mealId);
    },
    deleteMeal(mealId) {
      this.$emit('delete-meal', mealId);
    },
    openMealModal() {
      this.$emit('open-meal-modal');
    }
  }
}
</script>