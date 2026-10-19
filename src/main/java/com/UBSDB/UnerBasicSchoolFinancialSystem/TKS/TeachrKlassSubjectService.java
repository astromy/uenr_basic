/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.TKS;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.TeacherService1;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author enock
 */
@Service
public class TeachrKlassSubjectService {
    @Autowired
    private TeachrKlassSubjectRepository TKSR;
    @Autowired
    private TeacherService1 TS;
    
    public String saveTeacherClassSubject(TeachrKlassSubject TKS){
    try{
    TKSR.save(TKS);
    return "Subject has been added successfully";
    }catch(Exception ex){
    return "error contact admin  ------>>>>"+ex.toString();
    }}
    
    public List<Subject> getTeacherClassSubject(long teacherId,String klassId){
    List<Subject>TempSubject=new ArrayList<>();
        for(TeachrKlassSubject TKS:TKSR.findAll()){
    if((TKS.getKlas().getClassName().equals(klassId))&&(TKS.getTeacher().getTeacherId()==(teacherId))){
    TempSubject.add(TKS.getSubjects());} 
    }
    return TempSubject;
 
    }
    public String Check_If_Subject_Exist_For_A_Class(long Subject, String klass){
    for(TeachrKlassSubject TKS:TKSR.findAll()){
    if((TKS.getKlas().getClassName().equals(klass))&&(TKS.getSubjects().getId()==Subject)){
    
    return "exist";
    }
    }
    return "Not";
    }
     public Optional<TeachrKlassSubject>  findTKS(TeachrKlassSubject k){
     
     return TKSR.findById(k.getId());
     }  
        public List<TeachrKlassSubject>  findAllTKS(){
     
     return TKSR.findAll();
     } 
    public Set<klass>get_Teacher_Klass(long TeacherId){
        Set<klass>TeacherKlass=new HashSet<>();
    for(TeachrKlassSubject TKS:TKSR.findAll()){
    if(TKS.getTeacher().getTeacherId()==TeacherId){
        
        TeacherKlass.add(TKS.getKlas());
        
    }}
    return TeacherKlass;
    }
    public String removeTeacher(long TeacherId,long SubjectId){
    try{
        for(TeachrKlassSubject tks:TKSR.findAll()){
        if(tks.getTeacher().getTeacherId()==(TeacherId)&&((tks.getSubjects().getId()==SubjectId))){
        TKSR.delete(tks);
         return " successfully removed   ";
   
        }
        }
    }catch(Exception ex){
    
    return "Fatal Error contact admin "+ex.toString();
    }
    return "null";
    }
    public List<TeachrKlassSubject>getKlassSubjects(String klassId){
    List<TeachrKlassSubject>KlassSubjects=new ArrayList<>();
    for(TeachrKlassSubject TKS:TKSR.findAll()){
    if(TKS.getKlas().getClassName().equals(klassId)){
    
    KlassSubjects.add(TKS);
    }
    
    }
    return KlassSubjects;
    }
    public TeachrKlassSubject getClassMaster(String KlassId){
    
        for(TeachrKlassSubject TKS:getKlassSubjects( KlassId)){
    if(TKS.getTeacher().getClassTeacher().equals(KlassId)){
    return TKS;
    }
  
    }
    return null;
    }
    
    public String makeTeacherAClassTeacher(Long Id,String ClassId){
        Teacher t= TS.findTeacher(Id);
        t.setClassTeacher(ClassId);
        try{
    return TS.MakeClassTeacher(t);
        }catch(Exception ex){
        return "Action unsuccessful "+ex.toString();
        }
    }
     
    
    public List<TeachrKlassSubject>GetTeachersToMakeClassTeachers(String ClassId){
    List<TeachrKlassSubject>TKS=new ArrayList<>();
        for(TeachrKlassSubject tkS:getKlassSubjects(ClassId)){
        if(tkS.getTeacher().getClassTeacher().equals("NotClassTeacher")){
        TKS.add(tkS);
        }
        
        }
    return TKS;
    }
    public List<TeachrKlassSubject>ChangeClassTeacher(Long teacher,String ClassId){
     Teacher t= TS.findTeacher(teacher);
        t.setClassTeacher("NotClassTeacher");TS.addTeacher(t);
    return GetTeachersToMakeClassTeachers( ClassId);
    
    }
   
     public List<TeachrKlassSubject>GetTeacherKlassSubjectForATeacher(String teacher){
     Teacher t= TS.findbyStaffId(teacher);
       
    return TKSR.findAll().stream().filter(c->c.getTeacher().getStaffId().equals(t.getStaffId())).collect(Collectors.toList());
    }
     public Subject FindSubjectTaughtByTeacher(String ClassId,String subjectName){
     Subject sub =new Subject();
   for(TeachrKlassSubject s:  TKSR.findAll().stream().filter(c->c.getSubjects().getSubjectName().equals(subjectName)
             &&c.getKlas().getClassName().equals(ClassId)).collect(Collectors.toList())){
   sub=s.getSubjects();
      }
     return sub;
     }
}
