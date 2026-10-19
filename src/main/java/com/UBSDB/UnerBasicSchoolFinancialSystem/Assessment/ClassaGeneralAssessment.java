/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 *
 * @author enock
 */
@Component
public class ClassaGeneralAssessment {

   private List<Assessment> SubjectScores;
//create map of elements (Subjects) //create assessments
    
  

    public Set<String> getSubjectNames(List<Assessment> SubjectScores) {
        return SubjectScores.stream().map(Assessment::getSubject).sorted(Comparator.comparing(Subject::getSubjectName)).
                 collect(Collectors.toList()).stream().map(Subject::getSubjectName).
                 collect(Collectors.toSet());
               
    
    }

   
    public List< Assessment> getSubjectScores() {
        return SubjectScores;
    }

    public void setSubjectScores(List< Assessment> SubjectScores) {
        this.SubjectScores = SubjectScores;
    }
}
