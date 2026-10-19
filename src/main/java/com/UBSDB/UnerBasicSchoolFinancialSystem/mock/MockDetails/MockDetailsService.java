/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.mock.MockDetails;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.Mock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.hibernate.mapping.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author lys
 */
@Component
public interface MockDetailsService {
    
    public mockDetails savemockDetails(mockDetails mockDetails);
    
    public List<mockDetails>AllMockDetails();
    public Optional<mockDetails>findMockDetails(Long id);
     public mockDetails EditmockDetails(mockDetails mockDetails); 
     public List<mockDetails>findAllMocksByDate(LocalDate date);
     public java.util.List<Subject> MockExpectedSubjects(mockDetails mD);
     public java.util.Set<Subject>MockSubmittedSubjects(mockDetails mD);
      public java.util.List<Subject>MockSubmittedSubjectsStudents(mockDetails mD);
      public  Set<klass> AllKlasses(mockDetails md);
}
