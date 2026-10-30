/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author enock
 */
@Component
public class StudentBillServiceImpl implements StudentBillService {
    @Autowired
    private StudentBillRepository studentBillRepository;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private BillService billService;

    public StudentBillServiceImpl() {
    }

    @Override
    public StudentBill saveStudentBill(StudentBill studentBill) {
        try {
            if (studentBill.getFees() != null && studentBill.getTerm() == null) {
                studentBill.setTerm(studentBill.getFees().getTerm());
            }
            // Ensure the amount is set from the SchoolFees amount if not already set
            if (studentBill.getFees() != null && studentBill.getAmount() == 0.0) {
                studentBill.setAmount((float) studentBill.getFees().getAmount());
            }
            return studentBillRepository.save(studentBill);
        } catch (Exception e) {
            System.out.println("Error saving StudentBill: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<StudentBill> findStudentBillById(UUID id) {
        return studentBillRepository.findById(id);
    }

    @Override
    public List<StudentBill> findAllStudentBills() {
        return studentBillRepository.findAll();
    }

    @Override
    public List<StudentBill> findStudentBillsByStudent(Student student) {
        return studentBillRepository.findByStudent(student);
    }

    @Override
    public List<StudentBill> findStudentBillsByStudentId(String studentId) {
        return studentService.findStudent(studentId).getStudentBills();
    }

    @Override
    public List<StudentBill> findStudentBillsByBill(SchoolFees bill) {
        return studentBillRepository.findByFees(bill);
    }

    @Override
    public void deleteStudentBill(UUID id) {
        studentBillRepository.deleteById(id);
    }

    @Override
    public boolean existsStudentBill(UUID id) {
        return studentBillRepository.existsById(id);
    }
}
