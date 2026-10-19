package com.UBSDB.UnerBasicSchoolFinancialSystem.Notification;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByStudentOrderByCreatedAtDesc(Student student);
    List<Notification> findByUserOrderByCreatedAtDesc(users user);
    List<Notification> findByStudentAndIsReadOrderByCreatedAtDesc(Student student, boolean isRead);
    List<Notification> findByUserAndIsReadOrderByCreatedAtDesc(users user, boolean isRead);
    long countByStudentAndIsRead(Student student, boolean isRead);
    long countByUserAndIsRead(users user, boolean isRead);
}
