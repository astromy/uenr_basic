/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Finance;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Expenses.ExpensesService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.PaymentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.Payment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.PTA.PTA;
import com.UBSDB.UnerBasicSchoolFinancialSystem.PTA.PTAService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFeesService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author enock
 */
@Slf4j
@Service
public class FinanceService {
   @Autowired private StudentService SS;
   @Autowired private PaymentService PS;@Autowired private klassService KS;
   @Autowired private SchoolFeesService SFS;@Autowired private PTAService PTAS;
   @Autowired private TermService Ts;
   @Autowired private ExpensesService ES;
   public List<klass>GetStudentClasses(){
   return  KS.AllKlass();
   }
   
   public List<Student>GetStudentBasedOnKlass(String klas){
   return SS.GetStudents_In_A_Particular_Class(KS.FindKlass(klas));
   }
   
   public Payment saveFees(Payment payment,Principal p){
   return PS.SaveSchoolFeesPayment(payment,p);
   }
   
   
   public List<Payment>GetStudentPayment(String student){
   
   return PS.payments(student);
   }
   public double GetStudentOutStandingFees(String studentId){
   double amount=0;
   for(Payment p:GetStudentPayment(studentId)){
   
   amount=p.getRemaining();
   }
   return amount;
   }
   
   public String setSchoolFess(SchoolFees SF,List <String >ClassLevel){
   return SFS.SaveSchoolFees(SF,ClassLevel);
   }
    public Map<Set<String>, SchoolFees> getAllSchoolFees() {

        Map<Set<String>, SchoolFees> Tfees = new LinkedHashMap<>();

        List<SchoolFees> allFees = SFS.getAllSchoolFees();

        if (allFees == null || allFees.isEmpty()) {
            return Tfees;
        }

        Set<Integer> getHashCodes = allFees.stream()
                .map(SchoolFees::getHashCode)
                .filter(c -> c != null && c > 0)
                .collect(Collectors.toSet());

        getHashCodes.forEach(f -> {

            Set<String> keyValues = new LinkedHashSet<>();

            List<SchoolFees> fees = SFS.findSchoolFees_hashCode(f);

            if (fees == null || fees.isEmpty()) {
                return;
            }

            for (SchoolFees fee : fees) {

                if (fee.getKlass() != null
                        && fee.getKlass().getClassLevel() != null) {

                    keyValues.add(
                            fee.getKlass().getClassLevel()
                                    + fee.getHashCode()
                                    + ")"
                    );
                }
            }

            Tfees.put(keyValues, fees.get(0));
        });

        return Tfees;
    }
   public String setPTA(PTA pta){
   return PTAS.SavePTA(pta);
   }
   public List<klass>AllKlasses(){
   return KS.AllKlass();
   }
     public SchoolStatisticsModel getSchoolStaticsModel(){
        try{
         int totalStudents = Math.toIntExact(SS.countAllStudents());
         int juniorStaff = (int) SS.countJuniorStaffStudents();
         int nonStaff = (int) SS.countNonStaffStudents();
         int seniorMember = (int) SS.countSeniorMemberStudents();

         int upperPrimary = (int) SS.countStudentsByClassLevel("UpperPrimary");
         int lowerPrimary = (int) SS.countStudentsByClassLevel("LowerPrimary");
         int juniorHigh = (int) SS.countStudentsByClassLevel("JuniorHigh");
         int creche = (int) SS.countStudentsByClassLevel("Creche");
         int nursery = (int) SS.countStudentsByClassLevel("Nursery");
         int kindergarten = (int) SS.countStudentsByClassLevel("Kindergaten");

         double amountPaid = PS.sumPaymentsByDateAndTerm(LocalDate.now(), Ts.getCurrentTerm().getId());

         // Get total expenses for current term
         double totalExpenses = 0.0;
         try {
             Term currentTerm = Ts.getCurrentTerm();
             if (currentTerm != null) {
                 totalExpenses = ES.getTotalExpensesByTerm(currentTerm);
             }
         } catch (Exception e) {
             System.out.println("Error fetching expenses: " + e.getMessage());
         }

         double netProfit = amountPaid - totalExpenses;

         int totalByLevel = upperPrimary + lowerPrimary + juniorHigh + creche + kindergarten + nursery;

         SchoolStatisticsModel SSM = new SchoolStatisticsModel(totalByLevel, nonStaff, juniorStaff,
                 seniorMember, kindergarten, juniorHigh, lowerPrimary, upperPrimary,
                 creche,
                 nursery, (float) amountPaid, totalExpenses, netProfit);
          System.out.println("All students"+ SSM.toString());

         return SSM;

        }catch (Exception ex){
       log.error("{} ",ex.toString());
        return null;

        }
    }
   
}
