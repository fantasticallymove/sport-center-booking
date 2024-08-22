package com.sport_center.sport_center_booking.enums;

import lombok.Getter;

/**
 * 時段代號
 */
@Getter
public enum TimePeriodCode {
    MORNING(1, "早上"),
    AFTERNOON(2, "下午"),
    NIGHT(3, "晚上");

    TimePeriodCode(int code, String cnName) {
        this.code = code;
        this.cnName = cnName;
    }

    private final int code;
    private final String cnName;
}
