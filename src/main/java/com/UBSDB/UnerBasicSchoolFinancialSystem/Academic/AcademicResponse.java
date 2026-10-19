package com.UBSDB.UnerBasicSchoolFinancialSystem.Academic;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class AcademicResponse {
    private Long Id;
    private LocalDate AcademicYear;
    private LocalDate startDate;
    private LocalDate endDate;
    private String SuccessorAcademicYear;
    private String AcademicName;
    private List<TermResponse> terms;

}  
