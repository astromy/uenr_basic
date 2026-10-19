package com.UBSDB.UnerBasicSchoolFinancialSystem.Notification;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsForStudent(@PathVariable String studentId) {
        Student student = studentService.findStudent(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        List<Notification> notifications = notificationService.getNotificationsForStudent(student);
        List<NotificationResponse> responses = notifications.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/student/{studentId}/unread")
    public ResponseEntity<List<NotificationResponse>> getUnreadNotificationsForStudent(@PathVariable String studentId) {
        Student student = studentService.findStudent(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        List<Notification> notifications = notificationService.getUnreadNotificationsForStudent(student);
        List<NotificationResponse> responses = notifications.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/student/{studentId}/unread/count")
    public ResponseEntity<Long> getUnreadCountForStudent(@PathVariable String studentId) {
        Student student = studentService.findStudent(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        long count = notificationService.getUnreadCountForStudent(student);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/student/{studentId}/read/{notificationId}")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable String studentId, @PathVariable Long notificationId) {
        Student student = studentService.findStudent(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        Notification notification = notificationService.markAsRead(notificationId);
        if (notification == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/student/{studentId}/read-all")
    public ResponseEntity<?> markAllNotificationsAsRead(@PathVariable String studentId) {
        Student student = studentService.findStudent(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        notificationService.markAllAsReadForStudent(student);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsForUser(@PathVariable String username) {
        users user = userService.FindUserById(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<Notification> notifications = notificationService.getNotificationsForUser(user);
        List<NotificationResponse> responses = notifications.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{username}/unread")
    public ResponseEntity<List<NotificationResponse>> getUnreadNotificationsForUser(@PathVariable String username) {
        users user = userService.FindUserById(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<Notification> notifications = notificationService.getUnreadNotificationsForUser(user);
        List<NotificationResponse> responses = notifications.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{username}/unread/count")
    public ResponseEntity<Long> getUnreadCountForUser(@PathVariable String username) {
        users user = userService.FindUserById(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        long count = notificationService.getUnreadCountForUser(user);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/user/{username}/read/{notificationId}")
    public ResponseEntity<?> markUserNotificationAsRead(@PathVariable String username, @PathVariable Long notificationId) {
        users user = userService.FindUserById(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Notification notification = notificationService.markAsRead(notificationId);
        if (notification == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/{username}/read-all")
    public ResponseEntity<?> markAllUserNotificationsAsRead(@PathVariable String username) {
        users user = userService.FindUserById(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        notificationService.markAllAsReadForUser(user);
        return ResponseEntity.ok().build();
    }

    private NotificationResponse convertToResponse(Notification notification) {
        String studentName = notification.getStudent() != null
                ? notification.getStudent().getFirstName() + " " + notification.getStudent().getLastName()
                : null;

        String className = notification.getStudent() != null && notification.getStudent().getStudentklass() != null
                ? notification.getStudent().getStudentklass().getClassName()
                : null;

        return NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .type(notification.getType())
                .relatedId(notification.getRelatedId())
                .relatedType(notification.getRelatedType())
                .isRead(notification.isRead())
                .createdAt(notification.getCreatedAt().toString())
                .studentName(studentName)
                .className(className)
                .build();
    }
}
