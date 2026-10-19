//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.PDF;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.AssessmentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.MockDetails.MockDetailsService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.MockDetails.mockDetails;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AssessmentPDFController {
    @Autowired
    AssessmentService AS;
    @Autowired
    AssessmentPDFService APDFS;
    @Autowired
    StudentService SS;
    @Autowired
    TermService TS;
    @Autowired
    private MultipleAssessmentPDFService MPS;
    @Autowired
    private MultipleMockPDFService1 MMPS;
    @Autowired
    private MockDetailsService mDS;
    @Autowired
    klassService CS;

    public AssessmentPDFController() {
    }

    @GetMapping({"/Get/StudentAssessmentCurrentTermPDF/{StudentId}/{TermId}"})
    public void StudentAssessmentCurrentTermPDF(HttpServletResponse response, @PathVariable("StudentId") String StudentId, @PathVariable("TermId") Long TermId) throws IOException {
        response.setContentType("application/pdf");
        Term t = (Term)this.TS.findTermById(TermId).get();
        Student stud = this.SS.findStudent(StudentId);
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=" + stud.getStudentId() + "_" + t.getTermName() + "_" + LocalDate.now() + "_Assessment.pdf";
        response.addHeader(headerKey, headerValue);
        this.APDFS.setStudent(stud);
        this.APDFS.setTerm(t);
        this.APDFS.export(response, t);
    }

    @GetMapping({"/Get/StudentAssessmentCurrentTermPDF/{TermId}"})
    public void StudentAssessmentCurrentTermPDF(HttpServletResponse response, Principal p, @PathVariable("TermId") Long TermId) throws IOException {
        response.setContentType("application/pdf");
        Term t = (Term)this.TS.findTermById(TermId).get();
        Student stud = this.SS.findStudent(p.getName());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=" + stud.getStudentId() + "_" + t.getTermName() + "_" + LocalDate.now() + "_Assessment.pdf";
        response.addHeader(headerKey, headerValue);
        this.APDFS.setStudent(stud);
        this.APDFS.setTerm(t);
        this.APDFS.export(response, t);
    }

    @GetMapping({"/Get/StudentsCurrentTermPDF/{KlassId}/{TermId}"})
    public String StudentsAssessmentCurrentTermPDF1(HttpServletResponse response, ModelMap m, @PathVariable("KlassId") String KlassIdId, @PathVariable("TermId") Long TermId) throws IOException {
        Term t = (Term)this.TS.findTermById(TermId).get();

        try {
            if (!this.TS.findNextTermBasedOnTerm(t).isPresent() && this.TS.findNextTermBasedOnTerm(t).get() == null) {
                m.addAttribute("msg", "Please Contact Admin to set next term");
                return "index";
            } else {
                return "redirect:/Get/StudentsCurrentTermAssessmePDF/" + KlassIdId + "/" + TermId;
            }
        } catch (Exception var7) {
            m.addAttribute("msg", "Please Contact Admin to set next term");
            return "index";
        }
    }

    @GetMapping({"/Get/StudentsCurrentTermAssessmePDF/{KlassId}/{TermId}"})
    public String StudentsAssessmentCurrentTermPDF(HttpServletResponse response, ModelMap m, @PathVariable("KlassId") String KlassIdId, @PathVariable("TermId") Long TermId) throws IOException {
        klass k = new klass();
        k.setClassName(KlassIdId);
        String headerKey = "Content-Disposition";
        Term t = (Term)this.TS.findTermById(TermId).get();
        this.MPS.setStuds(this.SS.GetStudents_In_A_Particular_Class(k));
        String headerValue = "attachment;filename=" + KlassIdId + "_" + t.getTermName() + "_" + LocalDate.now() + "_Assessment.pdf";
        response.addHeader(headerKey, headerValue);
        this.MPS.setTerm(t);
        this.MPS.export(response, t);
        return "index";
    }

    @GetMapping({"/Get/MockAssessmePDF/"})
    public String MockAssessmePDF(HttpServletResponse response, ModelMap m, @RequestParam("id") Long id) throws IOException {
        try {
            klass k = new klass();
            k.setClassName("JHS3A");
            String headerKey = "Content-Disposition";
            mockDetails md = (mockDetails)this.mDS.findMockDetails(id).get();
            this.MMPS.setStuds(this.SS.GetStudents_In_A_Particular_Class(k));
            String headerValue = "attachment;filename=" + k.getClassName() + "_" + md.getMockName() + "_" + LocalDate.now() + "_Assessment.pdf";
            response.addHeader(headerKey, headerValue);
            this.MMPS.export(response, md);
        } catch (Exception var8) {
            Exception ex = var8;
            m.addAttribute("msg", ex);
            System.out.println(ex.toString());
        }

        return "index";
    }
}
