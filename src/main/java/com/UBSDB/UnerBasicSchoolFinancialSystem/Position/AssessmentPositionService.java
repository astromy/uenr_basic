/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Position;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.Assessment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.AssessmentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.Mock;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.MockDetails.mockDetails;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author HP
 */
@Component
public class AssessmentPositionService {
   @Autowired private AssessmentService AS;@Autowired private TermService TS;
   @Autowired private StudentService SS; @Autowired private klassService KS;
    public String getStudentPosition(Student student,List<AssessmentPosition>allStudents){
    return allStudents.stream().filter(c->c.getStudent().getStudentId().equals(student.getStudentId())).findFirst().get().getPosition();
    
    
    }
    public List<AssessmentPosition>createStudentPositionInClass(List<Student> students,Term t){
    List<AssessmentPosition>positions=new ArrayList<>();
    List<AssessmentPosition>Temppositions=new ArrayList<>();
     
   // students.removeIf(c->  SS.findStudent(c.getStudentId()).getAssessment().isEmpty());

    students.forEach(g->{
            int count=0;
    AssessmentPosition postion=new AssessmentPosition();  
    postion.setStudent(g);
    List<Assessment>assessment=
    SS.findStudent(g.getStudentId()).getAssessment()
    .stream().filter(c->Objects.equals(c.getTerm().getId(), t.getId()))
                    .collect(Collectors.toList());
    if (assessment.isEmpty()){
        count++;
        System.out.println(count+"  checking----->   ");
    postion.setTotalScore(0.0);
    
    }else{
    postion.setTotalScore(assessment.stream().map(Assessment::getTotalScore).reduce(0.0, (a,b)->a+b));
    positions.add(postion);
    }
    });
    
   int a=1;
     for(AssessmentPosition p:  positions.stream()
             .sorted(Comparator.comparing(AssessmentPosition::getTotalScore).reversed()).collect(Collectors.toList())){
    p.setPosition(AS.AddRankEnding(a));
    
    a++;
    Temppositions.add(p);
    }
 
     return Temppositions;
    }
    
     public List<AssessmentPosition>createStudentPositionMockInClass(List<Student> students,mockDetails d){
    List<AssessmentPosition>positions=new ArrayList<>();
    List<AssessmentPosition>Temppositions=new ArrayList<>();
     
   // students.removeIf(c->  SS.findStudent(c.getStudentId()).getAssessment().isEmpty());

    students.forEach(g->{
            int count=0;
    AssessmentPosition postion=new AssessmentPosition();  
    postion.setStudent(g);
    List<Mock>assessment=
    SS.findStudent(g.getStudentId()).getMocks()
    .stream().filter(c->Objects.equals(c.getMockDetails().getId(), d.getId()))
                    .collect(Collectors.toList());
    if (assessment.isEmpty()){
        count++;
        System.out.println(count+"  checking----->   ");
    postion.setTotalScore(0.0);
    
    }else{
    postion.setTotalScore(assessment.stream().map(Mock::getTotalScore).reduce(0.0, (a,b)->a+b));
    positions.add(postion);
    }
    });
    
   int a=1;
     for(AssessmentPosition p:  positions.stream()
             .sorted(Comparator.comparing(AssessmentPosition::getTotalScore).reversed()).collect(Collectors.toList())){
    p.setPosition(AS.AddRankEnding(a));
    
    a++;
    Temppositions.add(p);
    }
 
     return Temppositions;
    }
    public AssessmentPosition getStudentPostitionInClass(Student student,List<Student>students,Term t){
        
    return createStudentPositionInClass(students,t)
            .stream().filter(c->c.getStudent().getStudentId()==student.getStudentId()).findFirst().get();
    }
     public AssessmentPosition getStudentPostitionInClass(Student student,List<Student>students,mockDetails md){
        
    return createStudentPositionMockInClass(students,md)
            .stream().filter(c->c.getStudent().getStudentId()==student.getStudentId()).findFirst().get();
    }
}  

