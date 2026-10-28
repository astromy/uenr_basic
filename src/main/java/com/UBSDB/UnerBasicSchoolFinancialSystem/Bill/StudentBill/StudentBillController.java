/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author enock
 */
@Controller
public class StudentBillController {
    @Autowired
    private StudentBillService studentBillService;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private BillService billService;

    public StudentBillController() {
    }

    @PostMapping({"/saveStudentBill"})
    @ResponseBody
    public StudentBillResponse saveStudentBill(@RequestParam("studentId") String studentId, @RequestParam("billId") Long billId) {
        try {
            Student student = studentService.findStudent(studentId);
            Bill bill = billService.FindstudentBill(billId).orElse(null);
            
            if (student == null || bill == null) {
                return new StudentBillResponse();
            }
            
            StudentBill studentBill = new StudentBill();
            studentBill.setStudent(student);

            
            StudentBill saved = studentBillService.saveStudentBill(studentBill);
            if (saved != null) {
                return new StudentBillResponse(saved);
            }
            return new StudentBillResponse();
        } catch (Exception e) {
            System.out.println("Error saving StudentBill: " + e.getMessage());
            return new StudentBillResponse();
        }
    }

    @GetMapping({"/getStudentBills/{studentId}"})
    @ResponseBody
    public List<StudentBillResponse> getStudentBills(@PathVariable("studentId") String studentId) {
        try {
            List<StudentBill> studentBills = studentBillService.findStudentBillsByStudentId(studentId);
            return studentBills.stream()
                    .map(StudentBillResponse::new)
                    .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            System.out.println("Error getting StudentBills: " + e.getMessage());
            return List.of();
        }
    }

    @GetMapping({"/getStudentBill/{id}"})
    @ResponseBody
    public StudentBillResponse getStudentBill(@PathVariable("id") UUID id) {
        try {
            return studentBillService.findStudentBillById(id)
                    .map(StudentBillResponse::new)
                    .orElse(new StudentBillResponse());
        } catch (Exception e) {
            System.out.println("Error getting StudentBill: " + e.getMessage());
            return new StudentBillResponse();
        }
    }

    @GetMapping({"/getAllStudentBills"})
    @ResponseBody
    public List<StudentBillResponse> getAllStudentBills() {
        try {
            List<StudentBill> studentBills = studentBillService.findAllStudentBills();
            return studentBills.stream()
                    .map(StudentBillResponse::new)
                    .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            System.out.println("Error getting all StudentBills: " + e.getMessage());
            return List.of();
        }
    }

    @DeleteMapping({"/deleteStudentBill/{id}"})
    @ResponseBody
    public String deleteStudentBill(@PathVariable("id") UUID id) {
        try {
            if (studentBillService.existsStudentBill(id)) {
                studentBillService.deleteStudentBill(id);
                return "StudentBill deleted successfully";
            }
            return "StudentBill not found";
        } catch (Exception e) {
            System.out.println("Error deleting StudentBill: " + e.getMessage());
            return "Error deleting StudentBill: " + e.getMessage();
        }
    }
}
