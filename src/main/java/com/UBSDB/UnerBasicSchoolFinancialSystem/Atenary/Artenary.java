package com.UBSDB.UnerBasicSchoolFinancialSystem.Atenary;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek.TermWeek;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Artenary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID artenaryId;
    @ManyToOne
    @JoinColumn(name = "week_term_week_id")
    private TermWeek week;
    private String activity;

   public ArtenaryResponse getResponse(){
        return ArtenaryResponse.
                builder()
                .weekName(week.getWeekName())
                .weekId(week.getTermWeekId().toString())
                .activity(activity)
                .endDate(week.getEndDate())
                .startDate(week.getStartDate())
                .Id(artenaryId.toString())
                .build();

   }
}
