package com.sport_center.sport_center_booking.service.impl;

import com.sport_center.sport_center_booking.enums.SportCenterSettingEnum;
import com.sport_center.sport_center_booking.service.IBookingService;
import com.sport_center.sport_center_booking.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DaTongSportCenterBookingImpl extends SportCenterBookingImpl implements IBookingService {

    public DaTongSportCenterBookingImpl(MailUtil mailUtil, @Value("${sport.center.username}") String username) {
        super(mailUtil, username);
        this.SETTING = SportCenterSettingEnum.DA_TONG;
    }
}
