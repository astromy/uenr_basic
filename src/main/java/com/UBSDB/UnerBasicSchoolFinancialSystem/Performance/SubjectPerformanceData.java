package com.UBSDB.UnerBasicSchoolFinancialSystem.Performance;

public class SubjectPerformanceData {
    private String subjectName;
    private long subjectId;
    private double avgTestScore;
    private double avgExamScore;
    private double avgTotalScore;
    private double highestScore;
    private int studentCount;

    public SubjectPerformanceData(String subjectName, long subjectId, double avgTestScore,
                                  double avgExamScore, double avgTotalScore, double highestScore, int studentCount) {
        this.subjectName = subjectName;
        this.subjectId = subjectId;
        this.avgTestScore = avgTestScore;
        this.avgExamScore = avgExamScore;
        this.avgTotalScore = avgTotalScore;
        this.highestScore = highestScore;
        this.studentCount = studentCount;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
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
