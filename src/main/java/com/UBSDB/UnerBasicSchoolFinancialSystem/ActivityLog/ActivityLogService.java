package com.UBSDB.UnerBasicSchoolFinancialSystem.ActivityLog;

import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ActivityLogService {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Autowired
    private UserService userService;

    public ActivityLog recordLogin(String username, String role, HttpServletRequest request) {
        try {
            users user = userService.FindUserById(username);
            String ipAddress = getClientIpAddress(request);
            String userAgent = request.getHeader("User-Agent");
            if (userAgent == null || userAgent.isEmpty()) {
                userAgent = "Unknown";
            }
            if (userAgent.length() > 500) {
                userAgent = userAgent.substring(0, 500);
            }

            ActivityLog activityLog = ActivityLog.builder()
                    .user(user)
                    .username(username)
                    .role(role)
                    .loginTime(LocalDateTime.now())
                    .ipAddress(ipAddress)
                    .userAgent(userAgent)
                    .isActive(true)
                    .activityType("Login")
                    .build();

            return activityLogRepository.save(activityLog);
        } catch (Exception e) {
            log.error("Failed to record login activity for user: {}", username, e);
            return null;
        }
    }

    public ActivityLog recordLogout(String username) {
        try {
            List<ActivityLog> activeLogs = activityLogRepository.findByUsernameAndIsActiveOrderByLoginTimeDesc(username, true);
            if (!activeLogs.isEmpty()) {
                ActivityLog latestLog = activeLogs.get(0);
                latestLog.setLogoutTime(LocalDateTime.now());
                latestLog.setActive(false);
                latestLog.setActivityType("LOGOUT");
                return activityLogRepository.save(latestLog);
            }
        } catch (Exception e) {
            log.error("Failed to record logout activity for user: {}", username, e);
        }
        return null;
    }

    public ActivityLog recordAssessmentUpload(String username, String role, String description) {
        try {
            users user = userService.FindUserById(username);
            ActivityLog activityLog = ActivityLog.builder()
                    .user(user)
                    .username(username)
                    .role(role)
                    .loginTime(LocalDateTime.now())
                    .activityType("ASSESSMENT_UPLOAD")
                    .description(description)
                    .isActive(true)
                    .build();
            return activityLogRepository.save(activityLog);
        } catch (Exception e) {
            log.error("Failed to record assessment upload activity for user: {}", username, e);
            return null;
        }
    }

    public Page<ActivityLog> getRecentActivityLogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("loginTime").descending());
        return activityLogRepository.findAllByOrderByLoginTimeDesc(pageable);
    }

    public Page<ActivityLog> getActiveSessions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("loginTime").descending());
        return activityLogRepository.findByIsActiveOrderByLoginTimeDesc(true, pageable);
    }

    public List<ActivityLog> getActivityLogsByRole(String role) {
        return activityLogRepository.findByRoleOrderByLoginTimeDesc(role);
    }

    public Page<ActivityLog> getActivityLogsByRole(String role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("loginTime").descending());
        return activityLogRepository.findByRoleOrderByLoginTimeDesc(role, pageable);
    }

    public List<ActivityLog> getActivityLogsByDateRange(LocalDateTime start, LocalDateTime end) {
        return activityLogRepository.findByLoginTimeBetweenOrderByLoginTimeDesc(start, end);
    }

    public long getActiveSessionCount() {
        return activityLogRepository.countByIsActive(true);
    }

    public long deleteOldLogs(LocalDateTime cutoffTime) {
        return activityLogRepository.deleteByLoginTimeBefore(cutoffTime);
    }

    public Map<Integer, Long> getLoginStatsPerDay
            (int year, int month) {
       List<Object[]> results =
                 activityLogRepository .countLoginsPerDay(year, month);

        if (results==null){

            return null ;
        }else{
        Map<Integer, Long> stats = new LinkedHashMap<>();
        int daysInMonth;
        daysInMonth = LocalDate.ofYearDay(year, month).lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            stats.put(day, 0L);
        }

        for (Object[] result : results) {
            java.sql.Date sqlDate = (java.sql.Date) result[0];
            LocalDate day = sqlDate.toLocalDate();
            Long count = (Long) result[1];
            stats.put(day.getDayOfMonth(), count);
        }
        return stats;}
    }

    public String getTopKlassByLogins() {
        List<Object[]> results = activityLogRepository.countLoginsByUser();
        if (results.isEmpty()) {
            return "No data available";
        }
        Object[] topUserResult = results.get(0);
        users topUser = (users) topUserResult[0];
        Long loginCount = (Long) topUserResult[1];
        return topUser.getFirstname() + " " +
                topUser.getLastname() + " (" + loginCount + " logins)";
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0].trim();
        }
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = "Unknown";
        }
        return ipAddress;
    }
}
