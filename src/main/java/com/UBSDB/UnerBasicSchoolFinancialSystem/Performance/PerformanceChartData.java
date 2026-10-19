package com.UBSDB.UnerBasicSchoolFinancialSystem.Performance;

import java.util.List;

public class PerformanceChartData {
    private List<String> labels;
    private List<Double> testScores;
    private List<Double> examScores;
    private List<Double> totalScores;

    public PerformanceChartData(List<String> labels, List<Double> testScores,
                                List<Double> examScores, List<Double> totalScores) {
        this.labels = labels;
        this.testScores = testScores;
        this.examScores = examScores;
        this.totalScores = totalScores;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<Double> getTestScores() {
        return testScores;
    }

    public void setTestScores(List<Double> testScores) {
        this.testScores = testScores;
    }

    public List<Double> getExamScores() {
        return examScores;
    }

    public void setExamScores(List<Double> examScores) {
        this.examScores = examScores;
    }

    public List<Double> getTotalScores() {
        return totalScores;
    }

    public void setTotalScores(List<Double> totalScores) {
        this.totalScores = totalScores;
    }
}
