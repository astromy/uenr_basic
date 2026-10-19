/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Expenses;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Academic.Academic;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author enock
 */
@Service
public class ExpensesService {
    @Autowired private ExpenseRepository ER;
    public String saveExpense(Expenses expense){
    try{
        ER.save(expense);
    return "Saved Successfully";
    }catch(Exception ex){
    return "Erro please contact admin "+ex.toString();
    }
        //return "";
    }
    
    public List <Expenses> ListAllExpenses(Academic aca){
        List <Expenses>AllEx=new ArrayList<>();
        for(Expenses ex:ER.findAll()){
        if(ex.getExpenseDate().getYear()==aca.getAcademicYear().getYear()){
        AllEx.add(ex);
        }
        
        }return AllEx;
    }
    public List<Expenses>ListAllExpensesTermly(Term term){
    List <Expenses>AllEx=new ArrayList<>();
        for(Expenses ex:ER.findAll()){
        if(ex.getExpenseDate().isBefore(LocalDate.parse(term.getStartDate()))||ex.getExpenseDate().
               isAfter(LocalDate.parse(term.getEndDate()))==true){
     AllEx.add(ex);
        }
        
        }return AllEx;
    
    }
    public List<Expenses> MonthlyEspenses(LocalDate date){
    List <Expenses>AllEx=new ArrayList<>();
     for(Expenses ex:ER.findAll()){
       if(ex.getExpenseDate().getMonth().equals(date.getMonth())){
       AllEx.add(ex);
       }
     }
    return AllEx;
    }
}
