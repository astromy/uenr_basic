/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Term;


import com.UBSDB.UnerBasicSchoolFinancialSystem.Academic.Academic;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Academic.AcademicService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.role;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author enock
 */

@Controller
public class TermController {
 @Autowired AcademicService AS;
    @Autowired
 private TermService TS;
   @Autowired
 private UserService US;
    @GetMapping("/term")
    public String addTerm(Model model){
       if(AS.getAcademicyear_Date(LocalDate.now()).isPresent()){
     Term t =new Term();
     t.setAcademicyear(AS.getAcademicyear_Date(LocalDate.now()).get());
           model.addAttribute("newterm",t
           );
           List<Academic>academics=AS.AllAcademic();
           academics.removeIf(c->c.getTerm().size()>=3);
       model.addAttribute("years",
              academics);
        }else{ 
      return "redirect:/showAcademicForm";
        }
    return "index";
    }
    @GetMapping("Termgeneral")
    public String generalInfo( Model m){
        try{
        m.addAttribute("generalT", true);
            
        }catch (Exception ex){
     m.addAttribute("err", "PLEASE CONTACT ADMIN");
      System.out.println(ex.toString());
        }
    return "index";
    }
    
    @PostMapping("/saveTerm")
    public String saveTerm(Model model,Term term){
     if(term.getStartDate().equals("")||term.getEndDate().equals("")){
       model.addAttribute("newterm",
           new Term());
       model.addAttribute("year",
              AS.getAcademicyear_Date(LocalDate.now()).get());
       model.addAttribute("msg","please fill the form correctly");
   
      return "index";
     }
    model.addAttribute("msg",TS.SaveTerm(term));
    return "index";
    }
    
    @PostMapping("/EditTerm")
    public String EditTerm(Model model,Term term){
     
     
    model.addAttribute("msg",TS.updateTerm(term));
    return "index";
    }
    
    
    @GetMapping("/getAllTerms")
    public String getAllTerms(Model model,Principal pp){
     model.addAttribute("Terms",TS.terms());
     users user=US.FindUserById(pp.getName());
    for(role r:user.roles){
         switch (r.getName()) {
             case "HeadTeacher":
                 return "HeadTeacherPortal";
             case "Admin":
                 return "index";
             default:
                 return "login";
         }
    }
    return null;
  
    
    }
     @GetMapping("/EditTerm/{id}")
    public String EditTerms(Model model,@PathVariable("id")long id){
     model.addAttribute("UpdateTerm",TS.findTermById(id).get());
     // model.addAttribute("newterm",TS.CheckIfTermExistForTheAcademicYear());
      model.addAttribute("year",TS.findTermById(id).get().getAcademicyear().getAcademicName());
 
        return "index";
    
    }
     @GetMapping("/DeleteTerm/{id}")
    public String getAllDelete(Model model,@PathVariable("id")long id){
     model.addAttribute("Terms",TS.DeleteById(id));
     // model.addAttribute("newterm",TS.CheckIfTermExistForTheAcademicYear());
   //   model.addAttribute("year",TS.CheckIfTermExistForTheAcademicYear().getAcademicyear().getAcademicYear().getYear());
 
        return "index";
    
    }
    
    }
    

