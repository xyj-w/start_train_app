-- 初始化数据库数据
-- 创建用户
INSERT INTO user (height, weight, bmi, gender, age, training_intensity, bmr, tdee, train_calories, rest_calories, created_at, updated_at, deleted)
VALUES (175.0, 70.0, 22.9, 'male', 28, 'moderate', 1750, 2713, 2713, 2442, 1735392000000, 1735392000000, false);

-- 创建营养目标
INSERT INTO nutrition_goal (user_id, protein_ratio, carbs_ratio, fat_ratio, created_at, updated_at, deleted)
VALUES (1, 30, 50, 20, 1735392000000, 1735392000000, false);

-- 创建训练计划
-- 周一：胸部训练
INSERT INTO training_plan (user_id, day_of_week, exercise_name, sets, reps, weight, tips, sort_order, created_at, updated_at, deleted)
VALUES (1, '周一', '杠铃卧推', 4, '8-12', '60kg', '保持核心收紧，缓慢下放杠铃', 1, 1735392000000, 1735392000000, false);
INSERT INTO training_plan (user_id, day_of_week, exercise_name, sets, reps, weight, tips, sort_order, created_at, updated_at, deleted)
VALUES (1, '周一', '哑铃飞鸟', 3, '12-15', '15kg', '感受胸部肌肉的拉伸和收缩', 2, 1735392000000, 1735392000000, false);
INSERT INTO training_plan (user_id, day_of_week, exercise_name, sets, reps, weight, tips, sort_order, created_at, updated_at, deleted)
VALUES (1, '周一', '俯卧撑', 3, '15-20', '自重', '保持身体呈直线', 3, 1735392000000, 1735392000000, false);

-- 周二：背部训练
INSERT INTO training_plan (user_id, day_of_week, exercise_name, sets, reps, weight, tips, sort_order, created_at, updated_at, deleted)
VALUES (1, '周二', '引体向上', 3, '8-12', '自重', '感受背部肌肉的收缩', 1, 1735392000000, 1735392000000, false);
INSERT INTO training_plan (user_id, day_of_week, exercise_name, sets, reps, weight, tips, sort_order, created_at, updated_at, deleted)
VALUES (1, '周二', '杠铃划船', 4, '8-10', '70kg', '保持背部挺直', 2, 1735392000000, 1735392000000, false);
INSERT INTO training_plan (user_id, day_of_week, exercise_name, sets, reps, weight, tips, sort_order, created_at, updated_at, deleted)
VALUES (1, '周二', '高位下拉', 3, '12-15', '40kg', '下拉至锁骨位置', 3, 1735392000000, 1735392000000, false);

-- 周三：肩部训练
INSERT INTO training_plan (user_id, day_of_week, exercise_name, sets, reps, weight, tips, sort_order, created_at, updated_at, deleted)
VALUES (1, '周三', '坐姿哑铃推举', 4, '8-10', '20kg', '保持背部挺直', 1, 1735392000000, 1735392000000, false);
INSERT INTO training_plan (user_id, day_of_week, exercise_name, sets, reps, weight, tips, sort_order, created_at, updated_at, deleted)
VALUES (1, '周三', '侧平举', 3, '12-15', '10kg', '缓慢抬起，感受肩部外侧肌肉', 2, 1735392000000, 1735392000000, false);
INSERT INTO training_plan (user_id, day_of_week, exercise_name, sets, reps, weight, tips, sort_order, created_at, updated_at, deleted)
VALUES (1, '周三', '前平举', 3, '12-15', '10kg', '缓慢抬起，感受肩部前侧肌肉', 3, 1735392000000, 1735392000000, false);

-- 周四：手臂训练
INSERT INTO training_plan (user_id, day_of_week, exercise_name, sets, reps, weight, tips, sort_order, created_at, updated_at, deleted)
VALUES (1, '周四', '杠铃弯举', 4, '8-10', '30kg', '保持肘部固定', 1, 1735392000000, 1735392000000, false);
INSERT INTO training_plan (user_id, day_of_week, exercise_name, sets, reps, weight, tips, sort_order, created_at, updated_at, deleted)
VALUES (1, '周四', '三头肌下压', 3, '12-15', '40kg', '保持肘部固定', 2, 1735392000000, 1735392000000, false);
INSERT INTO training_plan (user_id, day_of_week, exercise_name, sets, reps, weight, tips, sort_order, created_at, updated_at, deleted)
VALUES (1, '周四', '哑铃锤式弯举', 3, '10-12', '15kg', '保持手腕固定', 3, 1735392000000, 1735392000000, false);

-- 周五：腿部训练
INSERT INTO training_plan (user_id, day_of_week, exercise_name, sets, reps, weight, tips, sort_order, created_at, updated_at, deleted)
VALUES (1, '周五', '杠铃深蹲', 4, '8-10', '80kg', '保持背部挺直，膝盖不超过脚尖', 1, 1735392000000, 1735392000000, false);
INSERT INTO training_plan (user_id, day_of_week, exercise_name, sets, reps, weight, tips, sort_order, created_at, updated_at, deleted)
VALUES (1, '周五', '硬拉', 3, '6-8', '90kg', '保持背部挺直，缓慢抬起', 2, 1735392000000, 1735392000000, false);
INSERT INTO training_plan (user_id, day_of_week, exercise_name, sets, reps, weight, tips, sort_order, created_at, updated_at, deleted)
VALUES (1, '周五', '腿举', 3, '12-15', '150kg', '膝盖不超过脚尖', 3, 1735392000000, 1735392000000, false);

