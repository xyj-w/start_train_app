<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <h1>创建新账户</h1>
        <p>注册以开始您的健身之旅</p>
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
            v-model="registerForm.username"
            placeholder="请输入用户名"
            required
          />
        </div>
        
        <div class="input-group">
          <label class="input-label">
            <i class="fas fa-user-circle"></i>
            昵称
          </label>
          <input 
            type="text" 
            class="elegant-input" 
            v-model="registerForm.nickname"
            placeholder="请输入昵称"
          />
        </div>
        
        <div class="input-group">
          <label class="input-label">
            <i class="fas fa-envelope"></i>
            邮箱
          </label>
          <input 
            type="email" 
            class="elegant-input" 
            v-model="registerForm.email"
            placeholder="请输入邮箱（可选）"
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
            v-model="registerForm.password"
            placeholder="请输入密码（至少6位）"
            required
            minlength="6"
          />
        </div>
        
        <div class="input-group">
          <label class="input-label">
            <i class="fas fa-lock"></i>
            确认密码
          </label>
          <input 
            type="password" 
            class="elegant-input" 
            v-model="registerForm.confirmPassword"
            placeholder="请再次输入密码"
            required
            minlength="6"
          />
        </div>
        
        <button class="btn btn-primary btn-block" @click="register">
          <i class="fas fa-user-plus"></i>
          注册
        </button>
        
        <div class="login-footer">
          <p>已有账户？<a href="#" @click.prevent="$emit('switch-to-login')">立即登录</a></p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Register',
  data() {
    return {
      registerForm: {
        username: '',
        nickname: '',
        email: '',
        password: '',
        confirmPassword: ''
      }
    }
  },
  methods: {
    register() {
      if (!this.registerForm.username || !this.registerForm.password) {
        alert('请输入用户名和密码');
        return;
      }
      
      if (this.registerForm.password.length < 6) {
        alert('密码长度至少6位');
        return;
      }
      
      if (this.registerForm.password !== this.registerForm.confirmPassword) {
        alert('两次输入的密码不一致');
        return;
      }
      
      this.$emit('register', this.registerForm);
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