package com.UBSDB.UnerBasicSchoolFinancialSystem.Notification;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;

    public Notification createNotification(Student student, String title, String message,
            String type, String relatedId, String relatedType) {
        try {
            Notification notification = Notification.builder()
                    .student(student)
                    .title(title)
                    .message(message)
                    .type(type)
                    .relatedId(relatedId)
                    .relatedType(relatedType)
                    .isRead(false)
                    .build();

            // Also link to user account if available
            if (student != null && student.getStudentId() != null) {
                users user = userService.FindUserById(student.getStudentId());
                if (user != null) {
                    notification.setUser(user);
                }
            }

            return notificationRepository.save(notification);
        } catch (Exception e) {
            log.error("Failed to create notification: {}", e.getMessage(), e);
            return null;
        }
    }

    public Notification createNotificationForUser(users user, String title, String message,
            String type, String relatedId, String relatedType) {
        try {
            Notification notification = Notification.builder()
                    .user(user)
                    .title(title)
                    .message(message)
                    .type(type)
                    .relatedId(relatedId)
                    .relatedType(relatedType)
                    .isRead(false)
                    .build();

            return notificationRepository.save(notification);
        } catch (Exception e) {
            log.error("Failed to create notification for user: {}", e.getMessage(), e);
            return null;
        }
    }

    public List<Notification> getNotificationsForStudent(Student student) {
        return notificationRepository.findByStudentOrderByCreatedAtDesc(student);
    }

    public List<Notification> getNotificationsForUser(users user) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public List<Notification> getUnreadNotificationsForStudent(Student student) {
        return notificationRepository.findByStudentAndIsReadOrderByCreatedAtDesc(student, false);
    }

    public List<Notification> getUnreadNotificationsForUser(users user) {
        return notificationRepository.findByUserAndIsReadOrderByCreatedAtDesc(user, false);
    }

    public long getUnreadCountForStudent(Student student) {
        return notificationRepository.countByStudentAndIsRead(student, false);
    }

    public long getUnreadCountForUser(users user) {
        return notificationRepository.countByUserAndIsRead(user, false);
    }

    public Notification markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification != null) {
            notification.setRead(true);
            return notificationRepository.save(notification);
        }
        return null;
    }

    public void markAllAsReadForStudent(Student student) {
        List<Notification> unread = notificationRepository.findByStudentAndIsReadOrderByCreatedAtDesc(student, false);
        unread.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(unread);
    }

    public void markAllAsReadForUser(users user) {
        List<Notification> unread = notificationRepository.findByUserAndIsReadOrderByCreatedAtDesc(user, false);
        unread.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(unread);
    }
}
