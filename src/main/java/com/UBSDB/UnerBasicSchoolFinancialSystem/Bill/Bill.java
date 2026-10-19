/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Bill;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillEdit.BillEdit;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "Jbill"
)
@Getter
@Setter
public class Bill {
    @Id
    private Long Id;
    @OneToMany(
        mappedBy = "bill"
    )
    private List<BillEdit> BillEdits;

    @OneToMany(mappedBy = "bill")
    private List<SchoolFees> fee;
    @ManyToOne
    private Term term;

    public Bill(Long Id, List<SchoolFees> fee, Term term) {
        this.Id = Id;
        this.fee = fee;
        this.term = term;
    }

    public Bill(List<SchoolFees> fee, Term term) {
        this.fee = fee;
        this.term = term;
    }

    public Bill() {
    }

    public Bill(Long Id) {
        this.Id = Id;
    }
}
