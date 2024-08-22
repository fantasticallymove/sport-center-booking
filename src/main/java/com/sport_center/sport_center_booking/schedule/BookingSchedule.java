package com.sport_center.sport_center_booking.schedule;

import com.sport_center.sport_center_booking.enums.TimePeriodCode;
import com.sport_center.sport_center_booking.service.IBookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
@Slf4j
public class BookingSchedule {

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    private final IBookingService nanGangSportsCenterBookingImpl;

    private final IBookingService daTongSportCenterBookingImpl;

    private final IBookingService neiHuSportCenterBookingImpl;

    private final IBookingService xinYiSportsCenterBookingImpl;

    public BookingSchedule(IBookingService nanGangSportsCenterBookingImpl,
                           IBookingService daTongSportCenterBookingImpl,
                           IBookingService neiHuSportCenterBookingImpl,
                           IBookingService xinYiSportsCenterBookingImpl) {
        this.nanGangSportsCenterBookingImpl = nanGangSportsCenterBookingImpl;
        this.daTongSportCenterBookingImpl = daTongSportCenterBookingImpl;
        this.neiHuSportCenterBookingImpl = neiHuSportCenterBookingImpl;
        this.xinYiSportsCenterBookingImpl = xinYiSportsCenterBookingImpl;
    }

    /**
     * Session心跳排程
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void executeKeepAlive() {
        executeFetchQidInfo();
        if (isAroundSkipTimeRange()) {
            log.info("[BookingSchedule] skip executeKeepAlive");
            return;
        }

        List<Callable<Void>> list = new ArrayList<>();
        list.add(() -> {
            nanGangSportsCenterBookingImpl.keepAlive();
            return null;
        });
        list.add(() -> {
            daTongSportCenterBookingImpl.keepAlive();
            return null;
        });
        list.add(() -> {
            neiHuSportCenterBookingImpl.keepAlive();
            return null;
        });
        list.add(() -> {
            xinYiSportsCenterBookingImpl.keepAlive();
            return null;
        });

        try {
            List<Future<Void>> futures = executorService.invokeAll(list);
            for (Future<Void> future : futures) {
                future.get();
            }
        } catch (Exception exception) {
            log.info("[BookingSchedule] throws an exception.", exception);
        }
    }

    /**
     * 自動預約排程
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void executeBooking() {
        log.info("[BookingSchedule] start executeBooking.");
        try {
            // Send requests asynchronously
            CompletableFuture<Void> responseFuture1 = CompletableFuture.runAsync(() -> {
                try {
                    nanGangSportsCenterBookingImpl.doBooking(88, 8);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });
            CompletableFuture<Void> responseFuture2 = CompletableFuture.runAsync(() -> {
                try {
                    daTongSportCenterBookingImpl.doBooking(0, 0);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });
            CompletableFuture<Void> responseFuture3 = CompletableFuture.runAsync(() -> {
                try {
                    neiHuSportCenterBookingImpl.doBooking(87, 18);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });
            CompletableFuture<Void> responseFuture4 = CompletableFuture.runAsync(() -> {
                try {
                    xinYiSportsCenterBookingImpl.doBooking(0, 0);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });

            // Combine futures and handle completion
            CompletableFuture<Void> allOf = CompletableFuture.allOf(responseFuture1, responseFuture2, responseFuture3, responseFuture4);

            allOf.thenRun(() -> {
                try {
                    responseFuture1.get();
                    responseFuture2.get();
                    responseFuture3.get();
                    responseFuture4.get();
                } catch (Exception e) {
                    log.info("[BookingSchedule] error.", e);
                }
            }).exceptionally(ex -> {
                log.info("[BookingSchedule] exceptionally error.", ex);
                System.exit(1);
                return null;
            });
        } catch (Exception exception) {
            log.info("[BookingSchedule] throws an exception.", exception);
        }
    }

    /**
     * 自動抓取場地碼排程
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void executeFetchQidInfo() {
        log.info("[BookingSchedule] start executeFetchQidInfo.");
        try {
            // Send requests asynchronously
            CompletableFuture<Void> responseFuture1 = CompletableFuture.runAsync(() -> {
                try {
                    nanGangSportsCenterBookingImpl.fetchQidInfo(TimePeriodCode.NIGHT);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });
            CompletableFuture<Void> responseFuture2 = CompletableFuture.runAsync(() -> {
                try {
                    daTongSportCenterBookingImpl.fetchQidInfo(TimePeriodCode.NIGHT);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });
            CompletableFuture<Void> responseFuture3 = CompletableFuture.runAsync(() -> {
                try {
                    neiHuSportCenterBookingImpl.fetchQidInfo(TimePeriodCode.NIGHT);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });
            CompletableFuture<Void> responseFuture4 = CompletableFuture.runAsync(() -> {
                try {
                    xinYiSportsCenterBookingImpl.fetchQidInfo(TimePeriodCode.NIGHT);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });

            // Combine futures and handle completion
            CompletableFuture<Void> allOf = CompletableFuture.allOf(responseFuture1, responseFuture2, responseFuture3, responseFuture4);

            allOf.thenRun(() -> {
                try {
                    responseFuture1.get();
                    responseFuture2.get();
                    responseFuture3.get();
                    responseFuture4.get();
                } catch (Exception e) {
                    log.info("[BookingSchedule] error.", e);
                }
            }).exceptionally(ex -> {
                log.info("[BookingSchedule] exceptionally error.", ex);
                System.exit(1);
                return null;
            });
        } catch (Exception exception) {
            log.info("[BookingSchedule] throws an exception.", exception);
        }
    }

    /**
     * 是否在不執行Session續約的期間內, 這段時間伺服器刷新很大會容易害執行緒卡死, 故略過執行
     */
    private boolean isAroundSkipTimeRange() {
        LocalTime now = LocalTime.now();
        LocalTime targetTimeStart = LocalTime.of(23, 58, 0);
        LocalTime targetTimeEnd = LocalTime.of(23, 59, 59);
        return now.isAfter(targetTimeStart) && now.isBefore(targetTimeEnd);
    }
}
