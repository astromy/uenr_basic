/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Academic;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentID_Checker.IDChecker;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;



/**
 *
 * @author enock
 */

@Entity
public class Academic implements Serializable {
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO
    )
    private Long Id;
    private LocalDate AcademicYear;
    @OneToMany(
        mappedBy = "academicYear"
    )
    private List<IDChecker> IDCheckers;
    @OneToMany(
        mappedBy = "academicyear"
    )
    private Set<Term> term;
    private LocalDate startDate;
    private LocalDate endDate;
    private String SuccessorAcademicYear;
    private String AcademicName;

    public Academic(Long Id) {
        this.Id = Id;
    }

    public List<IDChecker> getIDCheckers() {
        return this.IDCheckers;
    }

    public void setIDCheckers(List<IDChecker> IDCheckers) {
        this.IDCheckers = IDCheckers;
    }

    public Academic() {
    }

    public String getAcademicName() {
        return this.AcademicName;
    }

    public void setAcademicName(String AcademicName) {
        this.AcademicName = AcademicName;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getSuccessorAcademicYear() {
        return this.SuccessorAcademicYear;
    }

    public void setSuccessorAcademicYear(String SuccessorAcademicYear) {
        this.SuccessorAcademicYear = SuccessorAcademicYear;
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public LocalDate getAcademicYear() {
        return this.AcademicYear;
    }

    public void setAcademicYear(LocalDate AcademicYear) {
        this.AcademicYear = AcademicYear;
    }

    public Set<Term> getTerm() {
        return this.term;
    }

    public void setTerm(Set<Term> term) {
        this.term = term;
    }

    public AcademicResponse getAcademicResponse(){
        return AcademicResponse
                .builder()
                .AcademicName(AcademicName)
                .AcademicYear(AcademicYear)
                .startDate(startDate)
                .endDate(endDate)
                .Id(Id)
                .terms(term.stream().map(Term::getTermResponse).collect(Collectors.toList()))
                .build();


    }
}
