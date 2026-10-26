/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Bill;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill.StudentBill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.Payment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.PaymentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Receipt.ReceiptService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.role;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BillController {
    @Autowired
    private StudentService SS;
    @Autowired
    private PaymentService PS;
    @Autowired
    private TermService TS;
    @Autowired
    private BillService BS;
    @Autowired
    private klassService KS;
    @Autowired
    private UserService US;
    @Autowired
    private ReceiptService RS;

    public BillController() {
    }

    @GetMapping({"/SearchForm/"})
    public String SearchForm(Model model, Principal pp) {
        model.addAttribute("searchfss", true);
        users user = this.US.FindUserById(pp.getName());
        Iterator var4 = user.roles.iterator();
        if (var4.hasNext()) {
            role r = (role)var4.next();
            if (r.getName().equals("Teacher")) {
                return "TeacherPortal";
            } else {
                return r.getName().equals("Admin") ? "index" : "FinancePortal";
            }
        } else {
            return "index";
        }
    }

    @GetMapping({"/Payments/"})
    public String DailyPayments(@RequestParam("sdate") String sdate, @RequestParam("Edate") String Edate, Model model, Principal pp) {
        List<Payment> payments = this.PS.AllPayments();
        payments.removeIf((c) -> {
            return c.getDate().isBefore(LocalDate.parse(sdate)) || c.getDate().isAfter(LocalDate.parse(Edate));
        });
        model.addAttribute("Payments", payments);
        model.addAttribute("TotalPayments", payments.stream().map(Payment::getAmount).reduce(0.0, (t, f) -> {
            return t + f;
        }));
        users user = this.US.FindUserById(pp.getName());
        Iterator var7 = user.roles.iterator();
        if (var7.hasNext()) {
            role r = (role)var7.next();
            switch (r.getName()) {
                case "Admin":
                    return "index";
                default:
                    return "FinancePortal";
            }
        } else {
            return "index";
        }
    }

    @GetMapping({"/Defaulters/{classId}"})
    public String DailyPayments(@PathVariable("classId") String classId, Model model) {
        List<Student> students = new ArrayList();
        this.KS.FindKlass(classId).getStudents().stream().forEach((c) -> {
            double outcome = (Double)c.getPayments().stream().map(Payment::getAmount).reduce(0.0, (b, o) -> {
                return b + o;
            }) - (Double)c.getStudentBills().stream().map(StudentBill::getFees).map(SchoolFees::getAmount)
                    .reduce(0.0,Double::sum);
            if (outcome < 0.0) {
                students.add(c);
            }

        });
        List<Payment> payments = new ArrayList();
        students.stream().forEach((c) -> {
            Payment p = new Payment();
            p.setStudent(c);
            p.setAmount(c.getPayments().stream().map(Payment::getAmount).reduce(0.0, (v, g) -> {
                return v + g;
            }));
            p.setRemaining(p.getAmount() - c.getStudentBills().stream().map(StudentBill::getFees).map(SchoolFees::getAmount)
                    .reduce(0.0,Double::sum));
            p.setPaymentType("fees");
            payments.add(p);
        });
        model.addAttribute("defaulters", payments);
        return "FinancePortal";
    }

    @GetMapping({"/Defaulters/"})
    public String DailyPayments(Model model, HttpServletRequest request) {
        List<Student> students = new ArrayList();
        this.SS.AALLStudents().stream().forEach((c) -> {
            double outcome = (Double)c.getPayments().stream().map(Payment::getAmount).reduce(0.0, (b, o) -> {
                return b + o;
            }) - (Double)c.getStudentBills().stream().map(StudentBill::getFees).map(SchoolFees::getAmount)
                    .reduce(0.0,Double::sum);
            if (outcome < 0.0) {
                students.add(c);
            }

        });
        List<Payment> payments = new ArrayList();
        students.stream().forEach((c) -> {
            Payment p = new Payment();
            p.setStudent(c);
            p.setAmount(c.getPayments().stream().map(Payment::getAmount).reduce(0.0, (v, g) -> {
                return v + g;
            }));
            p.setRemaining(p.getAmount() - c.getStudentBills().stream().map(StudentBill::getFees).map(SchoolFees::getAmount)
                    .reduce(0.0,Double::sum));
            p.setPaymentType("fees");
            payments.add(p);
        });
        model.addAttribute("defaulters", payments.stream().sorted(Comparator.comparing((y) -> {
            return y.getStudent().getStudentklass().getClassName();
        })).collect(Collectors.toList()));
        model.addAttribute("Amout_not_Paid", payments.stream().map(Payment::getRemaining).reduce(0.0, (b, o) -> {
            return b + o;
        }));
        request.getSession().setAttribute("defaulterss", payments);
        request.getSession().setAttribute("Amout_not_Paidd", payments.stream().map(Payment::getRemaining).reduce(0.0, (b, o) -> {
            return b + o;
        }));
        return "index";
    }

    @GetMapping({"/PriDefaulters/"})
    public String pritDefaulters(Model model, HttpSession sess) {
        try {
            List<Payment> payments = (List)sess.getAttribute("defaulterss");
            Object amtT = sess.getAttribute("Amout_not_Paidd");
            model.addAttribute("defaulters", payments);
            model.addAttribute("Amout_not_Paid", amtT);
            return "Receipt";
        } catch (Exception var5) {
            Exception ex = var5;
            model.addAttribute("msg", " SORRY...... " + ex.getMessage());
            return "index";
        }
    }

    @GetMapping("/deleteAllBills")
    public void deleteBillForStudents() {
        this.BS.deleteBills();
    }

    @GetMapping("/getStudentBills/{StudentId}")
    public List<SchoolFees> getAllStudentBills(@PathVariable("StudentId") String studentId) {
        Optional<Student> studentOpt = this.SS.findOptionalStudent(studentId);
        if (studentOpt.isPresent()) {
            return studentOpt.get().getStudentBills().stream().map(StudentBill::getFees).toList();
        }
        Optional<Student> refStudentOpt = this.SS.findOptionalStudentByReference(studentId);

        return studentOpt.get().getStudentBills().stream().map(StudentBill::getFees).toList();
    }

    @GetMapping({"/GetStudentBill/{BillId}"})
    @ResponseBody
    public BillResponse getStudentBill(@PathVariable("BillId") Long BillId) {
        try {
            return this.BS.getStudentBill(BillId);
        } catch (Exception var3) {
            Exception ex = var3;
            System.out.println(ex);
            return new BillResponse();
        }
    }
}

