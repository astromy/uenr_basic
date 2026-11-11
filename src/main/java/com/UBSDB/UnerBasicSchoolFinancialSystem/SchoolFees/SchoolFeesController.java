/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill.StudentBill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill.StudentBillService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Billing.DailyBillingService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.PTA.PTAService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
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
  private @Autowired TermService termService;
  private  @Autowired StudentBillService studentBillService;
  @Autowired
  private DailyBillingService dailyBillingService;

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

    @GetMapping("/BillStudents")
    public String saveStudentFess(Model model){
             try{
                 ks.AllKlass().forEach(klass -> {
                     var tempSchoolFees =klass
                             .getSchoolFees()
                             .stream()
                             .filter(c
                                     ->c.getTerm()
                                     .getTermName()
                                     .equals(termService.getCurrentTerm().getTermName())).findFirst();
                           if (tempSchoolFees.isPresent()){
                            for (Student stud : klass.getStudents()){
                             var studentBill = StudentBill
                             .builder()
                             .fees(tempSchoolFees.get())
                                     .student(stud)
                                     .term(tempSchoolFees.get().getTerm())
                                     .amount((float) tempSchoolFees.get().getAmount())

                             .build();
                     studentBillService.saveStudentBill(studentBill);
                            }
                        }});
               return "Billed Successfully";
        }catch(Exception ex){
            model.addAttribute("msg",ex.toString());
           ex.printStackTrace();
        }

        return " null ";
    }


    @GetMapping("/BillStudentsNewAdmission")
    public String saveStudentsNewAdmission(Model model){
        try{
            ks.AllKlass().forEach(klass -> {
                var tempSchoolFees =klass
                        .getSchoolFees()
                        .stream()
                        .filter(c
                                ->c.getTerm()
                                .getTermName()
                                .equals((termService.getCurrentTerm().getTermName()))&&
                                        (c.getFeeType().equals("New_Admission"))).findFirst();
                if (tempSchoolFees.isPresent()){
                    for (Student stud : klass.getStudents()){
                        var studentBill = StudentBill
                                .builder()
                                .fees(tempSchoolFees.get())
                                .student(stud)
                                .term(tempSchoolFees.get().getTerm())
                                .amount((float) tempSchoolFees.get().getAmount())

                                .build();
                        studentBillService.saveStudentBill(studentBill);
                    }
                }});
            return "Billed Successfully";
        }catch(Exception ex){
            model.addAttribute("msg",ex.toString());
            ex.printStackTrace();
        }

        return " null ";
    }

    /**
     * Endpoint to manually trigger the background billing process.
     * Runs the billing in a background thread so the user is not blocked.
     *
     * @param model the Spring model
     * @return the FinancePortal view with a status message
     */
    @GetMapping("/RunBillingProcess")
    public String runBillingProcess(Model model) {
        try {
            String result = dailyBillingService.runBillingManually();
            model.addAttribute("msg", result);
        } catch (Exception ex) {
            model.addAttribute("msg", "Error starting billing process: " + ex.toString());
            ex.printStackTrace();
        }
        return "FinancePortal";
    }

    /**
     * Endpoint to run the billing process synchronously (waits for completion).
     *
     * @param model the Spring model
     * @return the FinancePortal view with a status message
     */
    @GetMapping("/RunBillingProcessSync")
    public String runBillingProcessSync(Model model) {
        try {
            String result = dailyBillingService.runBillingSynchronously();
            model.addAttribute("msg", result);
        } catch (Exception ex) {
            model.addAttribute("msg", "Error running billing process: " + ex.toString());
            ex.printStackTrace();
        }
        return "FinancePortal";
    }


}
