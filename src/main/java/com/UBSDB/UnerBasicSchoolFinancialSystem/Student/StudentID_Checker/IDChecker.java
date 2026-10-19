//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentID_Checker;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Academic.Academic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class IDChecker {
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO
    )
    private int Id;
    private int quantity;
    @ManyToOne
    private Academic academicYear;

    public IDChecker() {
    }

    public int getId() {
        return this.Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Academic getAcademicYear() {
        return this.academicYear;
    }

    public void setAcademicYear(Academic academicYear) {
        this.academicYear = academicYear;
    }

    public IDChecker(int quantity, Academic academicYear) {
        this.quantity = quantity;
        this.academicYear = academicYear;
    }
    
}
