/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.mock.MockDetails;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.SubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.Mock;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.MockService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lys
 */
@Service
public class mockDetailsImpl implements MockDetailsService{
@Autowired private mockDetailsRepository mDR;
@Autowired private SubjectService sS;
@Autowired private MockService MS;
    @Override
    public mockDetails savemockDetails(mockDetails mockDetails) {
        try{
        return mDR.save(mockDetails);
        }catch (Exception ex){
        System.out.println(ex);
        return null;
        }
    
    }

    @Override
    public List<mockDetails> AllMockDetails() {
   try{
        return mDR.findAll();
        }catch (Exception ex){
        System.out.println(ex);
        return null;
        }
    }

    @Override
    public Optional<mockDetails> findMockDetails(Long id) {
  try{
        return mDR.findById(id);
        }catch (Exception ex){
        System.out.println(ex);
      return null;
        }  }

    @Override
    public mockDetails EditmockDetails(mockDetails mockDetails) {
   try{
        return mDR.save(mockDetails);
        }catch (Exception ex){
        System.out.println(ex);
        return null;
        }  }

    @Override
    public List<mockDetails> findAllMocksByDate(LocalDate date) {
   return mDR.findAll().stream().filter(c->date.isBefore(LocalDate.parse
        (c.getEndDate()))||date.isAfter(
        (LocalDate.parse
        (c.getStartDate())))).collect(Collectors.toList());
    }

    @Override
    public  List<Subject> MockExpectedSubjects(mockDetails mD) {
  if ( mD.getMockDetails().isEmpty()){
  return new ArrayList<Subject>();
  }else{
  Mock Temp = mD.getMockDetails().stream().findAny().get();
  return  sS.findSubjectDoneByAParticularClass(Temp.getStudentClass().getClassName());
  }
   
         
    
 
    

    }

    @Override
    public Set<Subject> MockSubmittedSubjects(mockDetails mD) {
        if ( mD.getMockDetails().isEmpty()){
        return new HashSet<Subject>();
        }else{
      Set<Subject>subjects=mD.getMockDetails().stream().map(Mock::getSubject).collect(Collectors.toSet()) ;
    return subjects;  
        }

    
 }

    @Override
    public List<Subject> MockSubmittedSubjectsStudents(mockDetails mD) {
     if ( mD.getMockDetails().isEmpty()){
        return new ArrayList<Subject>();
        }else{
      List<Subject>subjects=mD.getMockDetails().stream().map(Mock::getSubject).collect(Collectors.toList()) ;
    return subjects;  
        }
 // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Set<klass> AllKlasses(mockDetails md) {
      if ( md.getMockDetails().isEmpty()){
         return new HashSet<>();
        }else{
      Set klasses=md.getMockDetails().stream().map(Mock::getStudentClass).collect(Collectors.toSet()) ;
    return klasses;  
        }
    
    }
    
}
