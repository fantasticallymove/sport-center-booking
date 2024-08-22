package com.sport_center.sport_center_booking.request;

import lombok.Data;

@Data
public class SportCenterRequest {
    private String module;
    private String files;
    private Integer StepFlag;

    //場地代碼
    private Integer QPid;

    //時段-小時開頭
    private Integer QTime;
    private Integer PT;

    //時間
    private String D;
    //時段
    private Integer D2;
}