-- 周六：全身训练
INSERT INTO training_plan (user_id, day_of_week, exercise_name, sets, reps, weight, tips, sort_order, created_at, updated_at, deleted)
VALUES (1, '周六', '波比跳', 3, '15-20', '自重', '快速完成，保持节奏', 1, 1735392000000, 1735392000000, false);
INSERT INTO training_plan (user_id, day_of_week, exercise_name, sets, reps, weight, tips, sort_order, created_at, updated_at, deleted)
VALUES (1, '周六', '哑铃箭步蹲', 3, '10-12', '15kg', '保持身体平衡', 2, 1735392000000, 1735392000000, false);
INSERT INTO training_plan (user_id, day_of_week, exercise_name, sets, reps, weight, tips, sort_order, created_at, updated_at, deleted)
VALUES (1, '周六', '平板支撑', 3, '60秒', '自重', '保持核心收紧', 3, 1735392000000, 1735392000000, false);

-- 周日：休息日（无需插入数据）

-- 创建餐食记录
-- 2025-12-28 餐食记录
INSERT INTO meal_log (user_id, record_date, meal_time, time_detail, content, protein, carbs, fat, calories, notes, sort_order, created_at, updated_at, deleted)
VALUES (1, '2025-12-28', '早餐', '07:30', '燕麦粥 + 鸡蛋2个 + 香蕉', 25.0, 60.0, 10.0, 400, '营养均衡的早餐', 1, 1735392000000, 1735392000000, false);
INSERT INTO meal_log (user_id, record_date, meal_time, time_detail, content, protein, carbs, fat, calories, notes, sort_order, created_at, updated_at, deleted)
VALUES (1, '2025-12-28', '午餐', '12:00', '鸡胸肉 + 糙米饭 + 西兰花', 30.0, 50.0, 15.0, 450, '高蛋白午餐', 2, 1735392000000, 1735392000000, false);
INSERT INTO meal_log (user_id, record_date, meal_time, time_detail, content, protein, carbs, fat, calories, notes, sort_order, created_at, updated_at, deleted)
VALUES (1, '2025-12-28', '晚餐', '18:30', '鱼 + 红薯 + 青菜', 25.0, 40.0, 15.0, 400, '低脂肪晚餐', 3, 1735392000000, 1735392000000, false);

-- 2025-12-29 餐食记录
INSERT INTO meal_log (user_id, record_date, meal_time, time_detail, content, protein, carbs, fat, calories, notes, sort_order, created_at, updated_at, deleted)
VALUES (1, '2025-12-29', '早餐', '07:45', '全麦面包 + 牛奶 + 苹果', 20.0, 55.0, 12.0, 380, '健康早餐', 1, 1735392000000, 1735392000000, false);
INSERT INTO meal_log (user_id, record_date, meal_time, time_detail, content, protein, carbs, fat, calories, notes, sort_order, created_at, updated_at, deleted)
VALUES (1, '2025-12-29', '午餐', '12:15', '瘦牛肉 + 杂粮饭 + 菠菜', 35.0, 45.0, 18.0, 480, '高蛋白午餐', 2, 1735392000000, 1735392000000, false);
INSERT INTO meal_log (user_id, record_date, meal_time, time_detail, content, protein, carbs, fat, calories, notes, sort_order, created_at, updated_at, deleted)
VALUES (1, '2025-12-29', '晚餐', '18:45', '虾仁 + 玉米 + 生菜', 28.0, 38.0, 14.0, 410, '低脂晚餐', 3, 1735392000000, 1735392000000, false);

-- 2025-12-30 餐食记录
INSERT INTO meal_log (user_id, record_date, meal_time, time_detail, content, protein, carbs, fat, calories, notes, sort_order, created_at, updated_at, deleted)
VALUES (1, '2025-12-30', '早餐', '08:00', '鸡蛋饼 + 豆浆 + 橙子', 22.0, 58.0, 13.0, 410, '营养早餐', 1, 1735392000000, 1735392000000, false);
INSERT INTO meal_log (user_id, record_date, meal_time, time_detail, content, protein, carbs, fat, calories, notes, sort_order, created_at, updated_at, deleted)
VALUES (1, '2025-12-30', '午餐', '12:30', '鸭肉 + 白米饭 + 胡萝卜', 32.0, 48.0, 20.0, 470, '均衡午餐', 2, 1735392000000, 1735392000000, false);
INSERT INTO meal_log (user_id, record_date, meal_time, time_detail, content, protein, carbs, fat, calories, notes, sort_order, created_at, updated_at, deleted)
VALUES (1, '2025-12-30', '晚餐', '19:00', '豆腐 + 糙米 + 芹菜', 20.0, 42.0, 16.0, 390, '素食晚餐', 3, 1735392000000, 1735392000000, false);
