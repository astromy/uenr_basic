//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.Subject;

import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;


@Entity
public class Subject {
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO
    )
    private long id;
    @ManyToMany
    @JoinTable(
        name = "Class_Subjects",
        joinColumns = {@JoinColumn(
    name = "id"
)},
        inverseJoinColumns = {@JoinColumn(
    name = "ClassName"
)}
    )
    private List<klass> klaases;
    private String SubjectName;

    public Subject() {
    }

    public List<klass> getKlaases() {
        return this.klaases;
    }

    public SubjectResponse SubjectResponse() {
        return new SubjectResponse(this.id, this.SubjectName);
    }

    public void setKlaases(List<klass> klaases) {
        this.klaases = klaases;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public String getSubjectName() {
        return this.SubjectName;
    }

    public void setSubjectName(String SubjectName) {
        this.SubjectName = SubjectName;
    }
}
