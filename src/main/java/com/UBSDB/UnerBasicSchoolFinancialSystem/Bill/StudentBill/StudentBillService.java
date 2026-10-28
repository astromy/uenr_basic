/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author enock
 */
@Service
public interface StudentBillService {
    StudentBill saveStudentBill(StudentBill studentBill);
    Optional<StudentBill> findStudentBillById(UUID id);
    List<StudentBill> findAllStudentBills();
    List<StudentBill> findStudentBillsByStudent(Student student);
    List<StudentBill> findStudentBillsByStudentId(String studentId);
    List<StudentBill> findStudentBillsByBill(SchoolFees bill);
    void deleteStudentBill(UUID id);
    boolean existsStudentBill(UUID id);
}
