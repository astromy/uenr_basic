package com.UBSDB.UnerBasicSchoolFinancialSystem.Term;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Academic.Academic;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.Assessment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.Payment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek.TermWeek;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.Mock;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.MockDetails.mockDetails;
import com.UBSDB.UnerBasicSchoolFinancialSystem.stock.stock;
import com.UBSDB.UnerBasicSchoolFinancialSystem.video.Video;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;


@Entity
public class Term {
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO
    )
    private Long id;
    private String TermName;
    private String StartDate;
    private String EndDate;
    @OneToMany(
        mappedBy = "term"
    )
    private List<Video> videos;
    @ManyToOne
    private Academic academicyear;
    @OneToMany(
        mappedBy = "term"
    )
    private List<Mock> mocks;
    @OneToMany(
        mappedBy = "term"
    )
    private List<mockDetails> mockDetails;
    @OneToMany(
        mappedBy = "term"
    )
    private List<Assessment> assessment;
    @OneToMany(
        mappedBy = "term"
    )
    private List<stock> stocks;
    @OneToMany(
        mappedBy = "term"
    )
    private List<Payment> payments;
    @OneToMany(
        mappedBy = "term"
         )
    List<SchoolFees> schoolFees;
    @OneToMany(
        mappedBy = "term"
         )
    private List<TermWeek> weeks;
    private String termAcademicYear;

    public List<Video> getVideos() {
        return this.videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public Term() {
    }

    public TermResponse getTermResponse() {
      return TermResponse.builder()
              .termDescription(TermName+" "+" "+ academicyear.getAcademicName())
              .TermName(TermName)
              .academicYear(academicyear.getAcademicName())
              .EndDate(EndDate)
              .StartDate(StartDate)
              .build();

          }

    public List<mockDetails> getMockDetails() {
        return this.mockDetails;
    }

    public void setMockDetails(List<mockDetails> mockDetails) {
        this.mockDetails = mockDetails;
    }

    public List<Mock> getMocks() {
        return this.mocks;
    }

    public void setMocks(List<Mock> mocks) {
        this.mocks = mocks;
    }

    public List<stock> getStocks() {
        return this.stocks;
    }

    public void setStocks(List<stock> stocks) {
        this.stocks = stocks;
    }

    public List<SchoolFees> getSchoolFees() {
        return this.schoolFees;
    }

    public void setSchoolFees(List<SchoolFees> schoolFees) {
        this.schoolFees = schoolFees;
    }

    public List<TermWeek> getWeeks() {
        return this.weeks;
    }

    public void setWeeks(List<TermWeek> weeks) {
        this.weeks = weeks;
    }

    public List<Payment> getPayments() {
        return this.payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Assessment> getAssessment() {
        return this.assessment;
    }

    public void setAssessment(List<Assessment> assessment) {
        this.assessment = assessment;
    }

    public Term(String TermName, String StartDate, String EndDate, Academic academicyear) {
        this.TermName = TermName;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.academicyear = academicyear;
    }

    public Academic getAcademicyear() {
        return this.academicyear;
    }

    public void setAcademicyear(Academic academicyear) {
        this.academicyear = academicyear;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTermName() {
        return this.TermName;
    }

    public void setTermName(String TermName) {
        this.TermName = TermName;
    }

    public String getStartDate() {
        return this.StartDate;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    public String getEndDate() {
        return this.EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public String getTermAcademicYear() {
        this.termAcademicYear = this.getTermName() + " - " + this.getAcademicyear().getAcademicName();
        return this.termAcademicYear;
    }

    public void setTermAcademicYear(String termAcademicYear) {
        this.termAcademicYear = termAcademicYear;
    }
}
