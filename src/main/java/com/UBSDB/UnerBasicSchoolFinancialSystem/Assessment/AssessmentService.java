/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment;


import com.UBSDB.UnerBasicSchoolFinancialSystem.ActivityLog.ActivityLogService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Excel.ExcelServices;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Notification.NotificationService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.RestController.student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.TKS.TeachrKlassSubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import static java.lang.System.out;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author enock
 */
@Slf4j
@Service
public class AssessmentService {
    @Autowired
    private AssessmentRepo AR;
    @Autowired
    private klassService kS;
    @Autowired
    private StudentService SS;
    @Autowired
    private TeachrKlassSubjectService TKSs;
    @Autowired
    private TermService TS;
    private @Autowired ExcelServices ES;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ActivityLogService activityLogService;

    public AssessmentService() {
    }

    public String saveAssessment(Assessment assessment) {
        return saveAssessment(assessment, null);
    }

    public String saveAssessment(Assessment assessment, String username) {
        try {
            Optional<Assessment> CheckAssessmnt =this.findAssessmentByDeatials(assessment);
            log.info(CheckAssessmnt.isPresent()+":::is present");
            if (CheckAssessmnt.isPresent()) {
                AssessmentService.log.info("Updating student assessment because it exist already ....No duplication");
                Assessment tempAssessment = (Assessment)this.findAssessmentByDeatials(assessment).get();
                System.out.println(tempAssessment.getStudent().getFirstName() + tempAssessment.getSubject().getSubjectName() + "found==> updated");
                tempAssessment.setExamScores(assessment.getExamScores());
                tempAssessment.setGrade(assessment.getGrade());
                tempAssessment.setTotalScore(assessment.getTotalScore());
                tempAssessment.setTotalTestScoreAverage(assessment.getTotalTestScoreAverage());
                tempAssessment.setTotalTestScore(assessment.getTotalTestScore());
                tempAssessment.setPosition(assessment.getPosition());
                tempAssessment.setHighestScore(assessment.getHighestScore());
                Assessment savedAssessment = (Assessment)this.AR.save(tempAssessment);
                System.out.println(savedAssessment.getStudent().getIdSpecia());

                // Create notification for assessment update
                createAssessmentNotification(savedAssessment, "updated");

                // Log assessment update activity
                if (username != null && !username.isEmpty()) {
                    String description = String.format("Assessment updated for %s - %s (%s)",
                            savedAssessment.getStudent().getStudentklass().getClassName(),
                            savedAssessment.getSubject().getSubjectName(),
                            savedAssessment.getTerm().getTermName());
                    activityLogService.recordAssessmentUpload(username, "TEACHER", description);
                }

                return "Updated Successfully";

        } else {
                assessment.setStudentClass(assessment.getStudent().getStudentklass());
                assessment.setHeadMasterApproval("false");
                Assessment savedAssessment = this.AR.save(assessment);
                System.out.println(savedAssessment.getStudent().getFirstName() + savedAssessment.getSubject().getSubjectName() + "found==>saved");

                // Create notification for new assessment
                createAssessmentNotification(savedAssessment, "loaded");

                // Log assessment upload activity
                if (username != null && !username.isEmpty()) {
                    String description = String.format("Assessment uploaded for %s - %s (%s)",
                            savedAssessment.getStudent().getStudentklass().getClassName(),
                            savedAssessment.getSubject().getSubjectName(),
                            savedAssessment.getTerm().getTermName());
                    activityLogService.recordAssessmentUpload(username, "TEACHER", description);
                }

                return "Successfully saved";
            }
        } catch (Exception var3) {
            Exception ex = var3;
            ex.printStackTrace();
            return " Error " + ex.toString() + ex.getMessage();
        }
    }

