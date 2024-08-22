package com.sport_center.sport_center_booking.enums;

import lombok.Getter;

/**
 * 營運單位資訊列舉
 */
@Getter
public enum OperatorDomainEnum {
    CHANG_JIA_ENGINEER_CORP("長佳機電工程股份有限公司", "https://www.cjcf.com.tw/CG{code}.aspx"),
    X_PORTS("全越運動事業股份有限公司", "https://xs.teamxports.com/xs{code}.aspx"),
    CHING_YOUNG_CORP("中國青年救國團", "https://scr.cyc.org.tw/tp{code}.aspx"),
    WDYG_SPORT("舞動陽光", "https://bwd.xuanen.com.tw/wd{code}.aspx");

    OperatorDomainEnum(String cnName, String domainUrl) {
        this.cnName = cnName;
        this.domainUrl = domainUrl;
    }

    private final String cnName;
    private final String domainUrl;
}
