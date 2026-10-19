/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.mock;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.*;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 *
 * @author enock
 */
@Component
public class MockAssessment {

   private List<Mock> SubjectScores;
//create map of elements (Subjects) //create assessments
    
  

    public Set<String> getSubjectNames(List<Mock> SubjectScores) {
        return 
                SubjectScores.stream().map(Mock::getSubject).sorted(Comparator.comparing(Subject::getSubjectName)).
                 collect(Collectors.toList()).stream().map(Subject::getSubjectName).
                 collect(Collectors.toSet());
               
    
    }

   
    public List< Mock> getSubjectScores() {
        return SubjectScores;
    }

    public void setSubjectScores(List<Mock> SubjectScores) {
        this.SubjectScores = SubjectScores;
    }
}
