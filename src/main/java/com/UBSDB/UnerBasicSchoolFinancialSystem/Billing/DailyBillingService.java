package com.UBSDB.UnerBasicSchoolFinancialSystem.Billing;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill.StudentBill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill.StudentBillRepository;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Notification.NotificationService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFeesRepository;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentRepository;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
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
 * Service responsible for automatic daily billing process.
 * Runs on weekdays only and creates student bills based on class fees
 * and student category matching.
 */
@Slf4j
@Service
public class DailyBillingService {

    @Autowired
    private TermService termService;

    @Autowired
    private klassService klassService;

    @Autowired
    private SchoolFeesRepository schoolFeesRepository;

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BillService billService;

    @Autowired
    private StudentBillRepository studentBillRepository;

    @Autowired
    private NotificationService notificationService;

    /**
     * Performs the daily billing process.
     * For each class, checks if there are school fees for the current term.
     * For each student in the class, if the student's parent category matches
     * the fee specification, creates a StudentBill (if not already exists).
     */
    public void processDailyBilling() {
        log.info("Starting daily billing process at {}", LocalDate.now());

        Term currentTerm = termService.getCurrentTerm();
        if (currentTerm == null) {
            log.warn("No current term found. Skipping daily billing process.");
            return;
        }

        log.info("Current term: {} - {}", currentTerm.getTermName(), currentTerm.getAcademicyear().getAcademicName());

        List<klass> allClasses = klassService.AllKlass();
        int totalBillsCreated = 0;

        for (klass k : allClasses) {
            int classBillsCreated = processClassBilling(k, currentTerm);
            totalBillsCreated += classBillsCreated;
        }

        log.info("Daily billing process completed. Total bills created: {}", totalBillsCreated);
    }

    /**
     * Processes billing for a single class.
     *
     * @param k the class to process
     * @param currentTerm the current academic term
     * @return number of bills created for this class
     */
    private int processClassBilling(klass k, Term currentTerm) {
        int billsCreated = 0;

        // Get school fees for this class and current term
        List<SchoolFees> classFees = schoolFeesRepository.findAll().stream()
                .filter(fee -> fee.getKlass() != null
                        && fee.getKlass().getClassName().equals(k.getClassName())
                        && fee.getTerm() != null
                        && fee.getTerm().getId() != null
                        && fee.getTerm().getId().equals(currentTerm.getId())
                        && fee.isStatus())
                .toList();

        if (classFees.isEmpty()) {
            log.debug("No active school fees found for class: {}", k.getClassName());
            return 0;
        }

        log.debug("Found {} fee(s) for class: {}", classFees.size(), k.getClassName());
     klass  tempKlass=klassService.FindKlass(k.getClassName());
        // Get all active students in this class
        List<Student> students =  studentRepository.findByStudentStatusAndStudentklassClassName("in School", k.getClassName());
        log.info(students.stream().map(Student::getStudentResponse).toString());


        if (students.isEmpty()) {
            log.debug("No active students found in class: {}", k.getClassName());
            return 0;
        }

        log.debug("Found {} active students in class: {}", students.size(), k.getClassName());

        // For each fee, check each student's category and create bills
        for (SchoolFees fee : classFees) {
            // Skip New_Admission fees for daily billing
            if ("New_Admission".equalsIgnoreCase(fee.getFeeType())) {
                log.debug("Skipping New_Admission fee for class: {}", k.getClassName());
                continue;
            }

            Bill bill = fee.getBill();
            if (bill == null) {
                log.warn("Fee {} has no associated bill. Skipping.", fee.getId());
                continue;
            }

            String feeSpecification = fee.getFeeSpecification();
            if (feeSpecification == null || feeSpecification.isEmpty()) {
                log.warn("Fee {} has no fee specification. Skipping.", fee.getId());
                continue;
            }

            for (Student student : students) {
                String studentCategory = student.getParentCategory();
                if (studentCategory == null || studentCategory.isEmpty()) {
                    continue;
                }

                // Check if the fee specification matches the student's parent category
                if (!feeSpecification.equals(studentCategory)) {
                    continue;
                }

                // Check if a StudentBill already exists for this student, bill, and term
                if (studentBillRepository.existsByStudentAndFeesAndTerm(student, fee, currentTerm)) {
                    log.debug("StudentBill already exists for student: {}, bill: {}, term: {}",
                            student.getStudentId(), bill.getId(), currentTerm.getId());
                    continue;
                }

                // Create the StudentBill
                StudentBill studentBill = StudentBill.builder()
                        .student(student)
                        .fees(fee)
                        .term(currentTerm)
                        .build();

                try {
                    studentBillRepository.save(studentBill);
                    billsCreated++;
                    log.debug("Created StudentBill for student: {} in class: {} for fee specification: {}",
                            student.getStudentId(), k.getClassName(), feeSpecification);

                    // Create notification for the student
                    String notificationTitle = "New Bill Assigned";
                    String notificationMessage = String.format(
                            "A new bill of %.2f has been assigned to you for %s - %s. Please check your portal for details.",
                            fee.getAmount(),
                            currentTerm.getTermName(),
                            currentTerm.getAcademicyear().getAcademicName()
                    );
                    notificationService.createNotification(
                            student,
                            notificationTitle,
                            notificationMessage,
                            "BILL",
                            studentBill.getStudBillId().toString(),
                            "StudentBill"
                    );
                } catch (Exception e) {
                    log.error("Failed to create StudentBill for student: {} in class: {}. Error: {}",
                            student.getStudentId(), k.getClassName(), e.getMessage(), e);
                }
            }
        }

        if (billsCreated > 0) {
            log.info("Created {} bill(s) for class: {}", billsCreated, k.getClassName());
        }

        return billsCreated;
    }
}
