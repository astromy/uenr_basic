package com.UBSDB.UnerBasicSchoolFinancialSystem.ActivityLog;

import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activity-logs")
public class ActivityLogController {

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private UserService userService;

    @GetMapping("/recent")
    public ResponseEntity<Page<ActivityLogResponse>> getRecentActivityLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<ActivityLog> logs = activityLogService.getRecentActivityLogs(page, size);
        Page<ActivityLogResponse> responses = logs.map(this::convertToResponse);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/active")
    public ResponseEntity<Page<ActivityLogResponse>> getActiveSessions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<ActivityLog> logs = activityLogService.getActiveSessions(page, size);
        Page<ActivityLogResponse> responses = logs.map(this::convertToResponse);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/active/count")
    public ResponseEntity<Long> getActiveSessionCount() {
        return ResponseEntity.ok(activityLogService.getActiveSessionCount());
    }

    @GetMapping("/user")
    public ResponseEntity<List<ActivityLogResponse>> getActivityLogsForCurrentUser(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        if (principal == null) {
            return ResponseEntity.ok(List.of());
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<ActivityLog> logsPage = activityLogService.getRecentActivityLogs(page, size);
        List<ActivityLog> userLogs = logsPage.getContent().stream()
                .filter(log -> log.getUsername() != null && log.getUsername().equals(principal.getName()))
                .collect(Collectors.toList());
        List<ActivityLogResponse> responses = userLogs.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/role")
    public ResponseEntity<Page<ActivityLogResponse>> getActivityLogsByRole(
            @RequestParam String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<ActivityLog> logs = activityLogService.getActivityLogsByRole(role, page, size);
        Page<ActivityLogResponse> responses = logs.map(this::convertToResponse);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/login-stats")
    public ResponseEntity<Map<String, Object>> getLoginStats(
            @RequestParam(defaultValue = "2024") int year,
            @RequestParam(defaultValue = "1") int month) {
        Map<Integer, Long> stats = activityLogService.getLoginStatsPerDay(year, month);
        String monthName = java.time.Month.of(month).name();
        monthName = monthName.charAt(0) + monthName.substring(1).toLowerCase();

        Map<String, Object> response = new java.util.HashMap<>();
        response.put("month", monthName);
        response.put("year", year);
        response.put("stats", stats);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-klass")
    public ResponseEntity<Map<String, String>> getTopKlassByLogins() {

        String topKlass = activityLogService.getTopKlassByLogins();
        Map<String, String> response = new java.util.HashMap<>();
        response.put("topKlass", topKlass);
        return ResponseEntity.ok(response);

    }

    private ActivityLogResponse convertToResponse(ActivityLog log) {
        String fullName = "Unknown";
        if (log.getUser() != null) {
            users user = log.getUser();
            fullName = user.getFirstname() + " " + user.getLastname();
            if (fullName.trim().isEmpty() || fullName.equals("Unknown Unknown")) {
                fullName = user.getUsername();
            }
        }

        return ActivityLogResponse.builder()
                .id(log.getId())
                .username(log.getUsername())
                .fullName(fullName)
                .role(log.getRole())
                .loginTime(log.getLoginTime().toString())
                .logoutTime(log.getLogoutTime() != null ? log.getLogoutTime().toString() : null)
                .ipAddress(log.getIpAddress())
                .userAgent(log.getUserAgent())
                .isActive(log.isActive())
                .activityType(log.getActivityType())
                .description(log.getDescription())
                .build();
    }
}
