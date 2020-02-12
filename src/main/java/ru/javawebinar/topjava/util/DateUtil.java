package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter SFORMATTER = DateTimeFormatter.ofPattern("DD-MM-yyyy");

    public static String formatHTMLDate(LocalDateTime dateTime) {
        return dateTime == null ? "" : dateTime.format(SFORMATTER);
    }
}
