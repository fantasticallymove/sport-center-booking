package com.sport_center.sport_center_booking.enums;

import lombok.Getter;

/**
 * 運動中心參數設定
 */
@Getter
public enum SportCenterSettingEnum {

    NEI_HU("yjuh3omedkyoo4jykbefrti5", "12", "內湖", OperatorDomainEnum.CHING_YOUNG_CORP, true, 13, 14),
    NAN_GANG("yjuh3omedkyoo4jykbefrti5", "02", "南港", OperatorDomainEnum.CHING_YOUNG_CORP, true, 12, 13),
    DA_TONG("gim2mozdkg3gjcq3ik5b2kcd", "02", "大同", OperatorDomainEnum.WDYG_SPORT, true, 6, 7),
    XIN_YI("r1bzgilymxewhd0e2zcln0gi", "03", "信義", OperatorDomainEnum.X_PORTS, true, 12, 13),
    DA_AN("", "02", "大安", OperatorDomainEnum.CHANG_JIA_ENGINEER_CORP, false, 0, 0);

    SportCenterSettingEnum(String aspToken, String code, String chName, OperatorDomainEnum operatorDomainEnum,
                           boolean autoBookingEnabled, int keepAliveDay, int bookingDay) {
        this.aspToken = aspToken;
        this.code = code;
        this.chName = chName;
        this.operatorDomainEnum = operatorDomainEnum;
        this.autoBookingEnabled = autoBookingEnabled;
        this.keepAliveDay = keepAliveDay;
        this.bookingDay = bookingDay;
    }

    private final String aspToken;
    private final boolean autoBookingEnabled;
    private final String code;
    private final String chName;
    private final OperatorDomainEnum operatorDomainEnum;
    private final Integer keepAliveDay;
    private final Integer bookingDay;

}
