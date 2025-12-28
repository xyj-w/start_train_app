package com.zhuru.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * 日期工具类
 */
public class DateUtil {

    /**
     * 默认日期格式：yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 格式化日期为字符串
     *
     * @param date 日期
     * @return 格式化后的字符串
     */
    public static String format(LocalDate date) {
        return format(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 格式化日期为指定格式的字符串
     *
     * @param date   日期
     * @param format 格式
     * @return 格式化后的字符串
     */
    public static String format(LocalDate date, String format) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }

    /**
     * 解析字符串为日期
     *
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static LocalDate parse(String dateStr) {
        return parse(dateStr, DEFAULT_DATE_FORMAT);
    }

    /**
     * 解析字符串为指定格式的日期
     *
     * @param dateStr 日期字符串
     * @param format  格式
     * @return 日期
     */
    public static LocalDate parse(String dateStr, String format) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(dateStr, formatter);
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期
     */
    public static LocalDate now() {
        return LocalDate.now();
    }

    /**
     * 获取指定日期所在周的第一天（周一）
     *
     * @param date 日期
     * @return 周的第一天
     */
    public static LocalDate getFirstDayOfWeek(LocalDate date) {
        return date.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
    }

    /**
     * 获取指定日期所在周的最后一天（周日）
     *
     * @param date 日期
     * @return 周的最后一天
     */
    public static LocalDate getLastDayOfWeek(LocalDate date) {
        return date.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));
    }
}