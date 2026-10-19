//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.Subject;

import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository Subjectrepo;

    public SubjectService() {
    }

    public String saveSubject(Subject subject) {
        return this.Subjectrepo.save(subject) + " saved Successfully";
    }

    public List<Subject> Allsubjects() {
        return this.Subjectrepo.findAll();
    }

    public void AllChangeRME() {
        this.Allsubjects().stream().forEach((U) -> {
            if (U.getSubjectName().equalsIgnoreCase("Creative Arts(CA)")) {
                U.setSubjectName("C. ARTS");
                this.saveSubject(U);
            }

        });
    }

    public Subject findAParticularSubject(Long id) {
        return (Subject)this.Subjectrepo.findById(id).get();
    }

    public Subject findASubjectByName(String name, klass klass) {
        return (Subject)this.Allsubjects().stream().filter((c) -> {
            return c.getSubjectName().equals(name);
        });
    }

    public String removeSubjects() {
        try {
            this.Subjectrepo.deleteAll();
            return "removed and cleared subject table successfully";
        } catch (Exception var2) {
            return "fatal error please try again next time";
        }
    }

    public List findSubjectDoneByAParticularClass(String klassId) {
        List<Subject> TempSubject = new ArrayList();
        Iterator var3 = this.Subjectrepo.findAll().iterator();

        while(var3.hasNext()) {
            Subject sub = (Subject)var3.next();
            Iterator var5 = sub.getKlaases().iterator();

            while(var5.hasNext()) {
                klass klass = (klass)var5.next();
                if (klass.getClassName().equals(klassId)) {
                    TempSubject.add(sub);
                }
            }
        }

        return TempSubject;
    }

    public Subject findSubjectByVideo(String subjectName, String klass) throws Exception {
        try {
            String[] tSubjectName = subjectName.split("_");
            List<Subject> subjects = this.findSubjectDoneByAParticularClass(klass);
            Subject sub = (Subject)subjects.stream().filter((c) -> {
                return c.getSubjectName().equals(tSubjectName[1]);
            }).findFirst().get();
            return sub;
        } catch (Exception var6) {
            return null;
        }
    }
}
