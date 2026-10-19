/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees;

import com.UBSDB.UnerBasicSchoolFinancialSystem.PTA.PTAService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author enock
 */
@Controller
@   RequestMapping("/FinancePortal/")
public class SchoolFeesController {
    @Autowired
    private SchoolFeesService SFS;
    @Autowired
    private StudentService SS;
   @Autowired
   private PTAService ptas;
  private  @Autowired klassService ks;
  
    @GetMapping("/ShowSchoolFeesForm")
   public String ShowSchoolFeesForm(Model model)
   {
       if(SFS.Feeterms().isEmpty()){
        model.addAttribute("msg","Please contact admin to set Term");
       
       }else{
         //  List<String >   datas=new ArrayList<>();
    // String [] alldata = { "JANUARY","FEBUARY","MARCH","APRIL","MAY","JUNE","JULY"
       //      ,"AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
   
   //  model.addAttribute("dates",alldata );
   
     model.addAttribute("Fees",new SchoolFees(LocalDate.now().toString()));
     model.addAttribute("Classlevel",ks.GetAllSpecifications());
     model.addAttribute("terms",SFS.Feeterms());
   
      } return "FinancePortal";
   }
   @GetMapping("/ShowSchoolOtherFeesForm")
   public String ShowSchoolOtherFeesForm(Model model)
   {
       if(SFS.Feeterms().isEmpty()){
        model.addAttribute("msg","Please contact admin to set Term");
       
       }else{
     //   System.out.println(dates.values());
     model.addAttribute("Fees",new SchoolFees());
     model.addAttribute("Classlevel",ks.GetAllSpecifications());
     model.addAttribute("terms",SFS.Feeterms());
   
      } return "FinancePortal";
   }
     
   @PostMapping("/savefees")
      public String ViewFees(Model model,SchoolFees fess,
              
                @RequestParam("levels")List <String>levels){
     
       if (levels.isEmpty()){
         model.addAttribute("msg","Please try and fill");
            return "FinancePortal";
       }
       try{
           System.out.println("......."+fess.getFeeSpecification()+"........"+fess.getTerm().getTermName());
      // fess.setMoth(mth);
      //  model.addAttribute
       model.addAttribute("ApproveFees",fess);
     model.addAttribute("Classlevel",levels);
      model.addAttribute("terms",SFS.Feeterms());
       }catch(Exception ex){
           
       model.addAttribute("msg","Sorry Contact Admin");
     
       }
      return "FinancePortal";
      }
      
   @PostMapping("/savefeesFinal")
      public String SaveFess(Model model,SchoolFees fess,
              
                @RequestParam("levels")List <String>levels){
     
       if (levels.isEmpty()){
         model.addAttribute("msg","Please try and fill");
            return "FinancePortal";
       }
       try{
           System.out.println("......."+fess.getFeeSpecification()+"........"+fess.getTerm().getTermName());
      // fess.setMoth(mth);
        model.addAttribute
        ("msg",
                SFS.SaveSchoolFees(fess,levels));
      
       }catch(Exception ex){
           
       model.addAttribute("msg","Sorry Contact Admin");
     
       }
      return "FinancePortal";
      }
       @PostMapping("/savefeesOutstandingFee/{StudentId}")
      public String SaveFess(Model model,SchoolFees fess,@PathVariable("StudentId")String StudentId){
     
      
       try{
           System.out.println("......."+fess.getFeeSpecification()+"........"+fess.getTerm().getTermName());
      // fess.setMoth(mth);
        model.addAttribute
        ("msg",
                SFS.SaveSchoolFees(fess,SS.findStudent(StudentId)));
      
       }catch(Exception ex){
           
       model.addAttribute("msg","Sorry Contact Admin");
     
       }
      return "FinancePortal";
      }
       @GetMapping("/Editfees/{id}")
      public String EditFess(Model model,@PathVariable("id")Long Id){
       //  
         //  System.out.println("......."+fess.getFeeSpecification()+"........"+fess.getTerm().getTermName());
      // fess.setMoth(mth);
      SchoolFees sf = new SchoolFees(); sf.setId(Id);
      model.addAttribute("UpdateFees",SFS.findSchoolFees(sf));
      
      return "FinancePortal";
      }
      @GetMapping("/SaveUpdateFees")
      public String SaveUpdateFess(Model model, SchoolFees fess){
       //  
         //  System.out.println("......."+fess.getFeeSpecification()+"........"+fess.getTerm().getTermName());
       // fess.setMoth(mth);
     try{
      model.addAttribute("msg", SFS.EditFees(fess));
      return "redirect:/FinancePortal/AllFees/";
     }catch(Exception ex){
      model.addAttribute("msg",ex.toString());
    
     } 
       
       return "FinancePortal";
       }
      
}
