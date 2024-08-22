package com.sport_center.sport_center_booking.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtil {

    private DateUtil() {
    }

    public static String getKeepAliveTime(int keepAliveDays) {
        LocalDate today = LocalDate.now();
        //防止跨日天數會瞬間變 預扣一天回來
        LocalDate futureDate = today.plusDays(keepAliveDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(futureDate);
    }

    public static String getBookingTime(int bookingDays) {
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(bookingDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(futureDate);
    }
}
