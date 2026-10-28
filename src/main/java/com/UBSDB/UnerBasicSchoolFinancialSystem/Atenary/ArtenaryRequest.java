package com.UBSDB.UnerBasicSchoolFinancialSystem.Atenary;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek.TermWeek;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ArtenaryRequest {
    private String weekId;
    private String activity;
    private String Id;


}
