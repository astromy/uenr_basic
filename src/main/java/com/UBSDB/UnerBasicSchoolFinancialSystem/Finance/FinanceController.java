//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.Finance;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillResponse;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill.StudentBill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.Payment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Receipt.Receipt;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Receipt.ReceiptService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.SubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/FinancePortal/"})
public class FinanceController {
    @Autowired
    private FinanceService FS;
    @Autowired
    private StudentService SS;
    @Autowired
    private TermService TS;
    @Autowired
    private ReceiptService RS;
    @Autowired
    private TermService Ts;
    @Autowired
    private SubjectService Ss;
    @Autowired
    private klassService KS;
    @Autowired
    private BillService BS;

    public FinanceController() {
    }

    @GetMapping({"/"})
    public String ShowFinancePortal(Model model, Principal principal) {
        model.addAttribute("user", principal.getName());
        Runnable r1 = () -> {
            try {
                Term t = this.Ts.getCurrentTerm();
                if (this.Ts.getCurrentTerm().getTermName().equals("ThirdTerm") && LocalDate.parse(t.getEndDate()).equals(LocalDate.now())) {
                    System.out.println(t.getTermName());
                }
            } catch (Exception var2) {
                Exception ex = var2;
                System.out.println(ex.getMessage() + ex.toString() + "fatal_error");
            }

        };
        r1.run();
        model.addAttribute("dash_oard", true);
        model.addAttribute("billingForm", true);
        if (this.Ts.getCurrentTerm() == null) {
            model.addAttribute("msg", "Please Contact Admin To Set Current Term");
            return "FinancePortal";
        } else if (this.Ss.Allsubjects().isEmpty() && this.KS.AllKlass().isEmpty()) {
            return "redirect:/klass";
        } else {
            model.addAttribute("ExcelTermsFinance", this.TS.getCurrentTerm().getAcademicyear().getTerm());
            return "FinancePortal";
        }
    }

    @GetMapping({"classes/"})
    public String getClasses(Model model, Principal p) {
        model.addAttribute("classes", this.FS.AllKlasses());
        model.addAttribute("user", p.getName());
        model.addAttribute("Canteen", "Canteen");
        model.addAttribute("Classes", "Classes");
        return "FinancePortal";
    }

    @GetMapping({"StudentList/{ClassId}"})
    public String GetStudentList(Model model, @PathVariable("ClassId") String ClassId) {
        model.addAttribute("StudentList", this.FS.GetStudentBasedOnKlass(ClassId));
        model.addAttribute("ClassId", ClassId);
        return "FinancePortal";
    }

