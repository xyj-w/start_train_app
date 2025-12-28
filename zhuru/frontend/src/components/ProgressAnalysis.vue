<template>
  <div class="card">
    <div class="card-header">
      <div class="card-title">
        <i class="fas fa-chart-bar"></i>
        <span>进度分析</span>
      </div>
    </div>
    
    <div class="stats-grid">
      <div class="stat-item">
        <div class="progress-circle">
          <svg class="progress-ring" width="100" height="100">
            <circle class="progress-ring-circle" stroke="#E8E6F0" stroke-width="8" fill="transparent" r="42" cx="50" cy="50"/>
            <circle class="progress-ring-circle" stroke="#8B5FBF" stroke-width="8" fill="transparent" r="42" cx="50" cy="50" 
              stroke-dasharray="264" :stroke-dashoffset="calorieProgressOffset"/>
          </svg>
          <div class="progress-text">
            <div class="progress-percent">{{ calorieProgress }}%</div>
            <div class="progress-label">热量目标</div>
          </div>
        </div>
      </div>
      
      <div class="stat-item">
        <div class="progress-circle">
          <svg class="progress-ring" width="100" height="100">
            <circle class="progress-ring-circle" stroke="#E8E6F0" stroke-width="8" fill="transparent" r="42" cx="50" cy="50"/>
            <circle class="progress-ring-circle" stroke="#00C9B7" stroke-width="8" fill="transparent" r="42" cx="50" cy="50" 
              stroke-dasharray="264" :stroke-dashoffset="proteinProgressOffset"/>
          </svg>
          <div class="progress-text">
            <div class="progress-percent">{{ proteinProgress }}%</div>
            <div class="progress-label">蛋白质目标</div>
          </div>
        </div>
      </div>
      
      <div class="stat-item">
        <div class="progress-circle">
          <svg class="progress-ring" width="100" height="100">
            <circle class="progress-ring-circle" stroke="#E8E6F0" stroke-width="8" fill="transparent" r="42" cx="50" cy="50"/>
            <circle class="progress-ring-circle" stroke="#FF6B9D" stroke-width="8" fill="transparent" r="42" cx="50" cy="50" 
              stroke-dasharray="264" :stroke-dashoffset="carbsProgressOffset"/>
          </svg>
          <div class="progress-text">
            <div class="progress-percent">{{ carbsProgress }}%</div>
            <div class="progress-label">碳水目标</div>
          </div>
        </div>
      </div>
    </div>
    
    <div class="daily-total">
      <div class="total-grid">
        <div class="total-item">
          <div class="total-value">{{ dailyTotal.calories }}/{{ userData.trainCalories }}</div>
          <div class="total-label">热量进度</div>
        </div>
        <div class="total-item">
          <div class="total-value">{{ dailyTotal.protein }}/{{ trainMacro.protein }}</div>
          <div class="total-label">蛋白质进度</div>
        </div>
        <div class="total-item">
          <div class="total-value">{{ dailyTotal.carbs }}/{{ trainMacro.carbs }}</div>
          <div class="total-label">碳水进度</div>
        </div>
        <div class="total-item">
          <div class="total-value">{{ dailyTotal.fat }}/{{ trainMacro.fat }}</div>
          <div class="total-label">脂肪进度</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProgressAnalysis',
  props: {
    userData: {
      type: Object,
      required: true,
      default: () => ({ trainCalories: 2000 }),
      validator: (value) => {
        return ['trainCalories'].every(key => key in value);
      }
    },
    trainMacro: {
      type: Object,
      required: true,
      default: () => ({ protein: 100, carbs: 200, fat: 50 }),
      validator: (value) => {
        return ['protein', 'carbs', 'fat'].every(key => key in value);
      }
    },
    dailyTotal: {
      type: Object,
      required: true,
      default: () => ({ calories: 0, protein: 0, carbs: 0, fat: 0 }),
      validator: (value) => {
        return ['calories', 'protein', 'carbs', 'fat'].every(key => key in value);
      }
    },
    calorieProgress: {
      type: Number,
      required: true,
      default: 0,
      validator: (value) => {
        return value >= 0 && value <= 100;
      }
    },
    proteinProgress: {
      type: Number,
      required: true,
      default: 0,
      validator: (value) => {
        return value >= 0 && value <= 100;
      }
    },
    carbsProgress: {
      type: Number,
      required: true,
      default: 0,
      validator: (value) => {
        return value >= 0 && value <= 100;
      }
    },
    calorieProgressOffset: {
      type: Number,
      required: true,
      default: 264
    },
    proteinProgressOffset: {
      type: Number,
      required: true,
      default: 264
    },
    carbsProgressOffset: {
      type: Number,
      required: true,
      default: 264
    }
  }
}
</script>

<style scoped>
.progress-percent {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--primary);
}

.progress-label {
  font-size: 0.9rem;
  color: var(--text-secondary);
  margin-top: 5px;
}

.progress-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}

.progress-circle {
  position: relative;
  margin: 0 auto;
}

.stats-grid {
  margin-top: 20px;
}

@media (max-width: 768px) {
  .stats-grid {
    grid-template-columns: repeat(3, 1fr);
  }
  
  .daily-total {
    margin-top: 10px;
  }
  
  .total-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>