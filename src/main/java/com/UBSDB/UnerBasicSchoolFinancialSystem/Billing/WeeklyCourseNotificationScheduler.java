package com.UBSDB.UnerBasicSchoolFinancialSystem.Billing;

import java.time.DayOfWeek;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler for weekly course notifications.
 * Runs every Monday to notify students about their weekly course schedule.
 */
@Slf4j
@Component
public class WeeklyCourseNotificationScheduler {

    @Autowired
    private WeeklyCourseNotificationService weeklyCourseNotificationService;

    /**
     * Scheduled task that runs every Monday to send weekly course notifications.
     * Cron expression: "0 0 8 ? * MON" means every Monday at 8:00 AM.
     */
    @Scheduled(cron = "${notification.weekly.course.cron:0 0 8 ? * MON}")
    public void sendWeeklyCourseNotifications() {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();

        log.info("Running scheduled weekly course notification for date: {} ({})", today, dayOfWeek);

        try {
            weeklyCourseNotificationService.sendWeeklyCourseNotifications();
        } catch (Exception e) {
            log.error("Error occurred during weekly course notification process: {}", e.getMessage(), e);
        }
    }
}
