package com.sport_center.sport_center_booking.util;

import com.sport_center.sport_center_booking.enums.ActionEnum;
import com.sport_center.sport_center_booking.enums.SportCenterSettingEnum;
import com.sport_center.sport_center_booking.request.SportCenterRequest;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

import static com.sport_center.sport_center_booking.util.DateUtil.getBookingTime;
import static com.sport_center.sport_center_booking.util.DateUtil.getKeepAliveTime;

@Slf4j
public final class HttpHandler {

    private HttpHandler() {
    }

    public static Response doRequest(SportCenterRequest requestDto, String domainUrl, ActionEnum actionEnum, SportCenterSettingEnum sportCenterSettingEnum) {

        //創建請求實例
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(180000L, TimeUnit.MILLISECONDS);
        client.setConnectTimeout(180000L, TimeUnit.MILLISECONDS);
        client.setWriteTimeout(180000L, TimeUnit.MILLISECONDS);
        String suffixUrl;
        String requestUrl;
        Request request;
        switch (actionEnum) {
            case KEEP_ALIVE:
                suffixUrl = prepareKeepAliveParameter(requestDto, sportCenterSettingEnum.getKeepAliveDay());
                requestUrl = domainUrl + suffixUrl;
                // 創建 Request 物件
                request = new Request.Builder().addHeader("Cookie", "ASP.NET_SessionId=" + sportCenterSettingEnum.getAspToken()).url(requestUrl).build();
                log.info("[{}] request:{}", sportCenterSettingEnum.getChName(), requestUrl);
                try {
                    return client.newCall(request).execute();
                } catch (Exception e) {
                    log.error("[{}] throws an exception.", sportCenterSettingEnum.getChName(), e);
                }
                break;
            case BOOKING:
                suffixUrl = prepareBookingParameter(requestDto, sportCenterSettingEnum.getBookingDay());
                requestUrl = domainUrl + suffixUrl;
                request = new Request.Builder().addHeader("Cookie", "ASP.NET_SessionId=" + sportCenterSettingEnum.getAspToken()).url(requestUrl).build();
                log.info("[{}] request:{}", sportCenterSettingEnum.getChName(), requestUrl);
                try {
                    return client.newCall(request).execute();
                } catch (Exception e) {
                    log.error("[{}] throws an exception.", sportCenterSettingEnum.getChName(), e);
                }
                break;
        }
        return null;
    }


    private static String prepareKeepAliveParameter(SportCenterRequest requestDto, int keepAliveDays) {
        return "?" + "module=" + requestDto.getModule() + "&" + "files=" + requestDto.getFiles() + "&" + "StepFlag=" + requestDto.getStepFlag() + "&" + "PT=" + requestDto.getPT() + "&" + "D=" + getKeepAliveTime(keepAliveDays) + "&" + "D2=" + requestDto.getD2();
    }

    /**
     * sample:
     * https://scr.cyc.org.tw/tp02.aspx?module=net_booking&files=booking_place&StepFlag=25&QPid=88&QTime=8&PT=1&D=2024-09-03
     */
    private static String prepareBookingParameter(SportCenterRequest requestDto, int bookingDays) {
        return "?" + "module=" + requestDto.getModule() + "&" + "files=" + requestDto.getFiles() + "&" + "StepFlag=" + requestDto.getStepFlag() + "&" + "QPid=" + requestDto.getQPid() + "&" + "QTime=" + requestDto.getQTime() + "&" + "PT=" + requestDto.getPT() + "&" + "D=" + getBookingTime(bookingDays);
    }
}
