package com.sport_center.sport_center_booking.enums;

import lombok.Getter;

/**
 * 場地代碼
 */
@Getter
public enum PlatformCodeEnum {

    XIN_YI_A(83, SportCenterSettingEnum.XIN_YI, "信義_羽A", true),
    XIN_YI_B(84, SportCenterSettingEnum.XIN_YI, "信義_羽B", true),
    XIN_YI_C(1074, SportCenterSettingEnum.XIN_YI, "信義_羽X", true),
    XIN_YI_D(1075, SportCenterSettingEnum.XIN_YI, "信義_羽D", true),

    DA_TONG_51(1112, SportCenterSettingEnum.DA_TONG, "大同_羽5-1", true),
    DA_TONG_52(1113, SportCenterSettingEnum.DA_TONG, "大同_羽5-2", true),
    DA_TONG_53(1114, SportCenterSettingEnum.DA_TONG, "大同_羽5-3", true),
    DA_TONG_54(1115, SportCenterSettingEnum.DA_TONG, "大同_羽5-4", true),
    DA_TONG_55(1116, SportCenterSettingEnum.DA_TONG, "大同_羽5-5", true),
    DA_TONG_56(1163, SportCenterSettingEnum.DA_TONG, "大同_羽5-6", true),
    DA_TONG_57(1164, SportCenterSettingEnum.DA_TONG, "大同_羽5-7", true),
    DA_TONG_58(1165, SportCenterSettingEnum.DA_TONG, "大同_羽5-8", true),
    DA_TONG_59(1166, SportCenterSettingEnum.DA_TONG, "大同_羽5-9", true),

    NEI_HU_1(83, SportCenterSettingEnum.NEI_HU, "內湖_羽1", true),
    NEI_HU_2(84, SportCenterSettingEnum.NEI_HU, "內湖_羽2", true),
    NEI_HU_5(87, SportCenterSettingEnum.NEI_HU, "內湖_羽5", true),
    NEI_HU_6(88, SportCenterSettingEnum.NEI_HU, "內湖_羽6", true),

    //羽3,4盡量別搶這兩個是常駐教練的場地運動中心內部在用的
    NEI_HU_3(1074, SportCenterSettingEnum.NEI_HU, "內湖_羽3", true),
    NEI_HU_4(1075, SportCenterSettingEnum.NEI_HU, "內湖_羽4", true),

    NAN_GANG_A(83, SportCenterSettingEnum.NAN_GANG, "南港_羽A", true),
    NAN_GANG_B(84, SportCenterSettingEnum.NAN_GANG, "南港_羽B", true),
    NAN_GANG_C(1074, SportCenterSettingEnum.NAN_GANG, "南港_羽C", true),
    NAN_GANG_D(1075, SportCenterSettingEnum.NAN_GANG, "南港_羽D", true),
    NAN_GANG_E(87, SportCenterSettingEnum.NAN_GANG, "南港_羽E", true),
    NAN_GANG_F(88, SportCenterSettingEnum.NAN_GANG, "南港_羽F", true);

    PlatformCodeEnum(int code, SportCenterSettingEnum sportCenter, String cnName, boolean dataVerified) {
        this.code = code;
        this.sportCenter = sportCenter;
        this.cnName = cnName;
        this.dataVerified = dataVerified;
    }

    private final int code;
    private final SportCenterSettingEnum sportCenter;
    private final String cnName;

    //是否曾經有在爬蟲上實際獲取過的數據
    private final boolean dataVerified;
}
