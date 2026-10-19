package com.UBSDB.UnerBasicSchoolFinancialSystem.Billing;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Course.Course;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Course.CourseService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.MountedCourse.MountedCourse;
import com.UBSDB.UnerBasicSchoolFinancialSystem.MountedCourse.MountedCourseService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Notification.NotificationService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service responsible for sending weekly course notifications to students.
 * Notifies students about the courses they have for the current week.
 */
@Slf4j
@Service
public class WeeklyCourseNotificationService {

    @Autowired
    private TermService termService;

    @Autowired
    private klassService klassService;

    @Autowired
    private MountedCourseService mountedCourseService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private NotificationService notificationService;

    /**
     * Sends weekly course notifications to all students.
     * For each class, gets the mounted courses for the current term and week,
     * and notifies each student about their courses.
     */
    public void sendWeeklyCourseNotifications() {
        log.info("Starting weekly course notification process at {}", LocalDate.now());

        Term currentTerm = termService.getCurrentTerm();
        if (currentTerm == null) {
            log.warn("No current term found. Skipping weekly course notification process.");
            return;
        }

        log.info("Current term: {} - {}", currentTerm.getTermName(), currentTerm.getAcademicyear().getAcademicName());

        List<klass> allClasses = klassService.AllKlass();
        int totalNotificationsSent = 0;

        for (klass k : allClasses) {
            int classNotificationsSent = sendClassCourseNotifications(k, currentTerm);
            totalNotificationsSent += classNotificationsSent;
        }

        log.info("Weekly course notification process completed. Total notifications sent: {}", totalNotificationsSent);
    }

    /**
     * Sends course notifications for a single class.
     *
     * @param k the class to process
     * @param currentTerm the current academic term
     * @return number of notifications sent for this class
     */
    private int sendClassCourseNotifications(klass k, Term currentTerm) {
        int notificationsSent = 0;

        // Get mounted courses for this class and current term
        List<MountedCourse> mountedCourses = mountedCourseService.getActiveMountedCoursesByClassAndTerm(k, currentTerm);

        if (mountedCourses.isEmpty()) {
            log.debug("No active mounted courses found for class: {}", k.getClassName());
            return 0;
        }

        log.debug("Found {} mounted course(s) for class: {}", mountedCourses.size(), k.getClassName());

        // Get all active students in this class
        List<Student> students = k.getStudents().stream()
                .filter(student -> "in School".equals(student.getStudentStatus()))
                .toList();

        if (students.isEmpty()) {
            log.debug("No active students found in class: {}", k.getClassName());
            return 0;
        }

        // Build course list message
        StringBuilder courseList = new StringBuilder();
        for (MountedCourse mountedCourse : mountedCourses) {
            Course course = mountedCourse.getCourse();
            if (course != null) {
                if (courseList.length() > 0) {
                    courseList.append("\n");
                }
                courseList.append("- ").append(course.getCourseName());
                if (mountedCourse.getTeacher() != null) {
                    courseList.append(" (Teacher: ").append(mountedCourse.getTeacher().getFirstName()).append(")");
                }
            }
        }

        // Send notification to each student
        for (Student student : students) {
            String title = "Weekly Course Schedule";
            String message = String.format(
                    "Dear %s,\n\nHere are your courses for this week (%s - %s):\n\n%s\n\nPlease check your portal for more details.",
                    student.getFirstName(),
                    currentTerm.getTermName(),
                    currentTerm.getAcademicyear().getAcademicName(),
                    courseList.toString()
            );

            try {
                notificationService.createNotification(
                        student,
                        title,
                        message,
                        "COURSE",
                        null,
                        "MountedCourse"
                );
                notificationsSent++;
                log.debug("Sent weekly course notification to student: {} in class: {}",
                        student.getStudentId(), k.getClassName());
            } catch (Exception e) {
                log.error("Failed to send weekly course notification to student: {} in class: {}. Error: {}",
                        student.getStudentId(), k.getClassName(), e.getMessage(), e);
            }
        }

        if (notificationsSent > 0) {
            log.info("Sent {} weekly course notification(s) for class: {}", notificationsSent, k.getClassName());
        }

        return notificationsSent;
    }
}
