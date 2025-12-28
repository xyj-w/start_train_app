<template>
  <div class="card">
    <div class="card-header">
      <div class="card-title">
        <i class="fas fa-chart-pie"></i>
        <span>营养目标设置</span>
      </div>
    </div>
    
    <div class="form-grid">
      <div class="input-group">
        <div class="input-label">
          <i class="fas fa-drumstick-bite"></i>
          <span>蛋白质比例 (%)</span>
        </div>
        <input type="number" v-model.number="localNutritionGoals.proteinRatio" class="elegant-input" min="20" max="40" step="1">
      </div>
      
      <div class="input-group">
        <div class="input-label">
          <i class="fas fa-bread-slice"></i>
          <span>碳水比例 (%)</span>
        </div>
        <input type="number" v-model.number="localNutritionGoals.carbsRatio" class="elegant-input" min="30" max="60" step="1">
      </div>
      
      <div class="input-group">
        <div class="input-label">
          <i class="fas fa-oil-can"></i>
          <span>脂肪比例 (%)</span>
        </div>
        <input type="number" v-model.number="localNutritionGoals.fatRatio" class="elegant-input" min="15" max="35" step="1">
      </div>
    </div>
    
    <button class="btn btn-primary" @click="updateNutritionGoals">
      <i class="fas fa-save"></i>
      保存营养目标
    </button>
  </div>
</template>

<script>
export default {
  name: 'NutritionGoal',
  props: {
    nutritionGoals: {
      type: Object,
      required: true,
      validator: (value) => {
        const hasRequiredKeys = ['proteinRatio', 'carbsRatio', 'fatRatio'].every(key => key in value);
        if (!hasRequiredKeys) return false;
        
        // Check that ratios add up to approximately 100% (allowing for small rounding errors)
        const total = value.proteinRatio + value.carbsRatio + value.fatRatio;
        return total >= 99 && total <= 101;
      }
    }
  },
  data() {
    return {
      localNutritionGoals: {
        proteinRatio: this.nutritionGoals.proteinRatio,
        carbsRatio: this.nutritionGoals.carbsRatio,
        fatRatio: this.nutritionGoals.fatRatio
      }
    }
  },
  watch: {
    nutritionGoals: {
      handler(newVal) {
        this.localNutritionGoals = {
          proteinRatio: newVal.proteinRatio,
          carbsRatio: newVal.carbsRatio,
          fatRatio: newVal.fatRatio
        }
      },
      deep: true
    }
  },
  methods: {
    updateNutritionGoals() {
      this.$emit('update-nutrition-goals', this.localNutritionGoals);
    }
  }
}
</script>