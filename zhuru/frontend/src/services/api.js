import axios from 'axios';

// 创建axios实例
const api = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
});

// 请求拦截器
api.interceptors.request.use(
  config => {
    // 在发送请求之前做些什么
    const token = localStorage.getItem('authToken');
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    // 对请求错误做些什么
    return Promise.reject(error);
  }
);

// 响应拦截器
api.interceptors.response.use(
  response => {
    // 对响应数据做点什么
    return response.data;
  },
  error => {
    // 对响应错误做点什么
    console.error('API Error:', error);
    return Promise.reject(error);
  }
);

// 用户相关API
export const userApi = {
  // 获取用户信息
  getUserInfo: (userId) => api.get(`/user/${userId}`),
  // 更新用户信息
  updateUserInfo: (userId, userData) => api.put(`/user/${userId}`, userData),
  // 创建用户信息
  createUserInfo: (userData) => api.post('/user', userData)
};

// 认证相关API
export const authApi = {
  // 登录
  login: (credentials) => api.post('/auth/login', credentials),
  // 注册
  register: (userData) => api.post('/auth/register', userData),
  // 退出登录
  logout: () => api.post('/auth/logout')
};

// 餐食记录相关API
export const mealLogApi = {
  // 获取指定日期的餐食记录
  getMealLogs: (userId, date) => api.get(`/meal/log/${userId}`, { params: { date } }),
  // 保存餐食记录
  saveMealLog: (mealLog) => api.post('/meal/log', mealLog),
  // 更新餐食记录
  updateMealLog: (mealLog) => api.put(`/meal/log/${mealLog.id}`, mealLog),
  // 删除餐食记录
  deleteMealLog: (mealId) => api.delete(`/meal/log/${mealId}`)
};

// 营养目标相关API
export const nutritionGoalApi = {
  // 获取营养目标
  getNutritionGoal: (userId) => api.get(`/nutrition/goal/user/${userId}`),
  // 更新营养目标
  updateNutritionGoal: (userId, goalData) => api.put(`/nutrition/goal/${goalData.id || 0}`, {...goalData, userId})
};

// 训练计划相关API
export const trainingPlanApi = {
  // 获取训练计划
  getTrainingPlan: (userId) => api.get(`/training/plan/user/${userId}`),
  // 更新训练计划
  updateTrainingPlan: (userId, planData) => api.put(`/training/plan/${planData.id || 0}`, {...planData, userId}),
  // 添加训练动作
  addExercise: (exerciseData) => api.post('/training/plan', exerciseData),
  // 更新训练动作
  updateExercise: (exerciseData) => api.put(`/training/plan/${exerciseData.id}`, exerciseData),
  // 删除训练动作
  deleteExercise: (exerciseId) => api.delete(`/training/plan/${exerciseId}`)
};

export default api;