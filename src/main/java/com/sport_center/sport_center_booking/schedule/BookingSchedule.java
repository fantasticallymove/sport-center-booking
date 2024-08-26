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

import static com.sport_center.sport_center_booking.enums.PlatformCodeEnum.*;

@Component
@Slf4j
public class BookingSchedule {

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    private ExecutorService bookingThreadPool = Executors.newFixedThreadPool(16);

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
    public void executeBooking() throws InterruptedException {
        log.info("[BookingSchedule] start executeBooking.");
        //會有過早啟動的問題 這邊要煞車100ms
        Thread.sleep(100L);
        try {
            CompletableFuture.supplyAsync(() -> {
                try {
                    nanGangSportsCenterBookingImpl.doBooking(NAN_GANG_A, 10);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
                return null;
            }, bookingThreadPool);
            CompletableFuture.supplyAsync(() -> {
                try {
                    nanGangSportsCenterBookingImpl.doBooking(NAN_GANG_B, 10);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
                return null;
            }, bookingThreadPool);
            CompletableFuture.supplyAsync(() -> {
                try {
                    nanGangSportsCenterBookingImpl.doBooking(NAN_GANG_C, 10);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
                return null;
            }, bookingThreadPool);
            CompletableFuture.supplyAsync(() -> {
                try {
                    nanGangSportsCenterBookingImpl.doBooking(NAN_GANG_D, 10);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
                return null;
            }, bookingThreadPool);
            CompletableFuture.supplyAsync(() -> {
                try {
                    nanGangSportsCenterBookingImpl.doBooking(NAN_GANG_E, 10);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
                return null;
            }, bookingThreadPool);
            CompletableFuture.supplyAsync(() -> {
                try {
                    nanGangSportsCenterBookingImpl.doBooking(NAN_GANG_F, 10);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
                return null;
            }, bookingThreadPool);


            CompletableFuture.supplyAsync(() -> {
                try {
                    nanGangSportsCenterBookingImpl.doBooking(NAN_GANG_A, 11);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
                return null;
            }, bookingThreadPool);
            CompletableFuture.supplyAsync(() -> {
                try {
                    nanGangSportsCenterBookingImpl.doBooking(NAN_GANG_B, 11);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
                return null;
            }, bookingThreadPool);
            CompletableFuture.supplyAsync(() -> {
                try {
                    nanGangSportsCenterBookingImpl.doBooking(NAN_GANG_C, 11);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
                return null;
            }, bookingThreadPool);
            CompletableFuture.supplyAsync(() -> {
                try {
                    nanGangSportsCenterBookingImpl.doBooking(NAN_GANG_D, 11);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
                return null;
            }, bookingThreadPool);
            CompletableFuture.supplyAsync(() -> {
                try {
                    nanGangSportsCenterBookingImpl.doBooking(NAN_GANG_E, 11);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
                return null;
            }, bookingThreadPool);
            CompletableFuture.supplyAsync(() -> {
                try {
                    nanGangSportsCenterBookingImpl.doBooking(NAN_GANG_F, 11);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
                return null;
            }, bookingThreadPool);
        } catch (Exception exception) {
            log.info("[BookingSchedule] throws an exception.", exception);
        }
    }

    /**
     * 自動抓取場地碼排程
     */
//    @Scheduled(cron = "0 0 0 * * ?")
    public void executeFetchQidInfo() {
        log.info("[BookingSchedule] start executeFetchQidInfo.");
        try {
            // Send requests asynchronously
            CompletableFuture<Void> responseFuture1 = CompletableFuture.runAsync(() -> {
                try {
                    nanGangSportsCenterBookingImpl.fetchQidInfo(TimePeriodCode.MORNING);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });
            CompletableFuture<Void> responseFuture2 = CompletableFuture.runAsync(() -> {
                try {
                    daTongSportCenterBookingImpl.fetchQidInfo(TimePeriodCode.MORNING);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });
            CompletableFuture<Void> responseFuture3 = CompletableFuture.runAsync(() -> {
                try {
                    neiHuSportCenterBookingImpl.fetchQidInfo(TimePeriodCode.MORNING);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });
            CompletableFuture<Void> responseFuture4 = CompletableFuture.runAsync(() -> {
                try {
                    xinYiSportsCenterBookingImpl.fetchQidInfo(TimePeriodCode.MORNING);
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
                System.exit(1);
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
     * 換日第一分中內也不請求去佔用掉執行緒的數量
     */
    private boolean isAroundSkipTimeRange() {
        LocalTime now = LocalTime.now();
        LocalTime targetTimeStart = LocalTime.of(23, 59, 0);
        LocalTime targetTimeEnd = LocalTime.of(23, 59, 59);

        LocalTime targetTimeStart1 = LocalTime.of(0, 0, 0);
        LocalTime targetTimeEnd1 = LocalTime.of(0, 1, 59);
        return (now.isAfter(targetTimeStart) && now.isBefore(targetTimeEnd))
                ||
                (now.isAfter(targetTimeStart1) && now.isBefore(targetTimeEnd1));
    }
}
