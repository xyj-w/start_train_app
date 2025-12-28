<template>
  <div class="action-buttons">
    <button class="floating-btn" @click="exportToExcel" title="导出Excel">
      <i class="fas fa-file-excel"></i>
    </button>
    <button class="floating-btn" @click="printPlan" title="打印计划">
      <i class="fas fa-print"></i>
    </button>
    <button class="floating-btn" @click="showHelp" title="使用帮助">
      <i class="fas fa-question-circle"></i>
    </button>
  </div>
</template>

<script>
export default {
  name: 'ActionButtons',
  methods: {
    exportToExcel() {
      this.$emit('export-to-excel');
    },
    printPlan() {
      this.$emit('print-plan');
    },
    showHelp() {
      this.$emit('show-help');
    }
  }
}
</script>