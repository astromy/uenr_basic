/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.PTA;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFeesService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author enock
 */
@Service
public class PTAService {
    @Autowired
    private PTARepository ptaR;
   
    @Autowired
    private BillService  BS;
    @Autowired
    private TermService TS;
     @Autowired
     private SchoolFeesService SFS;
    
     public String SavePTA(PTA pta){
        List<Bill>bills=new ArrayList<>();
 
    try{
        for(Bill bill:BS.bills()){
    if((bill.getTerm().getAcademicyear().getAcademicYear().getYear()==pta.getTerm().
            getAcademicyear().getAcademicYear().getYear())
            &&(bill.getTerm().getTermName().equals(pta.getTerm().getTermName()))){
       ptaR.save(pta);
        
     
        
       BS.saveBill(bill);
    } //return "saved succesfully";
     } return "savedSuccessfully"+pta.getTerm().getTermName();
      
    }catch(Exception ex){   return "error contact admin"+ex.toString();
    } //return "please add Fess for this term "+pta.getTerm().getTermName();
   
}
   public boolean checkIf_PTA_Is_Entered(Term term){
  for(PTA Pta:ptaR.findAll()){
  if(Pta.getTerm().equals(term)){
  
  return true;
  
  }
  
  }
   
   return false;
   
   }
   
    public Set<Term> PTATerm(){
        
   return SFS.Feeterms();
   
   }
    public PTA getPTAForTerm(Term term){
    for(PTA p:ptaR.findAll()){
    if(p.getTerm().getId()==term.getId()){
    return p;
    }
    
    }
    return null;
    }
}
