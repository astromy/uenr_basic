/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Attendance;

import java.security.Principal;
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
@RequestMapping("TeacherPortal/")
public class AttendanceController {
    @GetMapping("/AttendanceForm/{ClassId}")
    public String AttendanceForm(Model model,Principal principal,@PathVariable("ClassId")String ClassId){
    model.addAttribute("AttendanceForm",true);
    model.addAttribute("ClassId",ClassId);
    
    return "TeacherPortal";
    }
    @GetMapping("/SaveStudentAttendanceForm")
    public String SaveStudentAttendanceExel(Model model,Principal principal,Attendance attendance){
    model.addAttribute("AttendanceForm",new Attendance());
    return "TeacherPortal";
    }
}
