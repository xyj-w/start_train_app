<template>
  <div class="card">
    <div class="card-header">
      <div class="card-title">
        <i class="fas fa-calendar-alt"></i>
        <span>一周训练计划</span>
      </div>
      <button class="btn btn-secondary btn-small" @click="openExerciseModal">
        <i class="fas fa-plus"></i>
        添加训练动作
      </button>
    </div>
    
    <div class="training-plan">
      <div v-for="day in days" :key="day" class="day-card">
        <div class="day-header">
          <div class="day-title">
            <i class="fas fa-calendar-day"></i>
            <span>{{ day }}</span>
          </div>
          <div class="day-status">
            <span v-if="!isTrainingDay(day)" class="status-rest">主动恢复</span>
            <span v-else class="status-train">训练日</span>
            <label class="switch">
              <input type="checkbox" v-model="trainingDayToggle[day]" @change="toggleTrainingDay(day)">
              <span class="slider"></span>
            </label>
          </div>
        </div>
        <ul class="exercise-list">
          <li v-for="exercise in trainingPlan[day]" :key="exercise.id" class="exercise-item">
            <div class="exercise-content">
              <div class="exercise-header">
                <div class="exercise-name">
                  <i class="fas fa-dumbbell"></i>
                  {{ exercise.name }}
                </div>
                <div class="exercise-meta">
                  <span class="exercise-detail">{{ exercise.sets }}组 × {{ exercise.reps }}次</span>
                  <span v-if="exercise.weight" class="exercise-weight">{{ exercise.weight }}</span>
                </div>
              </div>
              <div v-if="exercise.tips" class="exercise-tips">{{ exercise.tips }}</div>
            </div>
            <div class="exercise-actions">
              <button class="action-icon action-edit" @click="editExercise(exercise.id)" title="编辑">
                <i class="fas fa-edit"></i>
              </button>
              <button class="action-icon action-delete" @click="deleteExercise(exercise.id)" title="删除">
                <i class="fas fa-trash"></i>
              </button>
              <button class="action-icon action-image" @click="uploadExerciseImage(exercise.id)" title="上传图片">
                <i class="fas fa-image"></i>
              </button>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TrainingPlan',
  props: {
    trainingPlan: {
      type: Object,
      required: true,
      validator: (value) => {
        return true; // Basic validation, can be expanded if needed
      }
    },
    days: {
      type: Array,
      required: true,
      validator: (value) => {
        return value.every(day => typeof day === 'string');
      }
    },
    trainingDays: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      // 训练日切换状态，初始值从props获取
      trainingDayToggle: { ...this.trainingDays }
    };
  },
  methods: {
    openExerciseModal() {
      this.$emit('open-exercise-modal');
    },
    editExercise(exerciseId) {
      this.$emit('edit-exercise', exerciseId);
    },
    deleteExercise(exerciseId) {
      this.$emit('delete-exercise', exerciseId);
    },
    uploadExerciseImage(exerciseId) {
      this.$emit('upload-exercise-image', exerciseId);
    },
    // 判断是否为训练日
    isTrainingDay(day) {
      return this.trainingDayToggle[day];
    },
    // 切换训练日/休息日
    toggleTrainingDay(day) {
      this.$emit('update-training-day', day, this.trainingDayToggle[day]);
    }
  }
}
</script>

<style scoped>
.day-status {
  display: flex;
  align-items: center;
  gap: 10px;
}

.status-rest {
  color: var(--accent);
  font-size: 0.9rem;
  background: rgba(0,201,183,0.1);
  padding: 4px 12px;
  border-radius: 8px;
}

.status-train {
  color: var(--primary);
  font-size: 0.9rem;
  background: rgba(139,95,191,0.1);
  padding: 4px 12px;
  border-radius: 8px;
}

.exercise-meta {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 4px;
}

.exercise-weight {
  font-size: 0.85rem;
  color: var(--accent);
  font-weight: 600;
  background: rgba(0, 201, 183, 0.1);
  padding: 2px 8px;
  border-radius: 4px;
}

.exercise-tips {
  font-size: 0.85rem;
  color: var(--text-light);
  font-style: italic;
  line-height: 1.4;
  margin-top: 4px;
  word-break: break-word;
}

.exercise-actions {
  display: flex;
  flex-direction: column;
  gap: 6px;
  margin-left: 12px;
}

.action-image {
  background: rgba(0, 201, 183, 0.1);
  color: var(--accent);
}

.action-image:hover {
  background: var(--accent);
  color: white;
  transform: scale(1.1);
}

/* 开关样式 */
.switch {
  position: relative;
  display: inline-block;
  width: 40px;
  height: 22px;
}

.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(139, 95, 191, 0.3);
  transition: .3s;
  border-radius: 34px;
}

.slider:before {
  position: absolute;
  content: "";
  height: 16px;
  width: 16px;
  left: 3px;
  bottom: 3px;
  background-color: white;
  transition: .3s;
  border-radius: 50%;
}

input:checked + .slider {
  background-color: var(--primary);
}

input:checked + .slider:before {
  transform: translateX(18px);
}

@media (max-width: 768px) {
  .exercise-actions {
    flex-direction: row;
    margin-left: 0;
    justify-content: flex-end;
  }
  
  .day-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }
  
  .day-status {
    align-self: flex-end;
  }
}
</style>