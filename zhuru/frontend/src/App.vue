<template>
  <div>
    <!-- 路由视图容器 -->
    <router-view @login="handleLogin" @register="handleRegister" @switch-to-register="switchToRegister" @switch-to-login="switchToLogin" @logout="handleLogout"></router-view>
  </div>
</template>

<script>
import { authApi } from './services/api';

export default {
  data() {
    return {
      // 认证相关状态
      isAuthenticated: localStorage.getItem('authToken') !== null
    }
  },
  
  methods: {
    // 处理登录
    handleLogin(credentials) {
      authApi.login(credentials)
        .then(response => {
          if (response.code === 0 && response.data) {
            // 保存认证信息到本地存储
            localStorage.setItem('authToken', response.data.token);
            localStorage.setItem('userId', response.data.userId);
            this.isAuthenticated = true;
            // 登录成功后跳转到主应用
            this.$router.push('/');
          } else {
            alert('登录失败: ' + (response.message || '未知错误'));
          }
        })
        .catch(error => {
          console.error('登录失败:', error);
          alert('登录失败，请检查网络连接');
        });
    },
    
    // 处理注册
    handleRegister(userData) {
      authApi.register(userData)
        .then(response => {
          if (response.code === 0) {
            alert('注册成功，请登录');
            this.$router.push('/login');
          } else {
            alert('注册失败: ' + (response.message || '未知错误'));
          }
        })
        .catch(error => {
          console.error('注册失败:', error);
          alert('注册失败，请检查网络连接');
        });
    },
    
    // 处理退出登录
    handleLogout() {
      // 清除本地存储的认证信息
      localStorage.removeItem('authToken');
      localStorage.removeItem('userId');
      this.isAuthenticated = false;
      // 跳转到登录页面
      this.$router.push('/login');
    },
    
    // 切换到登录页面
    switchToLogin() {
      this.$router.push('/login');
    },
    
    // 切换到注册页面
    switchToRegister() {
      this.$router.push('/register');
    }
  }
}
</script>

<style>
/* 全局样式 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background-color: #f5f7fa;
  color: #333;
  line-height: 1.6;
}
</style>