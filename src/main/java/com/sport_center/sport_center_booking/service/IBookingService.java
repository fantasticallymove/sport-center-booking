package com.sport_center.sport_center_booking.service;

import com.sport_center.sport_center_booking.enums.PlatformCodeEnum;
import com.sport_center.sport_center_booking.enums.TimePeriodCode;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface IBookingService {
    void doBooking(PlatformCodeEnum platformCodeEnum, int qTime) throws InterruptedException, ExecutionException;

    void getDomainUrl();

    void keepAlive() throws IOException, InterruptedException;

    void doFetchInfoFromHtml(TimePeriodCode timePeriodCode);

    void fetchQidInfo(TimePeriodCode timePeriodCode);
}
