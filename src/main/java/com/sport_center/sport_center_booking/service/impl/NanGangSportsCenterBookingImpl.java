package com.sport_center.sport_center_booking.service.impl;

import com.sport_center.sport_center_booking.enums.SportCenterSettingEnum;
import com.sport_center.sport_center_booking.service.IBookingService;
import com.sport_center.sport_center_booking.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NanGangSportsCenterBookingImpl extends SportCenterBookingImpl implements IBookingService {

    public NanGangSportsCenterBookingImpl(MailUtil mailUtil, @Value("${sport.center.username}") String username) {
        super(mailUtil, username);
        this.SETTING = SportCenterSettingEnum.NAN_GANG;
    }

    @Override
    protected String getParseContent(StringBuilder content) {
        return content.toString()
                .replace("<img", "").replace("src=\"img/place02.png\" width=\"88\" height=\"22\" title=", "")
                .replace(";\">", "")
                .replace("\"已被預約\"", "")
                .replace("onclick=\"", "")
                .replace("500", "");
    }
}
