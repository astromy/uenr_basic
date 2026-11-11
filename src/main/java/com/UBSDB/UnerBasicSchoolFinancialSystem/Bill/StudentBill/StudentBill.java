package com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class StudentBill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID studBillId;
    @ManyToOne
    private Student student;
    @ManyToOne
    private SchoolFees fees;
    private float amount;
    @ManyToOne
    private Term term;

}
