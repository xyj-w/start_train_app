<template>
  <div class="modal-overlay" v-if="visible" @click.self="close">
    <div class="modal meal-modal">
      <div class="modal-header">
        <div class="modal-title">
          <i :class="isEditMode ? 'fas fa-edit' : 'fas fa-plus'"></i>
          <span>{{ isEditMode ? '编辑餐食记录' : '添加餐食记录' }}</span>
        </div>
        <button class="modal-close" @click="close">
          <i class="fas fa-times"></i>
        </button>
      </div>
      
      <div class="modal-body">
        <form @submit.prevent="saveMeal">
          <div class="input-group">
            <div class="input-label">
              <i class="fas fa-clock"></i>
              <span>餐食时间</span>
            </div>
            <select v-model="mealForm.time" class="elegant-input">
              <option value="早餐">早餐</option>
              <option value="午餐">午餐</option>
              <option value="晚餐">晚餐</option>
              <option value="加餐">加餐</option>
              <option value="其他">其他</option>
            </select>
          </div>
          
          <div class="input-group">
            <div class="input-label">
              <i class="fas fa-calendar-clock"></i>
              <span>具体时间</span>
            </div>
            <input 
              type="time" 
              v-model="mealForm.timeDetail" 
              class="elegant-input"
              required
            />
          </div>
          
          <div class="input-group">
            <div class="input-label">
              <i class="fas fa-utensils"></i>
              <span>餐食内容</span>
            </div>
            <textarea 
              v-model="mealForm.content" 
              class="elegant-input"
              rows="3"
              placeholder="例如：燕麦50g + 鸡蛋2个 + 牛奶200ml"
              required
            ></textarea>
          </div>
          
          <div class="form-grid">
            <div class="input-group">
              <div class="input-label">
                <i class="fas fa-fire"></i>
                <span>热量 (kcal)</span>
              </div>
              <input 
                type="number" 
                v-model.number="mealForm.calories" 
                class="elegant-input"
                min="0"
                step="1"
                placeholder="0"
                required
              />
            </div>
            
            <div class="input-group">
              <div class="input-label">
                <i class="fas fa-drumstick-bite"></i>
                <span>蛋白质 (g)</span>
              </div>
              <input 
                type="number" 
                v-model.number="mealForm.protein" 
                class="elegant-input"
                min="0"
                step="0.1"
                placeholder="0"
                required
              />
            </div>
            
            <div class="input-group">
              <div class="input-label">
                <i class="fas fa-bread-slice"></i>
                <span>碳水化合物 (g)</span>
              </div>
              <input 
                type="number" 
                v-model.number="mealForm.carbs" 
                class="elegant-input"
                min="0"
                step="0.1"
                placeholder="0"
                required
              />
            </div>
            
            <div class="input-group">
              <div class="input-label">
                <i class="fas fa-seedling"></i>
                <span>脂肪 (g)</span>
              </div>
              <input 
                type="number" 
                v-model.number="mealForm.fat" 
                class="elegant-input"
                min="0"
                step="0.1"
                placeholder="0"
                required
              />
            </div>
          </div>
          
          <div class="input-group">
            <div class="input-label">
              <i class="fas fa-sticky-note"></i>
              <span>备注</span>
            </div>
            <textarea 
              v-model="mealForm.notes" 
              class="elegant-input"
              rows="2"
              placeholder="可以添加一些餐食的备注信息"
            ></textarea>
          </div>
          
          <div class="modal-footer" style="display: flex; justify-content: flex-end; gap: 15px; margin-top: 25px;">
            <button type="button" class="btn btn-secondary" @click="close">取消</button>
            <button type="submit" class="btn btn-primary">
              <i :class="isEditMode ? 'fas fa-save' : 'fas fa-plus'"></i>
              {{ isEditMode ? '保存修改' : '添加记录' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MealModal',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    meal: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      mealForm: {
        time: '早餐',
        timeDetail: new Date().toTimeString().substring(0, 5),
        content: '',
        calories: 0,
        protein: 0,
        carbs: 0,
        fat: 0,
        notes: ''
      }
    };
  },
  computed: {
    isEditMode() {
      return !!this.meal;
    }
  },
  watch: {
    meal(newVal) {
      if (newVal) {
        this.mealForm = { ...newVal };
      } else {
        this.resetForm();
      }
    },
    visible(newVal) {
      if (newVal && !this.isEditMode) {
        this.resetForm();
      }
    }
  },
  methods: {
    resetForm() {
      this.mealForm = {
        time: '早餐',
        timeDetail: new Date().toTimeString().substring(0, 5),
        content: '',
        calories: 0,
        protein: 0,
        carbs: 0,
        fat: 0,
        notes: ''
      };
    },
    saveMeal() {
      // 前台表单验证
      if (this.mealForm.calories <= 0) {
        alert('热量必须大于0');
        return;
      }
      if (this.mealForm.protein <= 0) {
        alert('蛋白质必须大于0');
        return;
      }
      if (this.mealForm.carbs <= 0) {
        alert('碳水化合物必须大于0');
        return;
      }
      if (this.mealForm.fat <= 0) {
        alert('脂肪必须大于0');
        return;
      }
      this.$emit('save-meal', this.mealForm);
    },
    close() {
      this.$emit('close');
    }
  }
};
</script>

<style scoped>
.meal-modal {
  width: 90%;
  max-width: 500px;
  max-height: 90vh;
  overflow-y: auto;
}

.nutrition-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 15px;
  margin-bottom: 20px;
}

@media (max-width: 768px) {
  .nutrition-grid {
    grid-template-columns: 1fr;
  }
}
</style>