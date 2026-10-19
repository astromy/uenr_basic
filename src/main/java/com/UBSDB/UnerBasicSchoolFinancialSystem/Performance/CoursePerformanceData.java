package com.UBSDB.UnerBasicSchoolFinancialSystem.Performance;

public class CoursePerformanceData {
    private String courseId;
    private String courseName;
    private String subjectName;
    private double avgTestScore;
    private double avgExamScore;
    private double avgTotalScore;
    private double highestScore;
    private int studentCount;

    public CoursePerformanceData(String courseId, String courseName, String subjectName,
                                 double avgTestScore, double avgExamScore, double avgTotalScore,
                                 double highestScore, int studentCount) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.subjectName = subjectName;
        this.avgTestScore = avgTestScore;
        this.avgExamScore = avgExamScore;
        this.avgTotalScore = avgTotalScore;
        this.highestScore = highestScore;
        this.studentCount = studentCount;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public double getAvgTestScore() {
        return avgTestScore;
    }

    public void setAvgTestScore(double avgTestScore) {
        this.avgTestScore = avgTestScore;
    }

    public double getAvgExamScore() {
        return avgExamScore;
    }

    public void setAvgExamScore(double avgExamScore) {
        this.avgExamScore = avgExamScore;
    }

    public double getAvgTotalScore() {
        return avgTotalScore;
    }

    public void setAvgTotalScore(double avgTotalScore) {
        this.avgTotalScore = avgTotalScore;
    }

    public double getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(double highestScore) {
        this.highestScore = highestScore;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }
}
