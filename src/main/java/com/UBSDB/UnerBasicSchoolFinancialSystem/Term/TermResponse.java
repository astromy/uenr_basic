//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.Term;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TermResponse {
    private Long id;
    private String academicYear;
    private String TermName;
    private String StartDate;
    private String EndDate;
    private String termDescription;

    @Override
    public String toString() {
        return "TermResponse{" +
                "id=" + id +
                ", academicYear='" + academicYear + '\'' +
                ", TermName='" + TermName + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", termDescription='" + termDescription + '\'' +
                '}';
    }
//    public TermResponse(Long id, String TermName, String StartDate, String EndDate, String academicYear) {
//        this.id = id;
//        this.TermName = TermName;
//        this.StartDate = StartDate;
//        this.EndDate = EndDate;
//        this.academicYear = academicYear;
//    }
//
//    public TermResponse() {
//    }
//
//    public String getAcademicYear() {
//        return this.academicYear;
//    }
//
//    public void setAcademicYear(String academicYear) {
//        this.academicYear = academicYear;
//    }
//
//    public Long getId() {
//        return this.id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getTermName() {
//        return this.TermName;
//    }
//
//    public void setTermName(String TermName) {
//        this.TermName = TermName;
//    }
//
//    public String getStartDate() {
//        return this.StartDate;
//    }
//
//    public void setStartDate(String StartDate) {
//        this.StartDate = StartDate;
//    }
//
//    public String getEndDate() {
//        return this.EndDate;
//    }
//
//    public void setEndDate(String EndDate) {
//        this.EndDate = EndDate;
//    }
//
//    public String getTermDescription() {
//        this.termDescription = this.TermName + this.academicYear;
//        return this.termDescription;
//    }
//
//    public void setTermDescription(String termDescription) {
//        this.termDescription = termDescription;
//    }


}
