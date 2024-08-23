package com.sport_center.sport_center_booking.service.impl;

import com.sport_center.sport_center_booking.enums.ActionEnum;
import com.sport_center.sport_center_booking.enums.SportCenterSettingEnum;
import com.sport_center.sport_center_booking.enums.TimePeriodCode;
import com.sport_center.sport_center_booking.request.SportCenterRequest;
import com.sport_center.sport_center_booking.service.IBookingService;
import com.sport_center.sport_center_booking.util.DateUtil;
import com.sport_center.sport_center_booking.util.HttpHandler;
import com.sport_center.sport_center_booking.util.MailUtil;
import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public abstract class SportCenterBookingImpl implements IBookingService {
    public String url;

    public SportCenterSettingEnum SETTING = SportCenterSettingEnum.NEI_HU;

    public final ExecutorService executorService = Executors.newFixedThreadPool(2);

    public final MailUtil mailUtil;

    private String username;

    protected SportCenterBookingImpl(MailUtil mailUtil, String username) {
        this.mailUtil = mailUtil;
        this.username = username;
    }

    @Override
    public void doBooking(int qPid, int qTime) throws InterruptedException, ExecutionException {
        if (url == null) {
            getDomainUrl();
        }
        if (!SETTING.isAutoBookingEnabled()) {
            log.info("[{}] auto booking disabled.", SETTING.getChName());
            return;
        }
        SportCenterRequest requestDto = new SportCenterRequest();
        requestDto.setModule("net_booking");
        requestDto.setFiles("booking_place");
        requestDto.setStepFlag(25);
        requestDto.setQPid(qPid);
        requestDto.setQTime(qTime);
        requestDto.setPT(1);
        List<Callable<Void>> tasks = new ArrayList<>();
        tasks.add(() -> {
            HttpHandler.doRequest(requestDto, url, ActionEnum.BOOKING, SETTING);
            return null;
        });
        //會開啟兩個線程同時搶場地
        List<Future<Void>> futures = executorService.invokeAll(tasks);
        for (Future<Void> future : futures) {
            future.get();
        }
    }

    @Override
    public void keepAlive() throws InterruptedException, IOException {
        if (url == null) {
            getDomainUrl();
        }

        SportCenterRequest requestDto = new SportCenterRequest();
        requestDto.setModule("net_booking");
        requestDto.setFiles("booking_place");
        requestDto.setStepFlag(2);
        requestDto.setPT(1);
        requestDto.setD2(3);

        Response response = HttpHandler.doRequest(requestDto, url, ActionEnum.KEEP_ALIVE, SETTING);

        if (response != null && response.priorResponse() == null) {
            String body = response.body().string();
            if (body.contains(username)) {
                log.info("[{}] keepAlive:{} " + username + " welcome.", SETTING.getChName(), true);
            } else {
                //寄送重新登入通知, 可以加一個line 通知在此
                log.error("[{}] keepAlive:{}", SETTING.getChName(), false);
            }
        } else {
            //寄送重新登入通知, 可以加一個line 通知在此
            log.error("[{}] keepAlive:{}", SETTING.getChName(), false);
        }

        long randomSleepOffset = (long) (30000 * Math.random());
        //最小連接間隔5秒
        if (randomSleepOffset < 5000L) {
            randomSleepOffset = 5000L;
        }
        Thread.sleep(randomSleepOffset);
    }

    @Override
    public void getDomainUrl() {
        if (this.url != null) {
            return;
        }
        this.url = SETTING.getOperatorDomainEnum().getDomainUrl().replace("{code}", SETTING.getCode());
    }

    /**
     * 爬蟲獲取網站 晚上場地代號
     * 需要更改時段請修改
     */
    @Override
    public void doFetchInfoFromHtml(TimePeriodCode timePeriodCode) {
        try {

            String url = this.url + "?module=net_booking&files=booking_place&StepFlag=2&PT=1&D=" +
                    DateUtil.getBookingTime(SETTING.getBookingDay()) + "&D2=" + timePeriodCode.getCode();

            log.info("[{}] 爬蟲 requestParam:{}", SETTING.getChName(), url);

            LocalDateTime requestTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd hh:mm:ss");

            // 連接到網站並解析 HTML 文檔
            Document doc = Jsoup.connect(url).timeout(180000).cookie("ASP.NET_SessionId", SETTING.getAspToken()).get();

            StringBuilder content = new StringBuilder();

            int skipFirst = 0;

            Elements allElements = doc.getAllElements();
            for (int i = 1; i < doc.getAllElements().size(); i++) {
                if (allElements.get(i).tag().getName().equals("tbody")) {

                    //跳過前兩個TBody
                    if (skipFirst < 2) {
                        skipFirst++;
                        continue;
                    }

                    List<Node> childNodes = allElements.get(i).childNodes();
                    for (int ii = 2; ii < childNodes.size(); ii++) {
                        if (childNodes.get(ii) instanceof Element) {
                            List<Node> nodes = childNodes.get(ii).childNodes();
                            for (int iii = 0; iii < nodes.size(); iii++) {
                                if (nodes.get(iii) instanceof Element) {
                                    if ("".equals(nodes.get(iii).childNodes().get(0).toString())) {
                                        continue;
                                    }

                                    if (nodes.get(iii).childNodes().get(0).toString().equals("預約時間：") ||
                                            nodes.get(iii).childNodes().get(0).toString().equals("運動種類：")) {
                                        break;
                                    }

                                    //代表時段開頭
                                    if (nodes.get(iii).childNodes().get(0).toString().contains(":00")) {
                                        content.append("\n\n\n");
                                    }

                                    content.append(nodes.get(iii).childNodes().get(0).toString());

                                    //代表時段開頭
                                    if (nodes.get(iii).childNodes().get(0).toString().contains(":00")) {
                                        content.append(": \n");
                                    }

                                    if (nodes.get(iii).childNodes().get(0).toString().contains(">")) {
                                        content.append("\n");
                                    }
                                }
                            }
                        }
                    }
                }
            }

            String parseContent = getParseContent(content);

            mailUtil.sendMail(DateUtil.getBookingTime(SETTING.getBookingDay()),
                    SETTING.getChName(),
                    parseContent,
                    timePeriodCode,
                    formatter.format(requestTime));
        } catch (Exception exception) {
            log.info("[{}] msg:{} error.", SETTING.getChName(), exception.getMessage(), exception);
        }
    }

    /**
     * 進行爬蟲後場地Html文本解析成明文
     */
    protected String getParseContent(StringBuilder content) {
        return content.toString()
                .replace("<img", "").replace("src=\"img/place02.png\" width=\"88\" height=\"22\" title=", "")
                .replace(";\">", "")
                .replace("\"已被預約\"", "")
                .replace("onclick=\"", "")
                .replace("500", "")
                .replace("if (!window.__cfRLUnblockHandlers) return false;", "");
    }

    /**
     * 爬蟲-捕捉運動中心對應時段的場地碼
     */
    @Override
    public void fetchQidInfo(TimePeriodCode timePeriodCode) {
        if (url == null) {
            getDomainUrl();
        }
        doFetchInfoFromHtml(timePeriodCode);
    }
}
