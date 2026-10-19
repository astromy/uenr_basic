package com.UBSDB.UnerBasicSchoolFinancialSystem.ActivityLog;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler for cleaning up old activity logs.
 * Runs daily at midnight and deletes logs older than the configured retention period.
 */
@Slf4j
@Component
public class ActivityLogCleanupScheduler {

    @Autowired
    private ActivityLogService activityLogService;

    @Value("${activity.log.retention.days:1}")
    private int retentionDays;

    /**
     * Scheduled task that runs daily at midnight to delete old activity logs.
     * Default retention period is 1 day.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanupOldLogs() {
        try {
            LocalDateTime cutoffTime = LocalDateTime.now().minusDays(retentionDays);
            long deletedCount = activityLogService.deleteOldLogs(cutoffTime);
            log.info("Activity log cleanup completed. Deleted {} logs older than {} days",
                    deletedCount, retentionDays);
        } catch (Exception e) {
            log.error("Error occurred during activity log cleanup: {}", e.getMessage(), e);
        }
    }
}
