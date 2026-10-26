package com.UBSDB.UnerBasicSchoolFinancialSystem.ActivityLog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityLogResponse {
    private Long id;
    private String username;
    private String fullName;
    private String role;
    private String loginTime;
    private String logoutTime;
    private String ipAddress;
    private String userAgent;
    private boolean isActive;
    private String activityType;
    private String description;
}
