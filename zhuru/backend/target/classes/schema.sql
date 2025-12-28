-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS zhuru DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE zhuru;

-- 创建用户表
CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    height DOUBLE COMMENT '身高 (cm)',
    weight DOUBLE COMMENT '体重 (kg)',
    bmi DOUBLE COMMENT 'BMI指数',
    train_calories INT COMMENT '训练日摄入热量 (大卡)',
    rest_calories INT COMMENT '休息日摄入热量 (大卡)',
    created_at BIGINT NOT NULL COMMENT '创建时间',
    updated_at BIGINT NOT NULL COMMENT '更新时间',
    deleted BOOLEAN DEFAULT FALSE COMMENT '标记删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 创建营养目标表
CREATE TABLE IF NOT EXISTS nutrition_goal (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '营养目标ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    protein_ratio INT NOT NULL COMMENT '蛋白质比例 (%)',
    carbs_ratio INT NOT NULL COMMENT '碳水比例 (%)',
    fat_ratio INT NOT NULL COMMENT '脂肪比例 (%)',
    created_at BIGINT NOT NULL COMMENT '创建时间',
    updated_at BIGINT NOT NULL COMMENT '更新时间',
    deleted BOOLEAN DEFAULT FALSE COMMENT '标记删除',
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='营养目标表';

-- 创建餐食记录表
CREATE TABLE IF NOT EXISTS meal_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '餐食记录ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    record_date VARCHAR(10) NOT NULL COMMENT '记录日期（YYYY-MM-DD）',
    meal_time VARCHAR(20) NOT NULL COMMENT '餐食时间（早餐、午餐、晚餐等）',
    time_detail VARCHAR(5) NOT NULL COMMENT '餐食具体时间（HH:MM）',
    content VARCHAR(255) NOT NULL COMMENT '餐食内容',
    protein DOUBLE NOT NULL COMMENT '蛋白质含量 (g)',
    carbs DOUBLE NOT NULL COMMENT '碳水化合物含量 (g)',
    fat DOUBLE NOT NULL COMMENT '脂肪含量 (g)',
    calories INT NOT NULL COMMENT '热量 (大卡)',
    notes VARCHAR(255) COMMENT '备注信息',
    created_at BIGINT NOT NULL COMMENT '创建时间',
    updated_at BIGINT NOT NULL COMMENT '更新时间',
    deleted BOOLEAN DEFAULT FALSE COMMENT '标记删除',
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='餐食记录表';

-- 创建训练计划表
CREATE TABLE IF NOT EXISTS training_plan (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '训练计划ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    day_of_week VARCHAR(10) NOT NULL COMMENT '训练日（周一至周日）',
    exercise_name VARCHAR(100) NOT NULL COMMENT '训练动作名称',
    sets INT NOT NULL COMMENT '组数',
    reps VARCHAR(20) NOT NULL COMMENT '次数',
    weight VARCHAR(20) COMMENT '重量',
    tips VARCHAR(255) COMMENT '动作要点',
    sort_order INT NOT NULL DEFAULT 0 COMMENT '排序序号',
    created_at BIGINT NOT NULL COMMENT '创建时间',
    updated_at BIGINT NOT NULL COMMENT '更新时间',
    deleted BOOLEAN DEFAULT FALSE COMMENT '标记删除',
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='训练计划表';

-- 创建索引
CREATE INDEX idx_user_deleted ON user(deleted);
CREATE INDEX idx_nutrition_goal_user_id ON nutrition_goal(user_id);
CREATE INDEX idx_meal_log_user_id ON meal_log(user_id);
CREATE INDEX idx_meal_log_record_date ON meal_log(record_date);
CREATE INDEX idx_training_plan_user_id ON training_plan(user_id);
CREATE INDEX idx_training_plan_day_of_week ON training_plan(day_of_week);
