package com.space.schedule.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    // Normalize and parse time
    public static LocalTime parseTime(String time) {
        time = normalizeTime(time);
        try {
            return LocalTime.parse(time, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format. Enter HH:mm (24-hour).");
        }
    }

    public static boolean isValidTime(String time) {
        time = normalizeTime(time);
        try {
            LocalTime parsed = LocalTime.parse(time, FORMATTER);
            // additional validation: hours 0-23, minutes 0-59
            int hour = parsed.getHour();
            int minute = parsed.getMinute();
            return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private static String normalizeTime(String time) {
        if (time.matches("\\d:\\d{2}")) { // 9:15 -> 09:15
            return "0" + time;
        }
        return time;
    }
}
