/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Expenses;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Academic.Academic;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author enock
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Expenses {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long Id;
    

    private String expensesType;
    

    private String category;
    

    private String description;

    private double Amount;

    private LocalDate expenseDate;
    

    private String paymentMethod;
    

    private String referenceNumber;
    

    private String status = "PENDING";
    
    @ManyToOne
    private Term term;
    
    @ManyToOne
    private Academic academicYear;
    
    @ManyToOne
    private users User;

   public  ExpenseResponse getResponse(){
       return ExpenseResponse.from(this);
   }
}
