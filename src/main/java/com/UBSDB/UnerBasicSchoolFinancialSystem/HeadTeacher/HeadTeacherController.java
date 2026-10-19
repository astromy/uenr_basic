/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.HeadTeacher;

import com.UBSDB.UnerBasicSchoolFinancialSystem.TKS.TeachrKlassSubject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.TKS.TeachrKlassSubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.TeacherService1;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.role;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author enock
 */
@Controller
@RequestMapping("/HeadTeacher/")
public class HeadTeacherController {
    @Autowired
    private TeachrKlassSubjectService TkSs;
     @Autowired
    private UserService US;
        @Autowired
    private TeacherService1 TS;
    @GetMapping("/")
    public String HeadTeacherPortal(Model model,Principal p){
    model.addAttribute("user",p);
    return "HeadTeacherPortal";
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
    
    return "HeadTeacherPortal";
    }
     @GetMapping("/RemoveClassTeacher/{TeacherId}/{ClassId}")
    public String RemoveClassTeacher(@PathVariable("TeacherId")Long TeacherId,@PathVariable("ClassId")String ClassId,
            Model model,Principal p){
      model.addAttribute( "TKS" ,TkSs.ChangeClassTeacher(TeacherId, ClassId));
    users user=US.FindUserById(p.getName());
    for(role r:user.roles){
    if(r.getName().equals("HeadTeacher")){
    return "HeadTeacherPortal";
    }}
    return "index";
    }
}
