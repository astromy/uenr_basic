package com.UBSDB.UnerBasicSchoolFinancialSystem.Billing;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.Assessment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.AssessmentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.PROMOTION.promote;
import com.UBSDB.UnerBasicSchoolFinancialSystem.PROMOTION.promoteService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentController;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentPromotion;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
@Component
public class PromotionSchedular {
    private @Autowired TermService termService;
    private @Autowired StudentService studentService;
    private @Autowired AssessmentService assessmentService;
    private  @Autowired promoteService promoteService;
    @Scheduled(cron = "${notification.weekly.course.cron:0 0 8 ? * MON}")
        public void Promotion() throws Exception {
            Runnable r1 = () -> {
                Term t = termService.getCurrentTerm();
                try {  List<Student> students = this.studentService.students();
                    students.removeIf((c) -> {
                        return c.getStudentStatus().equalsIgnoreCase("Stopped");
                    });

                    students.stream().forEach((s) -> {
                        s = this.studentService.findStudent(s.getStudentId());
                        if (s.getStudentklass() != null) {
                            int TotalSubject = assessmentService.getAssessmentBasedOnTerm(t, s).get(t).size();
                            Logger.getGlobal().log(Level.INFO, "checking.... " + TotalSubject);

                            float text = (TotalSubject * 100) / 2;
                            Logger.getGlobal().log(Level.INFO, "Starting the process " + text);
                            System.out.println(s.getStudentId() + ".....promotions " + s.getPromotions().size());
                            if (s.getStudentklass() != null && !s.getPromotions().stream().anyMatch((c) -> {
                                return c.getAcademicyear().getId()
                                        == this.termService.getCurrentTerm().getAcademicyear().getId();
                            }) && s.getAssessment().stream()
                                    .filter(c -> c.getTerm().getId() == termService.getCurrentTerm().getId())
                                    .map(Assessment::getTotalScore).reduce(0.0, (a, b) -> a + b) >= text) {
                                promote p = new promote(s.getStudentklass().getClassName()
                                        , this.studentService.promoteStudent_withoutsaving(s),
                                        s,
                                        this.termService.getCurrentTerm().getAcademicyear());
                                Logger.getGlobal().log(Level.INFO, (p.getStudent().getFullName()
                                        + " Previous class -> " + p.getStudent().getStudentklass().getClassName()
                                        + " Class seeking " + p.getNextClass()));

                                try {
                                    p = this.promoteService.savePromotion(p);
                                } catch (Exception var4) {
                                    Exception ex = var4;
                                    Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, (String) null, ex);
                                }
                                Logger.getGlobal().log(Level.INFO, p.getStudent().getFullName()
                                        + " Previous class -> " + p + " Approved "
                                        + p.getNextClass());

                            }else if ((s.getStudentklass().getClassNumber()==3||s.getStudentklass().getClassLevel()
                                    .equals("Kindergaten")||((s.getStudentklass().getClassLevel()
                                    .equals("UpperPrimary"))&&(!s.getStudentklass().getClassLevel()
                                    .equals("6"))||s.getStudentklass().getClassLevel()
                                    .equals("LowerPrimary")||s.getStudentklass()
                                    .getClassLevel().equals("Nursery")))&&!s.getPromotions().stream().filter((c) -> {
                                return c.getAcademicyear().getId()
                                        == this.termService.getCurrentTerm().getAcademicyear().getId();
                            }).findFirst().isPresent()){
                                promote p = new promote(s.getStudentklass().getClassName(),
                                        this.studentService.promoteStudent_withoutsaving(s), s, this.termService.getCurrentTerm().getAcademicyear());
                                Logger.getGlobal().log(Level.INFO, (p.getStudent().getFullName() +
                                        " Previous class -> " + p.getStudent().getStudentklass().getClassName()
                                        + " Class seeking " + p.getNextClass()));

                                try {
                                    p = this.promoteService.savePromotion(p);
                                } catch (Exception var4) {
                                    Exception ex = var4;
                                    Logger
                                            .getLogger(StudentController.class.getName()).log(Level.SEVERE, (String) null, ex);
                                }
                                Logger.getGlobal()
                                        .log(Level.INFO, p.getStudent().getFullName() + "..Exemption... Previous class -> " + p + " Approved " + p.getNextClass());


                            }else
                            {
                                Logger.getGlobal().log(Level.INFO, " Student Promotion has been effected this academic year already");

                            }

                        }
                    });
                } catch (Exception var2) {
                    Exception ex = var2;
                    ex.printStackTrace();
                }

            };
            r1.run();

        }
}
