/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.klass;

import com.UBSDB.UnerBasicSchoolFinancialSystem.TKS.TeachrKlassSubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.TeacherService1;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.role;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.security.Principal;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

public class klassController {
    private static final Logger log = LoggerFactory.getLogger(klassController.class);
    @Autowired
    private TeachrKlassSubjectService TkSs;
        @Autowired
    private TeacherService1 TS;
  
    @Autowired
    private klassService kS;
   @Autowired
        private UserService US;
    @GetMapping("/klass")
 
 public String saveKlass() {
 
     kS.AddClasses().stream().map(k -> {
        // System.out.print(k.getClassName());
            return k;
        }).forEachOrdered(k -> {
            kS.saveKlass(k);
        });return "index";
 }
 @GetMapping("/Claases")
 public String ShowClasses(Principal principal,Model model){
  var output =kS.AllKlass().stream().map(klass::getKlassResponse).toList();
  System.out.println(output.toString());
        model.addAttribute("klasses",output);

 users user=US.FindUserById(principal.getName());
    for(role r:user.roles){
    if(r.getName().equals("HeadTeacher")){
    return "HeadTeacherPortal";
    }
    }
 return "index";
 }

 @GetMapping("/api/GetKlass/{id}")
 public ResponseEntity<klassReponse>GetKlassResponse
         (@PathVariable ("id") String className){
        try{
           klassReponse response =kS.FindKlass(className).getKlassResponse();
     return  ResponseEntity.ok(response);
        }catch (Exception ex){
       return ResponseEntity.ok(null);
        }

 }
    @PostMapping("/api/EditKlass")
    public ResponseEntity<klassReponse>GetKlassResponse
            (KlassRequest request){
        try{
            log.info(request.toString());
       klass klass = kS.FindKlass(request.getClassName());
       klass.setFloralName(request.getFloralName());
       klass.setClassName(request.getClassName());
       klassReponse response =kS.saveKlass(klass);
       return ResponseEntity.ok(response);
        }catch (Exception ex){
            return ResponseEntity.ok(null);
        }

    }
  @GetMapping("/ClaasMasters")
 public String ShowClassesMaster(Principal principal,Model model){
 model.addAttribute("klassMasters",kS.GetClassTeachersInTheWholeSchool());
 users user=US.FindUserById(principal.getName());
    for(role r:user.roles){
    if(r.getName().equals("HeadTeacher")){
    return "HeadTeacherPortal";
    }
    }
 return "index";
 }  
 @GetMapping("/AssignClassTeacher/{TeacherId}/{ClassId}")
    public String AssignClassTeacher(@PathVariable("TeacherId")Long TeacherId,@PathVariable("ClassId")String ClassId,
            Model model,Principal p){
         if(TkSs.getClassMaster(ClassId)==null){
      model.addAttribute("msg",TkSs.makeTeacherAClassTeacher(TeacherId, ClassId));
    
        }else{
             Teacher t=  TkSs.getClassMaster(ClassId).getTeacher();
             model.addAttribute("msg",
      "Please Class Teacher has been assigned to "+t.getFirstName()+" "+t.getLastName()  );
    
      
               }
    
    return "index";
    }
       
}
