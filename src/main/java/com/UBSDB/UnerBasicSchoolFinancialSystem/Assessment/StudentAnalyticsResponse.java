package com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment;

import java.util.List;
import java.util.Map;

public class StudentAnalyticsResponse {
    private String studentName;
    private String studentClass;
    private List<TermPerformance> termPerformance;
    private List<SubjectSuggestion> suggestions;

    public StudentAnalyticsResponse() {
    }

    public StudentAnalyticsResponse(String studentName, String studentClass, List<TermPerformance> termPerformance, List<SubjectSuggestion> suggestions) {
        this.studentName = studentName;
        this.studentClass = studentClass;
        this.termPerformance = termPerformance;
        this.suggestions = suggestions;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public List<TermPerformance> getTermPerformance() {
        return termPerformance;
    }

    public void setTermPerformance(List<TermPerformance> termPerformance) {
        this.termPerformance = termPerformance;
    }

    public List<SubjectSuggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<SubjectSuggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public static class TermPerformance {
        private String termName;
        private String academicYear;
        private List<SubjectScore> subjects;

        public TermPerformance() {
        }

        public TermPerformance(String termName, String academicYear, List<SubjectScore> subjects) {
            this.termName = termName;
            this.academicYear = academicYear;
            this.subjects = subjects;
        }

        public String getTermName() {
            return termName;
        }

        public void setTermName(String termName) {
            this.termName = termName;
        }

        public String getAcademicYear() {
            return academicYear;
        }

        public void setAcademicYear(String academicYear) {
            this.academicYear = academicYear;
        }

        public List<SubjectScore> getSubjects() {
            return subjects;
        }

        public void setSubjects(List<SubjectScore> subjects) {
            this.subjects = subjects;
        }
    }

    public static class SubjectScore {
        private String subjectName;
        private double totalScore;
        private String grade;

        public SubjectScore() {
        }

        public SubjectScore(String subjectName, double totalScore, String grade) {
            this.subjectName = subjectName;
            this.totalScore = totalScore;
            this.grade = grade;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public double getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(double totalScore) {
            this.totalScore = totalScore;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }
    }

    public static class SubjectSuggestion {
        private String subjectName;
        private String suggestion;
        private String severity; // "high", "medium", "low"

        public SubjectSuggestion() {
        }

        public SubjectSuggestion(String subjectName, String suggestion, String severity) {
            this.subjectName = subjectName;
            this.suggestion = suggestion;
            this.severity = severity;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public String getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(String suggestion) {
            this.suggestion = suggestion;
        }

        public String getSeverity() {
            return severity;
        }

        public void setSeverity(String severity) {
            this.severity = severity;
        }
    }
}
