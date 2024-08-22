package com.sport_center.sport_center_booking.util;

import com.sport_center.sport_center_booking.enums.TimePeriodCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
@Slf4j
public class MailUtil {

    @Value("${smtp.address}")
    private String address;

    @Value("${smtp.password}")
    private String addressPassword;

    public void sendMail(String bookingDayStr, String sportCenterCnName, String content, TimePeriodCode timePeriodCode, String requestTime) {
        String host = "smtp.gmail.com";
        final String username = address;
        final String password = addressPassword;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
            message.setSubject(bookingDayStr + " " + sportCenterCnName + timePeriodCode.getCnName() + "場參數內容 :" + requestTime);
            message.setText(content);
            Transport.send(message);
            log.info("[{}] {}{}場參數內容 sent message successfully...", bookingDayStr, sportCenterCnName, timePeriodCode.getCnName());
        } catch (MessagingException e) {
            log.error("[{}] throws a messagingException.", sportCenterCnName, e);
        }
    }
}
