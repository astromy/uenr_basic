package com.UBSDB.UnerBasicSchoolFinancialSystem.Notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {
    private Long id;
    private String title;
    private String message;
    private String type;
    private String relatedId;
    private String relatedType;
    private boolean isRead;
    private String createdAt;
    private String studentName;
    private String className;
}
