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
            CompletableFuture<Void> responseFuture1 = CompletableFuture.runAsync(() -> {
                try {
                    daTongSportCenterBookingImpl.doBooking(DA_TONG_55, 10);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });
            CompletableFuture<Void> responseFuture2 = CompletableFuture.runAsync(() -> {
                try {
                    daTongSportCenterBookingImpl.doBooking(DA_TONG_55, 11);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });

            CompletableFuture<Void> responseFuture3 = CompletableFuture.runAsync(() -> {
                try {
                    daTongSportCenterBookingImpl.doBooking(DA_TONG_56, 10);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });
            CompletableFuture<Void> responseFuture4 = CompletableFuture.runAsync(() -> {
                try {
                    daTongSportCenterBookingImpl.doBooking(DA_TONG_56, 11);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });
            CompletableFuture<Void> responseFuture5 = CompletableFuture.runAsync(() -> {
                try {
                    daTongSportCenterBookingImpl.doBooking(DA_TONG_52, 10);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });
            CompletableFuture<Void> responseFuture6 = CompletableFuture.runAsync(() -> {
                try {
                    daTongSportCenterBookingImpl.doBooking(DA_TONG_52, 11);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });


            CompletableFuture<Void> responseFuture7 = CompletableFuture.runAsync(() -> {
                try {
                    daTongSportCenterBookingImpl.doBooking(NEI_HU_1, 10);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });
            CompletableFuture<Void> responseFuture8 = CompletableFuture.runAsync(() -> {
                try {
                    daTongSportCenterBookingImpl.doBooking(NEI_HU_1, 11);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });
            CompletableFuture<Void> responseFuture9 = CompletableFuture.runAsync(() -> {
                try {
                    daTongSportCenterBookingImpl.doBooking(NEI_HU_2, 10);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });
            CompletableFuture<Void> responseFuture10 = CompletableFuture.runAsync(() -> {
                try {
                    daTongSportCenterBookingImpl.doBooking(NEI_HU_2, 11);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });
            CompletableFuture<Void> responseFuture11 = CompletableFuture.runAsync(() -> {
                try {
                    daTongSportCenterBookingImpl.doBooking(NEI_HU_5, 10);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });
            CompletableFuture<Void> responseFuture12 = CompletableFuture.runAsync(() -> {
                try {
                    daTongSportCenterBookingImpl.doBooking(NEI_HU_5, 11);
                } catch (Exception e) {
                    log.info("[BookingSchedule] CompletableFuture error", e);
                }
            });


            // Combine futures and handle completion
            CompletableFuture<Void> allOf =
                    CompletableFuture.allOf(responseFuture1,
                            responseFuture2,
                            responseFuture3,
                            responseFuture4,
                            responseFuture5,
                            responseFuture6,
                            responseFuture7,
                            responseFuture8,
                            responseFuture9,
                            responseFuture10,
                            responseFuture11,
                            responseFuture12);

            allOf.thenRun(() -> {
                try {
                    responseFuture1.get();
                    responseFuture2.get();
                    responseFuture3.get();
                    responseFuture4.get();
                    responseFuture5.get();
                    responseFuture6.get();

                    responseFuture7.get();
                    responseFuture8.get();
                    responseFuture9.get();
                    responseFuture10.get();
                    responseFuture11.get();
                    responseFuture12.get();
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