    @GetMapping("/AllFees/")
    public String GetAllFees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "") String term,
            @RequestParam(required = false, defaultValue = "") String academicYear,
            @RequestParam(required = false, defaultValue = "") String level,
            @RequestParam(required = false, defaultValue = "") String month,
            Model model) {

        try {

            Map<Set<String>, SchoolFees> allFees = FS.getAllSchoolFees();

            if (allFees == null || allFees.isEmpty()) {
                model.addAttribute("msg", "Fees not set yet");
                return "FinancePortal";
            }

            /*
             * FILTER + SEARCH
             */
            Map<Set<String>, SchoolFees> filteredFees = allFees.entrySet()
                    .stream()
                    .filter(entry -> {

                        SchoolFees f = entry.getValue();

                        String searchText = search == null
                                ? ""
                                : search.trim().toLowerCase();

                        String feeTerm = "";
                        String feeYear = "";
                        String feeLevel = "";
                        String feeMonth = "";
                        String feeType = "";
                        String description = "";

                        try {
                            if (f.getTerm() != null
                                    && f.getTerm().getTermName() != null) {
                                feeTerm = f.getTerm()
                                        .getTermName()
                                        .toLowerCase();
                            }

                            if (f.getTerm() != null
                                    && f.getTerm().getAcademicyear() != null) {
                                feeYear = String.valueOf(
                                        f.getTerm()
                                                .getAcademicyear()

                                ).toLowerCase();
                            }

                            if (f.getMoth() != null) {
                                feeMonth = String.valueOf(f.getMoth())
                                        .toLowerCase();
                            }

                            if (f.getFeeType() != null) {
                                feeType = f.getFeeType()
                                        .toLowerCase();
                            }

                            description = feeType;

                        } catch (Exception ignored) {
                        }

                        /*
                         * LEVEL
                         */
                        String levels = entry.getKey()
                                .stream()
                                .map(String::toLowerCase)
                                .collect(Collectors.joining(" "));

                        /*
                         * SEARCH
                         */
                        boolean matchesSearch =
                                searchText.isEmpty()
                                        || feeTerm.contains(searchText)
                                        || feeYear.contains(searchText)
                                        || feeMonth.contains(searchText)
                                        || feeType.contains(searchText)
                                        || description.contains(searchText)
                                        || levels.contains(searchText);

                        /*
                         * TERM FILTER
                         */
                        boolean matchesTerm =
                                term == null
                                        || term.trim().isEmpty()
                                        || feeTerm.equalsIgnoreCase(term.trim());


                        boolean matchesYear =
                                academicYear == null
                                        || academicYear.trim().isEmpty()
                                        || feeYear.equalsIgnoreCase(
                                        academicYear.trim().toLowerCase()
                                );


                        boolean matchesLevel =
                                level == null
                                        || level.trim().isEmpty()
                                        || levels.contains(level.trim().toLowerCase());


                        boolean matchesMonth =
                                month == null
                                        || month.trim().isEmpty()
                                        || feeMonth.equalsIgnoreCase(
                                        month.trim().toLowerCase()
                                );

                        return matchesSearch
                                && matchesTerm
                                && matchesYear
                                && matchesLevel
                                && matchesMonth;
                    })
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (a, b) -> a,
                            LinkedHashMap::new
                    ));

             int totalItems = filteredFees.size();

               int totalPages = (int) Math.ceil(
                    (double) totalItems / size
            );

             if (page < 0) {
                page = 0;
            }

            if (totalPages > 0 && page >= totalPages) {
                page = totalPages - 1;
            }

            /*
             * PAGINATION
             */
            List<Map.Entry<Set<String>, SchoolFees>> entries =
                    new ArrayList<>(filteredFees.entrySet());

            int start = page * size;
            int end = Math.min(start + size, entries.size());

            Map<Set<String>, SchoolFees> paginatedFees =
                    new LinkedHashMap<>();

            if (start < end) {
                for (int i = start; i < end; i++) {
                    Map.Entry<Set<String>, SchoolFees> entry = entries.get(i);
                    paginatedFees.put(entry.getKey(), entry.getValue());
                }
            }

            /*
             * SEND DATA TO THYMELEAF
             */
            model.addAttribute("fees", paginatedFees);

            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("totalPages", totalPages);

            /*
             * KEEP SEARCH/FILTER VALUES
             */
            model.addAttribute("search", search);
            model.addAttribute("term", term);
            model.addAttribute("academicYear", academicYear);
            model.addAttribute("level", level);
            model.addAttribute("month", month);

            /*
             * PAGE NUMBERS
             */
            int startPage = Math.max(0, page - 2);
            int endPage = Math.min(totalPages - 1, page + 2);

            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute("msg", "Check later");
        }

        return "FinancePortal";
    }

    @GetMapping({"GetStudentReceipt/{studID}/{amount}/{receiptId}"})
    public String GetStudentReceipt(Model model, @PathVariable("studID") String studId, @PathVariable("receiptId") Long receiptId, @PathVariable("amount") Double amount) throws Exception {
        try {
            Student stud = this.SS.findStudent(studId);
            Receipt r = (Receipt)this.RS.findReceipt(new Receipt(receiptId)).get();
            List<StudentBill> bills = stud.getStudentBills();
            List<Payment> paymets = stud.getPayments();
            Optional<StudentBill> curreBill = bills.stream().filter((c) -> {
                return c.getTerm().equals(this.TS.getCurrentTerm());
            }).findFirst();
            Optional<Payment> currePayment = paymets.stream().filter((c) -> {
                return c.getTerm().equals(this.TS.getCurrentTerm());
            }).findFirst();
            List<Bill> previousbillss = (List)bills.stream().filter((c) -> {
                return LocalDate.parse(c.getTerm().getEndDate()).isBefore(LocalDate.parse(this.TS.getCurrentTerm().getStartDate()));
            }).collect(Collectors.toList());
            List<Payment> previouspay = (List)paymets.stream().filter((c) -> {
                return LocalDate.parse(c.getTerm().getEndDate()).isBefore(LocalDate.parse(this.TS.getCurrentTerm().getStartDate()));
            }).collect(Collectors.toList());
            double PreviousPayme = (Double)previouspay.stream().map(Payment::getAmount).reduce(0.0, (b, f) -> {
                return b + f;
            });
            double previousbill = (Double)previousbillss.stream().flatMap(bill -> bill.getFee().stream()).map(SchoolFees::getAmount).reduce(0.0, (b, f) -> {
                return b + f;
            });
            double amoutBill = 0.0;
            double amoutPay = 0.0;
            if (curreBill.isPresent()) {
                var currentBill = curreBill.get();
                if (currentBill.getFees() != null ) {
                    amoutBill = currentBill.getFees().getAmount();
                }
            }

            if (currePayment.isPresent()) {
                amoutPay = ((Payment)currePayment.get()).getAmount();
            }

            model.addAttribute("AreasFromPreviousTerm", previousbill - PreviousPayme);
            model.addAttribute("ReceiptStudDetailsStud", stud);
            model.addAttribute("CurrentPayment", amount);
            model.addAttribute("CurrentPayment", amount);
            model.addAttribute("ReceiptStudDetails", r);
            model.addAttribute("receiptId", receiptId);
            model.addAttribute("US", r.getPayment().getUser().getFirstname() + " " + r.getPayment().getUser().getLastname());
            model.addAttribute("Dsct", r.getPayment().getDiscount());
            model.addAttribute("paymentType", "Fees");
            model.addAttribute("Payments", amoutPay);
            model.addAttribute("Bal", amount - amoutBill);
            model.addAttribute("term", this.TS.getCurrentTerm());
            model.addAttribute("YEAR", LocalDate.now());
            model.addAttribute("Bal_carry_forward", -(Double)paymets.stream().map(Payment::getAmount).reduce(0.0, (a, p) -> {
                return a + p;
            }) + ((Double)bills.stream().map(StudentBill::getFees).map(SchoolFees::getAmount).reduce(0.0, (z, x) -> {
                return x + z;
            }) - (double)(Float)paymets.stream().map(Payment::getDiscount).reduce(0.0F, (a, p) -> {
                return a + p;
            })));
        } catch (Exception var21) {
            model.addAttribute("err", "Sorry, Receipt was not loaded");
        }

        return "Receipt";
    }

    @GetMapping({"StudentdentBills/{id}"})
    @ResponseBody
    public List<BillResponse> StudentBills(@PathVariable("id") String id) {
        System.out.print("From get Bills API" + id);
        return this.BS.Responses(this.SS.findStudent(id));
    }
}
