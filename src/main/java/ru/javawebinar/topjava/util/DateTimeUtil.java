package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter SFORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static boolean isBetweenInclusive(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static String formatHTMLDate(LocalDateTime dateTime) {
        return dateTime == null ? "" : dateTime.format(SFORMATTER);
    }
}
