/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Subject;

import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author enock
 */
@Component
public class SubjectSeeder {

    @Autowired
    private SubjectService SS;
    @Autowired
    private klassService kS;

    public String saveSubjectsForKindagatten(String subjectName, String ClassLevel) {
        Subject newSubject = new Subject();
        List<klass> klasses = new ArrayList<>();
        for (klass klas : kS.AllKlass()) {
            if (klas.getClassLevel().equals(ClassLevel)) {
                klasses.add(klas);
            }
        }
        newSubject.setSubjectName(subjectName);
        newSubject.setKlaases(klasses);
        return SS.saveSubject(newSubject);
    }

    public String saveSubjectForSchool(String subjectName) {

        Subject newSubject = new Subject();
        List<klass> klasses = new ArrayList<>();
        kS.AllKlass().stream().filter(klas -> (klas.getClassLevel().equals("UpperPrimary") 
                || klas.getClassLevel().equals("LowerPrimary")
                || klas.getClassLevel().equals("JuniorHigh"))).forEachOrdered(klas -> {
                    klasses.add(klas);
        });
        newSubject.setSubjectName(subjectName);
        newSubject.setKlaases(klasses);
        return SS.saveSubject(newSubject);

    }

    public String saveSubjectForPimarySchool(String subjectName) {

        Subject newSubject = new Subject();
        List<klass> klasses = new ArrayList<>();
        kS.AllKlass().stream().filter(klas -> (klas.getClassLevel().equals("UpperPrimary") || klas.getClassLevel().equals("LowerPrimary"))).forEachOrdered(klas -> {
            klasses.add(klas);
        });
        newSubject.setSubjectName(subjectName);
        newSubject.setKlaases(klasses);
        return SS.saveSubject(newSubject);

    }

    public String SeedAllData() {
       
       saveSubjectsForKindagatten("English", "Kindergaten");
        saveSubjectsForKindagatten("Arithmetic", "Kindergaten");
        saveSubjectsForKindagatten("Music, Art/Craft)", "Kindergaten");
        saveSubjectsForKindagatten("Our World and Our People(OWOP))", "Kindergaten");
        saveSubjectsForKindagatten("Scriptures", "Kindergaten");

        saveSubjectsForKindagatten("Physical Education", "Kindergaten");
        saveSubjectsForKindagatten("Environmeantal Studies", "Kindergaten");

        saveSubjectForPimarySchool("Creative Arts(CA)");
        saveSubjectForPimarySchool("Physical Education");

        saveSubjectForPimarySchool("Our World and Our People(OWOP)");

        saveSubjectForSchool("Computing");
        saveSubjectForSchool("Ghanian Language");

        saveSubjectForSchool("Mathematics");
        saveSubjectForSchool("French");
        saveSubjectForPimarySchool("RME");
        saveSubjectForPimarySchool("History");
        saveSubjectForSchool("English");
        saveSubjectForPimarySchool("Science");

        saveSubjectsForKindagatten("Social Studies", "JuniorHigh");
        saveSubjectsForKindagatten("Integrated Science)", "JuniorHigh");
        saveSubjectsForKindagatten("career Technology", "JuniorHigh");

        saveSubjectsForKindagatten("Creative Arts and Design ", "JuniorHigh");
        saveSubjectsForKindagatten("Library Skills", "JuniorHigh");
     
      saveSubjectsForKindagatten("RME", "JuniorHigh");
       return "";
    }

    //l("LowerPrimary");("UpperPrimary");
public String removeSubject(){

return SS.removeSubjects();
}
}
