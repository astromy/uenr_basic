/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Enock
 */
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class TermWeek {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID termWeekId;
   private String weekName;
   private String startDate;
   private String endDate;
   private String numberOfDays;
   @ManyToOne
   private Term term;

    @Override
    public String toString() {
        return "TermWeek{" +
                "termWeekId=" + termWeekId +
                ", weekName='" + weekName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", numberOfDays='" + numberOfDays + '\'' +
                '}';
    }
}