    private void createAssessmentNotification(Assessment assessment, String action) {
        try {
            Student student = assessment.getStudent();
            if (student == null || student.getStudentklass() == null) {
                return;
            }

            String title = "Assessment " + action;
            String message = String.format(
                    "Your %s assessment for %s has been %s. Term: %s. Please check your portal for details.",
                    assessment.getSubject() != null ? assessment.getSubject().getSubjectName() : "Unknown",
                    student.getStudentklass().getClassName(),
                    action,
                    assessment.getTerm() != null ? assessment.getTerm().getTermName() : "Unknown"
            );

            notificationService.createNotification(
                    student,
                    title,
                    message,
                    "ASSESSMENT",
                    assessment.getId().toString(),
                    "Assessment"
            );

            // Also notify all students in the same class about the assessment being loaded
            if ("loaded".equals(action) && student.getStudentklass() != null) {
                List<Student> classStudents = student.getStudentklass().getStudents().stream()
                        .filter(s -> "in School".equals(s.getStudentStatus()))
                        .toList();

                for (Student classStudent : classStudents) {
                    if (!classStudent.getStudentId().equals(student.getStudentId())) {
                        String classNotificationMessage = String.format(
                                "New assessment has been loaded for %s - %s. Subject: %s. Please check your portal.",
                                student.getStudentklass().getClassName(),
                                assessment.getTerm() != null ? assessment.getTerm().getTermName() : "Unknown",
                                assessment.getSubject() != null ? assessment.getSubject().getSubjectName() : "Unknown"
                        );
                        notificationService.createNotification(
                                classStudent,
                                "New Assessment Available",
                                classNotificationMessage,
                                "ASSESSMENT",
                                assessment.getId().toString(),
                                "Assessment"
                        );
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to create assessment notification: {}", e.getMessage(), e);
        }
    }

    public List<Assessment> FindAll() {
        return this.AR.findAll();
    }

    public Map<Term, List<Assessment>> getAssessmentBasedOnTerm(final Term term, Student student) {
        Map<Term, List<Assessment>> TempTermAssessment = new HashMap();
        List<Assessment> assess = this.AR.findByTermAndStudent(term.getId(), student.getStudentId());
        
        System.out.println("Fetching assesment from DB: "+student.getStudentId());
        TempTermAssessment.put(term, assess);
        return TempTermAssessment;


    }


    public Assessment getStudentClassForATermAssessment(Term term, Student student) {
 
Assessment newAss = new Assessment();

 Iterator var4 = this.FindAll().iterator();
    
        Logger.getLogger("Fetching student assessment phase 1").log(Level.FINE,"info");
        while(var4.hasNext()) {
            Assessment TempAsessment = (Assessment)var4.next();
            if (Objects.equals(TempAsessment.getTerm().getId(), term.getId()) && TempAsessment.getStudent().getStudentId().equals(student.getStudentId())) {
                newAss = TempAsessment;
            }
        }
    
        return newAss;
    }
  public Map<Term, List<Assessment>> getAssessmentBasedOnTermResponse(final Term term, Student student) {
        Map<Term, List<Assessment>> TempTermAssessment = new HashMap();
        List<Assessment> assess = this.AR.findByTermAndStudent(term.getId(), student.getStudentId());
          out.println("Done getting assessment for the term....");
        TempTermAssessment.put(term, assess);
        out.println("Done getting assessment for the term");
        return TempTermAssessment;


    }


//    public AssessmentResponse getStudentClassForATermAssessmentResponse(Term term, Student student) {
// 
//     AssessmentResponse newAss ; AR.findByTermAndStudent(term.getId(), 
//             student.getStudentId()).stream().map(Assessment::getAsessmentResponse).findFirst().get();
//    
//    
//      out.println("Fetching student assessment phase 1");
//    
//        return newAss;
//    }
     public List<Assessment> getStudentClassForATermAssessmentsResponse(Term term, Student student) {
       List<Assessment> tempAss =new ArrayList<>();
       int count=0;
       String time =LocalDateTime.now().toString();
        out.println("Start with operation : "+student.getStudentId()+"// Time: "+time);
   
        for (Assessment newAss:student.getAssessment()){
            
            if (newAss.getTerm().getId()==term.getId()){
                 out.println("Fetching student assessment phase : "+count+"->"+student.getStudentId());
                 
        tempAss.add(newAss);
            }
            count ++;
        }
  
      out.println("Done with operation : "+student.getStudentId()+"// Time: "+LocalDateTime.now().toString());
    
        return tempAss;
    }
    public Map<Student, List<Assessment>> GetClassGeneralAssessment(klass klass, Term term, List<Student> stud) {
        Map<Student, List<Assessment>> GA = new HashMap();

        try {
            stud.forEach((Newstud) -> {
                new ArrayList();
                List<Assessment> Assessment = ((Student)this.SS.FindstudentById(Newstud).get()).getAssessment();
                if (!((List)Assessment.stream().filter((c) -> {
                    return c.getTerm().getId() == term.getId();
                }).collect(Collectors.toList())).isEmpty()) {
                    GA.put(Newstud, (List)Assessment.stream().filter((c) -> {
                        return c.getTerm().getId() == term.getId();
                    }).collect(Collectors.toList()));
                }

            });
        } catch (Exception var6) {
        }

        return GA;
    }

    public List<AssessmentHelper> SortedMarksWithData(List<Assessment> assessment) {
        List<AssessmentHelper> AH = new ArrayList();
        assessment.stream().forEach((a) -> {
            AssessmentHelper TempAH = new AssessmentHelper();
            TempAH.setScore(a.getTotalScore());
            TempAH.setStud(a.getStudent());
            TempAH.setSubjectName(a.getSubject().getSubjectName());
            AH.add(TempAH);
        });
        return (List)AH.stream().sorted(Comparator.comparing(AssessmentHelper::getSubjectName)).collect(Collectors.toList());
    }

    public List<Assessment> GetKlassAssessment(klass klas, Term term) {
        return (List)this.kS.FindKlass(klas.getClassName()).getAssessment().stream().filter((c) -> {
            return c.getTerm().getId() == term.getId();
        }).collect(Collectors.toList());
    }

    public String AddRankEnding(int number) {
        String TempConvertNumber = number + "";
        int totalNumberOfElements = TempConvertNumber.length() - 1;
        if (TempConvertNumber.length() > 1 && TempConvertNumber.charAt(totalNumberOfElements - 1) == '1') {
            return TempConvertNumber + "th";
        } else if (TempConvertNumber.charAt(totalNumberOfElements) == '1') {
            return TempConvertNumber + "st";
        } else if (TempConvertNumber.charAt(0) == '1' && TempConvertNumber.charAt(totalNumberOfElements) == '1') {
            return TempConvertNumber + "th";
        } else if (TempConvertNumber.charAt(totalNumberOfElements) == '2') {
            return TempConvertNumber + "nd";
        } else {
            return TempConvertNumber.charAt(totalNumberOfElements) == '3' ? TempConvertNumber + "rd" : TempConvertNumber + "th";
        }
    }

    public String ApproveAssessment(List<Assessment> ass, users user) {
        try {
            ass.stream().forEach((a) -> {
                a.setHeadMasterApproval("TRUE");
                a.setHeadmasterInitial(user.getFirstname().toUpperCase().charAt(0) + user.getLastname().toUpperCase().charAt(0) + "");
                this.AR.save(a);
            });
            return "Approved Successfully";
        } catch (Exception var4) {
            Exception ex = var4;
            return "Problem Approving Assessment Contact Admin" + ex.getLocalizedMessage();
        }
    }

    public String checkHeadMasterInitials(Student stud, Term term) {
        String initials = "false";
        List<Assessment> ass = (List)((Term)this.TS.findTermById(term.getId()).get()).getAssessment().stream().filter((c) -> {
            return c.getStudent().getStudentId().equals(stud.getStudentId());
        }).collect(Collectors.toList());
        Iterator var5 = ass.iterator();
        if (var5.hasNext()) {
            Assessment as = (Assessment)var5.next();
            if (as.getHeadMasterApproval().equals("false")) {
                return initials;
            } else {
                initials = as.getInitials();
                return initials;
            }
        } else {
            return initials;
        }
    }

    public String getInitialSfromTeacherAndHead(Student stud, Term term) {
        String message = "";
        if (this.checkHeadMasterInitials(stud, term).equals("false")) {
            message = "provisional";
        } else {
            Teacher t = this.TKSs.getClassMaster(stud.getStudentklass().getClassName()).getTeacher();
            message = "HeadTeacher Signature:" + this.checkHeadMasterInitials(stud, term) + "Teachers" + t.getFirstName().toUpperCase().charAt(0) + t.getLastName().toUpperCase().charAt(0) + " Date: " + LocalDate.now();
        }

        return message;
    }

    public Optional<Assessment> findAssessmentByDeatials(Assessment assessment) {
       List<Assessment>Tempassessment=AR.findByTermAndStudentAndSubject(assessment.getTerm().getId()
                                                                      , assessment.getStudent().getStudentId()
                                                                      ,assessment.getSubject().getId());

        Optional<Assessment> ass = Tempassessment.stream().findFirst();
        return ass;
    }

    public Optional<Assessment> findAssessmentById(Long id) {
        return this.AR.findById(id);
    }

    public String removeAssessment(Assessment a) {
        try {
            this.AR.delete(a);
            return "Success";
        } catch (Exception var3) {
            Exception ex = var3;
            return ex.toString();
        }
    }

    public List<StudentAssessmentModel> studAssessmentTerms(Student stud) {
        List<StudentAssessmentModel> assessmentModel = new ArrayList();

        try {
            Map<Long, String> Allterms = new HashMap();
            stud.getAssessment().stream().forEach((c) -> {
                Allterms.put(c.getTerm().getId(), c.getStudentClass().getClassName());
            });
            if (!Allterms.isEmpty()) {
                Allterms.forEach((a, b) -> {
                    Term t = (Term)this.TS.findTermById(a).get();

                    assessmentModel.add(new StudentAssessmentModel(t.getTermName(), a, stud.getStudentId(), b, t.getAcademicyear().getAcademicName()));
                    System.out.println (t.getTermResponse().toString());
                });
            }

            return assessmentModel;
        } catch (Exception c) {
            c.printStackTrace();
            return new ArrayList();
        }
    }

    public StudentAnalyticsResponse getStudentAnalytics(Student stud) {
        try {
            List<Assessment> assessments = stud.getAssessment();
            if (assessments == null || assessments.isEmpty()) {
                return new StudentAnalyticsResponse(
                    stud.getFirstName() + " " + stud.getLastName(),
                    stud.getStudentklass().getClassName(),
                    new ArrayList<>(),
                    new ArrayList<>()
                );
            }

            // Group assessments by term
            Map<Long, List<Assessment>> assessmentsByTerm = assessments.stream()
                .collect(Collectors.groupingBy(a -> a.getTerm().getId()));

            // Sort terms by ID (assuming first term = 1, second = 2, third = 3)
            List<Long> sortedTermIds = new ArrayList<>(assessmentsByTerm.keySet());
            sortedTermIds.sort(Long::compareTo);

            List<StudentAnalyticsResponse.TermPerformance> termPerformanceList = new ArrayList<>();
            List<StudentAnalyticsResponse.SubjectSuggestion> suggestions = new ArrayList<>();

            // Track subject performance across terms for trend analysis
            Map<String, List<Double>> subjectScoresAcrossTerms = new HashMap<>();
            Map<String, String> subjectNames = new HashMap<>();

            for (Long termId : sortedTermIds) {
                Term term = (Term) this.TS.findTermById(termId).get();
                List<Assessment> termAssessments = assessmentsByTerm.get(termId);

                List<StudentAnalyticsResponse.SubjectScore> subjectScores = termAssessments.stream()
                    .map(a -> {
                        String subjectName = a.getSubject().getSubjectName();
                        subjectNames.put(subjectName, subjectName);
                        subjectScoresAcrossTerms
                            .computeIfAbsent(subjectName, k -> new ArrayList<>())
                            .add(a.getTotalScore());
                        return new StudentAnalyticsResponse.SubjectScore(
                            subjectName,
                            a.getTotalScore(),
                            a.getGrade()
                        );
                    })
                    .collect(Collectors.toList());

                termPerformanceList.add(new StudentAnalyticsResponse.TermPerformance(
                    term.getTermName(),
                    term.getAcademicyear().getAcademicName(),
                    subjectScores
                ));
            }

            // Generate suggestions based on performance trends
            for (Map.Entry<String, List<Double>> entry : subjectScoresAcrossTerms.entrySet()) {
                String subjectName = entry.getKey();
                List<Double> scores = entry.getValue();

                if (scores.size() >= 2) {
                    // Check for declining trend
                    boolean declining = true;
                    for (int i = 1; i < scores.size(); i++) {
                        if (scores.get(i) >= scores.get(i - 1)) {
                            declining = false;
                            break;
                        }
                    }

                    if (declining) {
                        suggestions.add(new StudentAnalyticsResponse.SubjectSuggestion(
                            subjectName,
                            "Your performance in " + subjectName + " is declining across terms. Consider seeking extra help from your teacher or forming a study group.",
                            "high"
                        ));
                    } else {
                        // Check for consistently low scores
                        double average = scores.stream().mapToDouble(Double::doubleValue).average().orElse(0);
                        if (average < 50) {
                            suggestions.add(new StudentAnalyticsResponse.SubjectSuggestion(
                                subjectName,
                                "Your average score in " + subjectName + " is " + String.format("%.1f", average) + "%. You need to focus more on this subject to improve your grades.",
                                "high"
                            ));
                        } else if (average < 60) {
                            suggestions.add(new StudentAnalyticsResponse.SubjectSuggestion(
                                subjectName,
                                "Your average score in " + subjectName + " is " + String.format("%.1f", average) + "%. There is room for improvement. Consider reviewing past assessments and practicing more.",
                                "medium"
                            ));
                        }
                    }
                } else if (scores.size() == 1) {
                    double score = scores.get(0);
                    if (score < 50) {
                        suggestions.add(new StudentAnalyticsResponse.SubjectSuggestion(
                            subjectName,
                            "Your score in " + subjectName + " is " + String.format("%.1f", score) + "%. You need to focus more on this subject.",
                            "high"
                        ));
                    } else if (score < 60) {
                        suggestions.add(new StudentAnalyticsResponse.SubjectSuggestion(
                            subjectName,
                            "Your score in " + subjectName + " is " + String.format("%.1f", score) + "%. Consider putting in more effort to improve.",
                            "medium"
                        ));
                    }
                }
            }

            return new StudentAnalyticsResponse(
                stud.getFirstName() + " " + stud.getLastName(),
                stud.getStudentklass().getClassName(),
                termPerformanceList,
                suggestions
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new StudentAnalyticsResponse(
                stud.getFirstName() + " " + stud.getLastName(),
                stud.getStudentklass().getClassName(),
                new ArrayList<>(),
                new ArrayList<>()
            );
        }
    }

    public StudentAnalyticsResponse getStudentAnalyticsByTerm(Student stud, Long termId) {
        try {
            List<Assessment> assessments = stud.getAssessment();
            if (assessments == null || assessments.isEmpty()) {
                return new StudentAnalyticsResponse(
                    stud.getFirstName() + " " + stud.getLastName(),
                    stud.getStudentklass().getClassName(),
                    new ArrayList<>(),
                    new ArrayList<>()
                );
            }

            // Filter assessments for the specific term
            List<Assessment> termAssessments = assessments.stream()
                .filter(a -> a.getTerm().getId().equals(termId))
                .collect(Collectors.toList());

            if (termAssessments.isEmpty()) {
                return new StudentAnalyticsResponse(
                    stud.getFirstName() + " " + stud.getLastName(),
                    stud.getStudentklass().getClassName(),
                    new ArrayList<>(),
                    new ArrayList<>()
                );
            }

            Term term = (Term) this.TS.findTermById(termId).get();

            List<StudentAnalyticsResponse.SubjectScore> subjectScores = termAssessments.stream()
                .map(a -> new StudentAnalyticsResponse.SubjectScore(
                    a.getSubject().getSubjectName(),
                    a.getTotalScore(),
                    a.getGrade()
                ))
                .collect(Collectors.toList());

            List<StudentAnalyticsResponse.TermPerformance> termPerformanceList = new ArrayList<>();
            termPerformanceList.add(new StudentAnalyticsResponse.TermPerformance(
                term.getTermName(),
                term.getAcademicyear().getAcademicName(),
                subjectScores
            ));

            // Generate suggestions for this term only
            List<StudentAnalyticsResponse.SubjectSuggestion> suggestions = new ArrayList<>();
            for (StudentAnalyticsResponse.SubjectScore score : subjectScores) {
                if (score.getTotalScore() < 50) {
                    suggestions.add(new StudentAnalyticsResponse.SubjectSuggestion(
                        score.getSubjectName(),
                        "Your score in " + score.getSubjectName() + " is " + String.format("%.1f", score.getTotalScore()) + "%. You need to focus more on this subject to improve your grades.",
                        "high"
                    ));
                } else if (score.getTotalScore() < 60) {
                    suggestions.add(new StudentAnalyticsResponse.SubjectSuggestion(
                        score.getSubjectName(),
                        "Your score in " + score.getSubjectName() + " is " + String.format("%.1f", score.getTotalScore()) + "%. There is room for improvement. Consider reviewing past assessments and practicing more.",
                        "medium"
                    ));
                }
            }

            return new StudentAnalyticsResponse(
                stud.getFirstName() + " " + stud.getLastName(),
                stud.getStudentklass().getClassName(),
                termPerformanceList,
                suggestions
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new StudentAnalyticsResponse(
                stud.getFirstName() + " " + stud.getLastName(),
                stud.getStudentklass().getClassName(),
                new ArrayList<>(),
                new ArrayList<>()
            );
        }
    }
}
