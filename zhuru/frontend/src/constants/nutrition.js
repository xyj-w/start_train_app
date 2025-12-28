/**
 * 营养目标相关常量
 */

// 默认营养目标比例
export const DEFAULT_NUTRITION_GOALS = {
  proteinRatio: 27,
  carbsRatio: 48,
  fatRatio: 25
};

// 宏量营养素单位热量值 (kcal/g)
export const MACRO_CALORIES = {
  protein: 4,
  carbs: 4,
  fat: 9
};

// 宏量营养素类型
export const MACRO_TYPES = [
  { key: 'protein', label: '蛋白质', unit: 'g' },
  { key: 'carbs', label: '碳水化合物', unit: 'g' },
  { key: 'fat', label: '脂肪', unit: 'g' }
];
