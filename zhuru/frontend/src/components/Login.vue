<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <h1>训练计划管理系统</h1>
        <p>登录您的账户开始训练</p>
      </div>
      
      <div class="login-form">
        <div class="input-group">
          <label class="input-label">
            <i class="fas fa-user"></i>
            用户名
          </label>
          <input 
            type="text" 
            class="elegant-input" 
            v-model="loginForm.username"
            placeholder="请输入用户名"
            required
          />
        </div>
        
        <div class="input-group">
          <label class="input-label">
            <i class="fas fa-lock"></i>
            密码
          </label>
          <input 
            type="password" 
            class="elegant-input" 
            v-model="loginForm.password"
            placeholder="请输入密码"
            required
          />
        </div>
        
        <button class="btn btn-primary btn-block" @click="login">
          <i class="fas fa-sign-in-alt"></i>
          登录
        </button>
        
        <div class="login-footer">
          <p>还没有账户？<a href="#" @click.prevent="$emit('switch-to-register')">立即注册</a></p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    login() {
      if (!this.loginForm.username || !this.loginForm.password) {
        alert('请输入用户名和密码');
        return;
      }
      
      this.$emit('login', this.loginForm);
    }
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  background: linear-gradient(135deg, var(--primary-light), var(--light-bg));
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

.login-card {
  background: white;
  border-radius: 20px;
  box-shadow: 0 20px 40px var(--shadow);
  padding: 40px;
  width: 100%;
  max-width: 450px;
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h1 {
  color: var(--primary);
  margin-bottom: 10px;
  font-size: 1.8rem;
}

.login-header p {
  color: var(--text-secondary);
  font-size: 1rem;
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.btn-block {
  width: 100%;
  justify-content: center;
  padding: 16px;
  font-size: 1.1rem;
}

.login-footer {
  text-align: center;
  margin-top: 20px;
  color: var(--text-secondary);
}

.login-footer a {
  color: var(--primary);
  text-decoration: none;
  font-weight: 600;
  transition: color 0.3s ease;
}

.login-footer a:hover {
  color: var(--secondary);
}
</style>