/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Subject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author enock
 */
@Controller
public class SubjectController {
    private @Autowired SubjectSeeder subjectSeeder;
     private @Autowired SubjectService SubS;
     
  		
      
     @GetMapping("/subjectRemove")
    public @ResponseBody String removeAllSubjects(){
    
    return  subjectSeeder.removeSubject();
    }
      @GetMapping("/EditSubject")
    public String EditSubject(Model model , Subject subject){
   try{
        Subject Tsubject=SubS.findAParticularSubject(subject.getId());
    Tsubject.setSubjectName(subject.getSubjectName());
   model.addAttribute("msg",SubS.saveSubject(Tsubject));
   }catch (Exception ex){
   System.out.println("Error at --> saving Editted Subject ...."+ ex.toString());
   }return "redirect:/GellSubjects";
    }
    @GetMapping("subjectAdd")
    public @ResponseBody String saveSubjects(){
    
    SubS.AllChangeRME();
    return "check";
    }
    @GetMapping("/GellSubjects")
    public String ListAllSubject(Model  model){
   Map   <Subject,Set<String>>SubjectKlass=new HashMap<>();
  SubS.Allsubjects().stream().forEach(c->{
  Set <String >s=new HashSet<>();
     c.getKlaases().forEach(k->{
     s.add(k.getClassLevel());
     });
     SubjectKlass.put(c, s);
  });
   model.addAttribute("SubjectsInschool", SubjectKlass);
    
    return "index";
    }
    @GetMapping("/ShowSubjectForm/{id}")
    public String ShowSubjectForm(Model model,@PathVariable("id")Long id){
   // Subject s=new Subject();s.setId(id);
    
    model.addAttribute("UpdateSubject", SubS.findAParticularSubject(id));
   
    return "index";
    }
    @GetMapping("/UpdateSubject")
        
        public @ResponseBody String UpdateSubject(Model model, Subject subject){
           SubS.Allsubjects().stream().forEach(c->{
            
            if(c.getSubjectName().equalsIgnoreCase("Mathematics")){
            c.setSubjectName("Maths");
            SubS.saveSubject(c);
            }
            
            });
            
            
            return "index";
              }
      @GetMapping ("/seedSubjects")
    public ResponseEntity<String> seedSubjects () {
         try{

       return  ResponseEntity.ok( subjectSeeder.SeedAllData());
         }catch(Exception ex){

             ex.printStackTrace();
      return ResponseEntity.ok("Error adding subjects");
         }



    }

    {


    }
}
