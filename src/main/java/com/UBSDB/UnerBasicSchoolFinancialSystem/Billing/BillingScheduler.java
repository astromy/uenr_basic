package com.UBSDB.UnerBasicSchoolFinancialSystem.Billing;

import java.time.DayOfWeek;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler for automatic daily billing process.
 * Runs on weekdays only (Monday to Friday) at a configurable time.
 */
@Slf4j
@Component
public class BillingScheduler {

    @Autowired
    private DailyBillingService dailyBillingService;

    /**
     * Scheduled task that runs daily billing process.
     * Skips weekends (Saturday and Sunday).
     * Default schedule: runs at 00:00 (midnight) every day.
     * The weekend check is performed inside the method.
     *
     * Cron expression: "0 0 0 * * ?" means every day at midnight.
     * To change the time, modify the cron expression in application.properties.
     */
    @Scheduled(cron = "${billing.scheduler.cron:0 0 0 * * ?}")
    public void runDailyBilling() {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();

        // Skip weekends (Saturday = 6, Sunday = 7)
//        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
//            log.info("Skipping daily billing process - today is {} (weekend)", dayOfWeek);
//            return;
//        }

        log.info("Running scheduled daily billing for date: {} ({})", today, dayOfWeek);

        try {
            dailyBillingService.processDailyBilling();
        } catch (Exception e) {
            log.error("Error occurred during daily billing process: {}", e.getMessage(), e);
        }
    }
}
