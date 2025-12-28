/**
 * 日期工具函数
 */

/**
 * 获取日期的key（YYYY-MM-DD格式）
 * @param {Date} date - 日期对象
 * @returns {string} 日期key
 */
export function getDateKey(date) {
  return date.toISOString().split('T')[0];
}

/**
 * 格式化日期显示
 * @param {Date} date - 日期对象
 * @returns {string} 格式化后的日期字符串
 */
export function formatDate(date) {
  return date.toLocaleDateString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit' });
}

/**
 * 计算日期偏移
 * @param {Date} date - 原始日期
 * @param {number} days - 偏移天数
 * @returns {Date} 偏移后的日期
 */
export function offsetDate(date, days) {
  const newDate = new Date(date);
  newDate.setDate(newDate.getDate() + days);
  return newDate;
}
