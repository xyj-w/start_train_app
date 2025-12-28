<template>
  <header>
    <div class="logo">
      <div class="logo-icon">
        <i class="fas fa-heartbeat"></i>
      </div>
      <div>
        <h1>逐日</h1>
        <p class="subtitle">科学减脂增肌 · 实时追踪管理 · 个性化调整</p>
      </div>
    </div>
    <button class="btn btn-secondary logout-btn" @click="handleLogout">
      <i class="fas fa-sign-out-alt"></i>退出登录
    </button>
  </header>
</template>

<script>
export default {
  name: 'Header',
  methods: {
    handleLogout() {
      this.$emit('logout');
    }
  }
}
</script>