package com.UBSDB.UnerBasicSchoolFinancialSystem.Atenary;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArtenaryResponse {
    private String  weekName;
    private String  startDate;
    private String endDate;
    private String Id;
    private String activity;
    private boolean isCurrentWeek;
    private String weekId;
}
