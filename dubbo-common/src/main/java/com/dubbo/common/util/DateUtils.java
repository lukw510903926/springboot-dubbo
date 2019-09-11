package com.dubbo.common.util;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2019-09-09 21:46
 */
@Slf4j
public class DateUtils {

    private DateUtils() {
    }

    /**
     * 时间
     */
    public static final String DEFAULT_DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    /**
     * 天
     */
    public static final String DEFAULT_DAY_FORMATTER = "yyyy-MM-dd";

    /**
     * 天
     */
    public static final String DAY_FORMATTER = "yyyyMMdd";

    /**
     * @param date 时间
     * @return date
     */
    public static Date parseDate(String date) {

        return parseDate(date, DEFAULT_DATE_FORMATTER);
    }

    /**
     * @param date    时间
     * @param pattern 格式
     * @return date
     */
    public static Date parseDate(String date, String pattern) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        return fromLocalDateTime(localDateTime);
    }

    /**
     * @param timestamp 时间戳
     * @param format    格式
     * @return string
     */
    public static String formatDate(long timestamp, String format) {

        LocalDateTime localDateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * @param date   时间
     * @param format 格式
     * @return string
     */
    public static String formatDate(Date date, String format) {
        LocalDateTime localDateTime = fromDate(date);
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 获取时间范围内的日期
     *
     * @param startDay 开始日期
     * @param endDay   结束日期
     * @return list
     */
    public static List<String> getDays(String startDay, String endDay) {

        List<String> list = new ArrayList<>();
        LocalDate start = LocalDate.parse(startDay, DateTimeFormatter.ofPattern(DEFAULT_DAY_FORMATTER));
        LocalDate end = LocalDate.parse(endDay, DateTimeFormatter.ofPattern(DEFAULT_DAY_FORMATTER));
        try {
            while (start.compareTo(end) < 0) {
                list.add(start.format(DateTimeFormatter.ofPattern(DEFAULT_DAY_FORMATTER)));
                start = start.plusDays(1);
            }
            list.add(end.format(DateTimeFormatter.ofPattern(DEFAULT_DAY_FORMATTER)));
        } catch (Exception e) {
            log.error(" startDay : {}  endDay : {} 传入时间有误,异常为 : {}", startDay, endDay, e);
        }
        return list;
    }

    /**
     * 获取指定格式的当前时间字符串
     *
     * @param format 格式
     * @return string
     */
    public static String getCurrentTime(String format) {

        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 获取指定天数前的日期
     *
     * @param days 天数
     * @return date
     */
    public static Date getDateBeforeDays(int days) {

        LocalDateTime localDateTime = LocalDateTime.now().minusDays(days);
        return fromLocalDateTime(localDateTime);
    }

    /**
     * localDateTime 转为 date
     *
     * @param localDateTime localDateTime
     * @return date
     */
    public static Date fromLocalDateTime(LocalDateTime localDateTime) {

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * date 转为 LocalDateTime
     *
     * @param date date
     * @return LocalDateTime
     */
    public static LocalDateTime fromDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * 获取指定月前的第一天
     *
     * @param month 月
     * @return date
     */
    public static Date getFirstDayBeforeMonth(int month) {

        LocalDate localDate = LocalDate.now().minusMonths(month);
        LocalDateTime dateTime = LocalDateTime.of(localDate.getYear(), localDate.getMonth(), 1, 0, 0, 0);
        return fromLocalDateTime(dateTime);
    }

    /**
     * 获取指定月前的最后一天
     *
     * @param month 月
     * @return date
     */
    public static Date getEndDayBeforeMonth(int month) {

        LocalDate localDate = LocalDate.now().minusMonths(month);
        //是否闰年
        boolean leapYear = LocalDate.now().isLeapYear();
        int length = localDate.getMonth().length(leapYear);
        LocalDateTime dateTime = LocalDateTime.of(localDate.getYear(), localDate.getMonth(), length, 23, 59, 59);
        return fromLocalDateTime(dateTime);
    }
}
