package com.zhuru.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Bean转换工具类
 */
public class BeanConvertUtil {

    /**
     * 将源对象转换为目标对象
     *
     * @param source 源对象
     * @param targetClass 目标对象类型
     * @param <T> 目标对象泛型
     * @return 转换后的目标对象
     */
    public static <T> T convert(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("对象转换失败: " + e.getMessage(), e);
        }
    }

    /**
     * 将源对象列表转换为目标对象列表
     *
     * @param sources 源对象列表
     * @param targetClass 目标对象类型
     * @param <S> 源对象泛型
     * @param <T> 目标对象泛型
     * @return 转换后的目标对象列表
     */
    public static <S, T> List<T> convertList(List<S> sources, Class<T> targetClass) {
        if (sources == null) {
            return null;
        }
        return sources.stream()
                .map(source -> convert(source, targetClass))
                .collect(Collectors.toList());
    }
}