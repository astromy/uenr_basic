package com.UBSDB.UnerBasicSchoolFinancialSystem.Performance;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.Assessment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Course.Course;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Course.CourseService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.SubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PerformanceService {

    @Autowired
    private TermService termService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private klassService klassService;

    /**
     * Get performance data for all subjects in a given term and class
     * Returns average test scores, exam scores, and total scores per subject
     */
    public List<SubjectPerformanceData> getSubjectPerformanceByClassAndTerm(String className, Long termId) {
        klass k = klassService.FindKlass(className);
        Term term = termService.findTermById(termId).orElse(null);
        if (term == null || k == null) {
            return new ArrayList<>();
        }

        List<Assessment> assessments = k.getAssessment().stream()
                .filter(a -> a.getTerm().getId() == termId)
                .collect(Collectors.toList());

        // Group by subject
        Map<Subject, List<Assessment>> assessmentsBySubject = assessments.stream()
                .collect(Collectors.groupingBy(Assessment::getSubject));

        List<SubjectPerformanceData> result = new ArrayList<>();
        for (Map.Entry<Subject, List<Assessment>> entry : assessmentsBySubject.entrySet()) {
            Subject subject = entry.getKey();
            List<Assessment> subjectAssessments = entry.getValue();

            double avgTestScore = subjectAssessments.stream()
                    .mapToDouble(Assessment::getTotalTestScoreAverage)
                    .average()
                    .orElse(0.0);

            double avgExamScore = subjectAssessments.stream()
                    .mapToDouble(Assessment::getExamsAverage)
                    .average()
                    .orElse(0.0);

            double avgTotalScore = subjectAssessments.stream()
                    .mapToDouble(Assessment::getTotalScore)
                    .average()
                    .orElse(0.0);

            double highestScore = subjectAssessments.stream()
                    .mapToDouble(Assessment::getHighestScore)
                    .max()
                    .orElse(0.0);

            result.add(new SubjectPerformanceData(
                    subject.getSubjectName(),
                    subject.getId(),
                    avgTestScore,
                    avgExamScore,
                    avgTotalScore,
                    highestScore,
                    subjectAssessments.size()
            ));
        }

        return result.stream()
                .sorted(Comparator.comparing(SubjectPerformanceData::getSubjectName))
                .collect(Collectors.toList());
    }

    /**
     * Get performance data for all courses in a given term and class
     * Links courses to subjects and shows performance metrics
     */
    public List<CoursePerformanceData> getCoursePerformanceByClassAndTerm(String className, Long termId) {
        List<SubjectPerformanceData> subjectPerformance = getSubjectPerformanceByClassAndTerm(className, termId);
        List<Course> courses = courseService.getAllCourses();

        List<CoursePerformanceData> result = new ArrayList<>();
        for (Course course : courses) {
            Subject subject = course.getSubject();
            Optional<SubjectPerformanceData> perfData = subjectPerformance.stream()
                    .filter(s -> s.getSubjectId() == subject.getId())
                    .findFirst();

            SubjectPerformanceData spd = perfData.orElse(new SubjectPerformanceData(
                    subject.getSubjectName(),
                    subject.getId(),
                    0.0, 0.0, 0.0, 0.0, 0
            ));

            result.add(new CoursePerformanceData(
                    course.getCourseId().toString(),
                    course.getCourseName(),
                    subject.getSubjectName(),
                    spd.getAvgTestScore(),
                    spd.getAvgExamScore(),
                    spd.getAvgTotalScore(),
                    spd.getHighestScore(),
                    spd.getStudentCount()
            ));
        }

        return result.stream()
                .sorted(Comparator.comparing(CoursePerformanceData::getCourseName))
                .collect(Collectors.toList());
    }

    /**
     * Get performance comparison data for charting
     * Returns labels and datasets for Chart.js
     */
    public PerformanceChartData getPerformanceChartData(String className, Long termId) {
        List<SubjectPerformanceData> subjectPerformance = getSubjectPerformanceByClassAndTerm(className, termId);

        List<String> labels = subjectPerformance.stream()
                .map(SubjectPerformanceData::getSubjectName)
                .collect(Collectors.toList());

        List<Double> testScores = subjectPerformance.stream()
                .map(SubjectPerformanceData::getAvgTestScore)
                .collect(Collectors.toList());

        List<Double> examScores = subjectPerformance.stream()
                .map(SubjectPerformanceData::getAvgExamScore)
                .collect(Collectors.toList());

        List<Double> totalScores = subjectPerformance.stream()
                .map(SubjectPerformanceData::getAvgTotalScore)
                .collect(Collectors.toList());

        return new PerformanceChartData(labels, testScores, examScores, totalScores);
    }

    /**
     * Get all available terms for filtering
     */
    public List<Term> getAllTerms() {
        return termService.terms();
    }

    /**
     * Get all available classes
     */
    public List<klass> getAllClasses() {
        return klassService.AllKlass();
    }

    /**
     * Get all subjects
     */
    public List<Subject> getAllSubjects() {
        return subjectService.Allsubjects();
    }
}
