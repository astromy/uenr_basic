/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.mock.MockDetails;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.SubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.Mock;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.MockService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author lys
 */
@Controller
public class MockDetailsController {
    @Autowired private MockDetailsService mDS;
       @Autowired private SubjectService sS;
    @Autowired private MockService ms;
    @GetMapping("mockgeneral")
    public String generalInfo( Model m){
    try{
    
    m.addAttribute("generalM", true);
    
    }catch (Exception ex){
     m.addAttribute("err", "PLEASE CONTACT ADMIN");
      System.out.println(ex.toString());
        }
    return "index";
    }
   
    @GetMapping("/ShowAddMockDetailsForm")
    public String MockDetailsForm(Model m){
    try{
    m.addAttribute("MockDetailsform", new mockDetails());
            
        }catch (Exception ex){
        
      m.addAttribute("err", "PLEASE CONTACT ADMIN");
      System.out.println(ex.toString());
       
        }
     return "index";
    }
    
     @GetMapping("/ShowUpdateMockDetailsForm/{id}")
    public String MockDetailsForm(Model m,@PathVariable("id")Long id){
    try{
    m.addAttribute("MockDetailsform",mDS.findMockDetails(id).get());
            
        }catch (Exception ex){
        
      m.addAttribute("err", "PLEASE CONTACT ADMIN");
      System.out.println(ex.toString());
       
        }
     return "index";
    }
    
    
    @PostMapping("/SaveUpdateMockDetails")
    public String SaveUpdateMockDetails(Model m, mockDetails mckdetails){
    try{
    mckdetails.setMockDetails(sS.findSubjectDoneByAParticularClass("JHS3A"));
    System.out.println(mckdetails.getSubjectsa());
    mckdetails.setSubjects(mckdetails.getSubjects());
    mckdetails.setHashCode((mckdetails.hashCode()+mckdetails.hashCode())+"");
    m.addAttribute("msg",mDS.savemockDetails(mckdetails).getMockName()+" has been saved with duration "+mckdetails.getStartDate()+" to "+mckdetails.getEndDate() );
       
        }catch (Exception ex){
        
     m.addAttribute("err", "PLEASE CONTACT ADMIN");
      System.out.println(ex.toString());
    
        }
    return "index"; 
    }
    
    
    @GetMapping("/AllMocks")
    public String AddMockDetails(Model  m ){
    try{
       List <mockDetails>mockDetailss = new ArrayList<>();
        Runnable r1=()->{
        mDS.AllMockDetails().stream().forEach(c->{
         Set<Subject>Submittedsubjects= mDS.MockSubmittedSubjects(c);
        c.setSubjects(mDS.MockExpectedSubjects(c).size());
       c.setSubmitted(Submittedsubjects.size());
       c.setSubmittedNames(Submittedsubjects.stream().map(Subject::getSubjectName).collect(Collectors.toSet()).toString());
     List<Student>studs = new ArrayList<>();
       mDS.AllKlasses(c).stream().map(klass::getStudents)
              .forEach(s->
              {
                  s.stream().filter(w->w.getStudentStatus().equalsIgnoreCase("in School")).collect(Collectors.toList()).forEach(t->{
              studs.add(t);
              });
              });
         c.setTotalStudents(studs.size());
           c.setStudentsResultsUploaded(mDS.MockSubmittedSubjectsStudents(c)+" ");
       mockDetailss.add(c);
               });
        };
        //mockDetailss.removeIf(c->LocalDate.parse(c.getStartDate()).isBefore(LocalDate.now())&&LocalDate.parse(c.getEndDate()).isAfter(LocalDate.now()));
         r1.run();
           m.addAttribute("allmocks", mockDetailss);
           m.addAttribute("Submitted", mockDetailss);
        }catch (Exception ex){
        
          m.addAttribute("err", "PLEASE CONTACT ADMIN");
      System.out.println(ex.toString());
    
        }
    return "index"; 
    }
}
