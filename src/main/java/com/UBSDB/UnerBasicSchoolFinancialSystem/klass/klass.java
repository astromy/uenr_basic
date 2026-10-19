//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.klass;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.Assessment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.Mock;
import com.UBSDB.UnerBasicSchoolFinancialSystem.product.product;
import com.UBSDB.UnerBasicSchoolFinancialSystem.video.Video;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;


@Entity
public class klass implements Serializable {
    @OneToMany(
        mappedBy = "studentklass"
    )
    private List<Student> students;
    @OneToMany(
        mappedBy = "klass"
    )
    private List<Video> Video;
    @OneToMany(
        mappedBy = "klass"
    )
    private List<product> products;
    @OneToMany(
        mappedBy = "klass"
    )
    private List<SchoolFees> schoolFees;
    public int classNumber;
    @Id
    private String className;
    private String floralName;

    public String getFloralName() {
        return floralName;
    }

    public void setFloralName(String floralName) {
        this.floralName = floralName;
    }

    private String ClassClassification;
    @OneToMany(
        mappedBy = "StudentClass"
    )
    private List<Assessment> assessment;
    @OneToMany(
        mappedBy = "StudentClass"
    )
    private List<Mock> Mocks;
    private String ClassLevel;

    public List<Video> getVideo() {
        return this.Video;
    }

    public void setVideo(List<Video> Video) {
        this.Video = Video;
    }

    public List<Student> getStudents() {
        return (List)this.students.stream().filter((C) -> {
            return C.getStudentStatus().equals("in School");
        }).collect(Collectors.toList());
    }

    public List<SchoolFees> getSchoolFees() {
        return this.schoolFees;
    }

    public void setSchoolFees(List<SchoolFees> schoolFees) {
        this.schoolFees = schoolFees;
    }

    public List<product> getProducts() {
        return this.products;
    }

    public void setProducts(List<product> products) {
        this.products = products;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getClassClassification() {
        return this.ClassClassification;
    }

    public int getClassNumber() {
        return this.classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    public void setClassClassification(String ClassClassification) {
        this.ClassClassification = ClassClassification;
    }

    public List<Mock> getMocks() {
        return this.Mocks;
    }

    public void setMocks(List<Mock> Mocks) {
        this.Mocks = Mocks;
    }

    public List<Assessment> getAssessment() {
        return this.assessment;
    }

    public void setAssessment(List<Assessment> assessment) {
        this.assessment = assessment;
    }

    public klass() {
    }

    public klass(String ClassName, String ClassLevel) {
        this.className = ClassName;
        this.ClassLevel = ClassLevel;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String ClassName) {
        this.className = ClassName;
    }

    public String getClassLevel() {
        return this.ClassLevel;
    }

    public void setClassLevel(String ClassLevel) {
        this.ClassLevel = ClassLevel;
    }

    public klassReponse getKlassResponse() {
        return new klassReponse(this.className,
                this.ClassLevel,  floralName,this.ClassLevel + this.className);
    }

}
