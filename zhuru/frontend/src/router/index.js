import Vue from 'vue'
import VueRouter from 'vue-router'

// 导入组件
import Login from '../components/Login.vue'
import Register from '../components/Register.vue'
import MainApp from '../components/MainApp.vue' // 主应用组件

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: { requiresAuth: false }
  },
  {
    path: '/register',
    name: 'Register',
    component: Register,
    meta: { requiresAuth: false }
  },
  {
    path: '/',
    name: 'MainApp',
    component: MainApp,
    meta: { requiresAuth: true }
  },
  // 默认重定向到登录页
  {
    path: '*',
    redirect: '/login'
  }
]

const router = new VueRouter({
  mode: 'hash',
  base: import.meta.env.BASE_URL,
  routes
})

// 路由守卫，检查登录状态
router.beforeEach((to, from, next) => {
  const isAuthenticated = localStorage.getItem('authToken') !== null
  
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // 路由需要认证
    if (isAuthenticated) {
      next() // 已认证，允许访问
    } else {
      next({ name: 'Login' }) // 未认证，重定向到登录页
    }
  } else {
    // 路由不需要认证
    if (isAuthenticated && (to.name === 'Login' || to.name === 'Register')) {
      next({ name: 'MainApp' }) // 已认证，访问登录/注册页时重定向到主应用
    } else {
      next() // 允许访问
    }
  }
})

export default router