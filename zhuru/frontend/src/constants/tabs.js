/**
 * 标签页相关常量
 */

// 标签页ID
export const TAB_IDS = {
  TRAINING: 'training',
  NUTRITION: 'nutrition',
  PROGRESS: 'progress'
};

// 标签页配置
export const TABS_CONFIG = [
  {
    id: TAB_IDS.TRAINING,
    label: '训练计划',
    icon: 'fas fa-dumbbell'
  },
  {
    id: TAB_IDS.NUTRITION,
    label: '营养目标',
    icon: 'fas fa-apple-alt'
  },
  {
    id: TAB_IDS.PROGRESS,
    label: '进度分析',
    icon: 'fas fa-chart-bar'
  }
];
