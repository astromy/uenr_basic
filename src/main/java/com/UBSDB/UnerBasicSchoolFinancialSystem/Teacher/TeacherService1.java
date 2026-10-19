/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher;

import com.UBSDB.UnerBasicSchoolFinancialSystem.TKS.TeachrKlassSubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.TKS.TeachrKlassSubject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.SubjectService;

import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserRepository;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.role;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author enock
 */
@Service
public class TeacherService1 {

    @Autowired
    private TeacherRepository1
            TR;
    @Autowired
    private SubjectService SS;
    @Autowired
    private TeachrKlassSubjectService TKS;
    @Autowired
    private UserService US;    @Autowired
    private UserRepository UR;
   @Autowired
   private klassService KS;
     public String CreateTeacherAccount(Teacher t){
        users user=new users();
             BCryptPasswordEncoder encode=new BCryptPasswordEncoder();
        try{user.setEnabled(true);
            user.setUsername(t.getStaffId());
      
           user.setPassword(encode.encode(t.getStaffId()+"ubs"));
            
            Set<role>roles=new HashSet<>();
            roles.add(getTeacherRoleId());
           user.setRoles(roles);
           if(US.checkIfUserNameIsAssigned(user).equals("false")){
          
         
            US.addUser(user);  return "User Name: "+user.getUsername()+" Password: " + t.getStaffId()+"ubs";
     
           }else{
             return "USER NAME HAS BEEN ASSIGNED \n PLEASE CANT CREATE ACCOUNT UPDATE USER TO CREATE ACCOUNT";
     
           }
             }catch(Exception ex){ex.printStackTrace();
        return "error saving teacher please contact admin "+ex.toString()+" / "+t.getTeacherId()
             + " / "   +t.getFirstName()+" / "+t.getPhoneNumber();
        }
   
    }
     
     public Teacher findbyStaffId(String StaffId){
     Teacher teacher =TR.getTeacherByStaffId(StaffId);
     return teacher;
     }
    public String addTeacher(Teacher teacher) {
        try {
            teacher.setClassTeacher("NotClassTeacher");
            return TR.save(teacher).getFirstName() + " have been saved Successfully"+CreateTeacherAccount(teacher);
            
        } catch (Exception ex) {
            return " Error contact admin" + ex.toString();
        }

    }
     public String UpdateTeacher(Teacher teacher) {
         System.out.println("=====>>>>>Teacher"+teacher.getTeacherId());
       TR.save(teacher).getFirstName();
         
     try{      return CreateTeacherAccount(teacher) +" Updated Successfully";
            
        } catch (Exception ex) {
            return " Error contact admin" + ex.toString();
        }

    }
     public String MakeClassTeacher(Teacher teacher) {
        try {
             return TR.save(teacher).getFirstName() + " have been saved Successfully";
            
        } catch (Exception ex) {
            return " Error contact admin" + ex.toString();
        }

    }
     public String RemoveClassTeacher(Teacher teacher) {
        try {
             return TR.save(teacher).getFirstName() + " have been removed Successfully";
            
        } catch (Exception ex) {
            return " Error contact admin" + ex.toString();
        }

    }
     public String ChangeClassTeacher
     (Teacher teacher) {
        try {
             return TR.save(teacher).getFirstName() + " have been saved Successfully";
            
        } catch (Exception ex) {
            return " Error contact admin" + ex.toString();
        }

    }
     
     

    public String flagTeacher(Long id) {
        Teacher teacher = TR.findById(id).get();
        teacher.setTeacherStatus("Stopped");
        return addTeacher(teacher);
    }

    public List<Teacher> listOfTeachers() {

        return TR.findAll();
    }

    public Teacher findTeacher(Long TeacherId) {

        return TR.findById(TeacherId).get();
    }
    public List< Subject> getSubjectsThoughtByATeacherInAClass(Long TeacherId,String KlassId){
    return TKS.getTeacherClassSubject(TeacherId, KlassId);
    }
    public String AssignTeacherToClass(Long teacherId, String klasId, Long subjectId) {
        TeachrKlassSubject TkS=new TeachrKlassSubject(findTeacher(teacherId),KS.FindKlass( klasId), SS.findAParticularSubject(subjectId));
        
        return TKS.saveTeacherClassSubject(TkS) ;
    }
     
    public String checkIfSubjectIsAssignedToAteacher(Subject subject, klass klas) {
        return TKS.Check_If_Subject_Exist_For_A_Class(subject.getId(), klas.getClassName());
    }

    public String getTeacherFullName(Long id) {
        Teacher teacher = TR.findById(id).get();

        return teacher.getFirstName() + " " + teacher.getLastName();
    }

    public List<Subject> Subjecs_Not_Assigned_To_A_Teacher_In_A_Particular_Class(klass klass) {
        List<Subject> subjects = new ArrayList<>();
        for (Subject S : SS.Allsubjects()) {
            for (klass k : S.getKlaases()) {
                if (k.getClassName().equals(klass.getClassName())) {
                    if (checkIfSubjectIsAssignedToAteacher(S, klass).equals("Not")) {
                        subjects.add(S);
                    }
                }
            }
        }
        return subjects;

    }

    public Set<klass> Teacher_Assigned_Klasses(Long id) {
      return  TKS.get_Teacher_Klass(id);
    }

    public List<TeachrKlassSubject> SubjectThoughtByTeachersInAParticularClass(String klassId) {
        
        return TKS.getKlassSubjects(klassId);
    }
    public String removeSubjectFromTeacherSubjects(Long TeacherId,Long SubjectId){
   return TKS.removeTeacher(TeacherId, SubjectId);
    
    }
    private role getTeacherRoleId(){
        if(US.FindUserRoles().isEmpty()){
        role rol=new role();
        rol.setName("Teacher");
     return rol;
        }else{
       
       if(US.FindUserRoles().get("Teacher")==null){
        role rol=new role();
        rol.setName("Teacher");
     return rol;
       }else{
     Integer get = US.FindUserRoles().get("Teacher");
     return US.findparticularRole(get);
     
       }
    }
    }
    
}
