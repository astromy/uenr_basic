/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Expenses;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Academic.Academic;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Academic.AcademicService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author enock
 */
@Slf4j
@Controller
@RequestMapping({"/FinancePortal/expenses/"})
public class ExpensesController {

    @Autowired private ExpensesService expensesService;
    @Autowired private TermService TS;
    @Autowired private AcademicService AS;
    @Autowired private UserService userService;

    public ExpensesController() {
    }

    // ---------------------------------------------------------------------
    // Server-rendered list page (table + filters + modals)
    // ---------------------------------------------------------------------

    @GetMapping({"list", ""})
    public String listExpenses(Model model, Principal principal,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(required = false) String search,
                               @RequestParam(required = false) String category,
                               @RequestParam(required = false) String status,
                               @RequestParam(required = false) Long termId,
                               @RequestParam(required = false) Long academicYearId,
                               @RequestParam(required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                               @RequestParam(required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        if (principal != null) {
            model.addAttribute("user", principal.getName());
        }
        model.addAttribute("expensesList", true);
        model.addAttribute("categories", ExpenseCategory.values());
        model.addAttribute("statuses", ExpenseStatus.values());

        try {
            model.addAttribute("terms", TS.getCurrentTerm().getAcademicyear().getTerm());
            model.addAttribute("academicYears", AS.AllAcademic());
        } catch (Exception e) {
            log.warn("Unable to load terms / academic years: {}", e.getMessage());
        }

        try {
            ExpenseFilter filter = new ExpenseFilter(search, category, status,
                    termId, academicYearId, from, to);
            Pageable pageable = PageRequest.of(
                    Math.max(page, 0),
                    Math.min(Math.max(size, 1), 100),
                    Sort.by(Sort.Direction.DESC, "expenseDate")
                            .and(Sort.by(Sort.Direction.DESC, "Id")));
            Page<ExpenseResponse> expensesPage = expensesService.search(filter, pageable);
            model.addAttribute("expensesPage", expensesPage);
            model.addAttribute("filter", filter);
        } catch (Exception e) {
            log.error("Failed to load expenses page", e);
            // Defensive default so the page never 500s
            model.addAttribute("expensesPage",
                    Page.<ExpenseResponse>empty());
        }
        return "FinancePortal";
    }

    // ---------------------------------------------------------------------
    // JSON endpoints used by the table + modals
    // ---------------------------------------------------------------------

    /**
     * Returns a page of expenses as JSON. Used by the client-side table
     * when pagination / filtering needs to happen without a full page reload.
     */
    @GetMapping({"api/list"})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> apiList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long termId,
            @RequestParam(required = false) Long academicYearId,
            @RequestParam(required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        ExpenseFilter filter = new ExpenseFilter(search, category, status,
                termId, academicYearId, from, to);
        Pageable pageable = PageRequest.of(
                Math.max(page, 0),
                Math.min(Math.max(size, 1), 100),
                Sort.by(Sort.Direction.DESC, "expenseDate")
                        .and(Sort.by(Sort.Direction.DESC, "Id")));
        Page<ExpenseResponse> expensesPage = expensesService.search(filter, pageable);

        Map<String, Object> body = new HashMap<>();
        body.put("content", expensesPage.getContent());
        body.put("page", expensesPage.getNumber());
        body.put("size", expensesPage.getSize());
        body.put("totalElements", expensesPage.getTotalElements());
        body.put("totalPages", expensesPage.getTotalPages());
        body.put("first", expensesPage.isFirst());
        body.put("last", expensesPage.isLast());
        return ResponseEntity.ok(body);
    }

    /** Returns a single expense (used to populate the Edit modal). */
    @GetMapping({"api/{id}"})
    @ResponseBody
    public ResponseEntity<ExpenseResponse> apiGet(@PathVariable Long id) {
        Expenses e = expensesService.findExpenseById(id);
        if (e == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(e.getResponse());
    }

    @PostMapping({"api/save"})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> apiSave(@RequestBody
                                                           ExpensePayload payload,
                                                       Principal principal) {

        log.info(payload.toString());

        return saveOrUpdate(payload, principal, false);
    }

    @PostMapping({"api/update"})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> apiUpdate(@RequestBody ExpensePayload payload,
                                                         Principal principal) {
        return saveOrUpdate(payload, principal, true);
    }

    @PostMapping({"api/delete/{id}"})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> apiDelete(@PathVariable Long id) {
        String msg = expensesService.deleteExpense(id);
        Map<String, Object> body = new HashMap<>();
        body.put("success", msg.toLowerCase().contains("success"));
        body.put("message", msg);
        return ResponseEntity.ok(body);
    }

    private ResponseEntity<Map<String, Object>> saveOrUpdate(ExpensePayload payload,
                                                             Principal principal,
                                                             boolean isUpdate) {
        Map<String, Object> body = new HashMap<>();
        try {
            Expenses expense = isUpdate
                    ? expensesService.findExpenseById(payload.getId())
                    : new Expenses();
            if (isUpdate && expense == null) {
                body.put("success", false);
                body.put("message", "Expense not found");
                return ResponseEntity.status(404).body(body);
            }

            expense.setExpensesType(payload.getExpensesType());
            expense.setCategory(payload.getCategory());
            expense.setDescription(payload.getDescription());
            expense.setAmount(payload.getAmount());
            expense.setExpenseDate(payload.getExpenseDate() != null
                    ? payload.getExpenseDate() : LocalDate.now());
            expense.setPaymentMethod(payload.getPaymentMethod());
            expense.setReferenceNumber(payload.getReferenceNumber());
            expense.setStatus(payload.getStatus() != null
                    ? payload.getStatus() : "PENDING");

            if (payload.getTermId() != null) {
                TS.findTermById(payload.getTermId()).ifPresent(term -> {
                    expense.setTerm(term);
                    if (term.getAcademicyear() != null) {
                        Academic ay = AS.FindAcademic(term.getAcademicyear());
                        expense.setAcademicYear(ay);
                    }
                });
            } else {
                expense.setTerm(null);
                expense.setAcademicYear(null);
            }

            if (principal != null) {
                users user = userService.FindUserByUserName(principal);
                if (user != null) {
                    expense.setUser(user);
                }
            }

            String msg = expensesService.saveExpense(expense);
            body.put("success", msg.toLowerCase().contains("success"));
            body.put("message", msg);
            body.put("expense", expense.getResponse());
            return ResponseEntity.ok(body);
        } catch (Exception e) {
            log.error("Failed to save expense", e);
            body.put("success", false);
            body.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(500).body(body);
        }
    }

    // ---------------------------------------------------------------------
    // Legacy endpoints (kept for compatibility with the existing forms)
    // ---------------------------------------------------------------------

    @GetMapping({"add"})
    public String showAddExpenseForm(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("user", principal.getName());
        }
        model.addAttribute("expense", new Expenses());
        model.addAttribute("addExpense", true);
        populateLookups(model);
        return "FinancePortal";
    }

    @PostMapping({"save"})
    public String saveExpense(@ModelAttribute Expenses expense,
                              @RequestParam(required = false) Long termId,
                              @RequestParam(required = false) Long academicYearId,
                              Principal principal, Model model) {
        try {
            if (termId != null) {
                TS.findTermById(termId).ifPresent(term -> {
                    expense.setTerm(term);
                    if (term.getAcademicyear() != null) {
                        expense.setAcademicYear(AS.FindAcademic(term.getAcademicyear()));
                    }
                });
            }
            if (principal != null) {
                users user = userService.FindUserByUserName(principal);
                if (user != null) {
                    expense.setUser(user);
                }
            }
            if (expense.getExpenseDate() == null) {
                expense.setExpenseDate(LocalDate.now());
            }
            if (expense.getStatus() == null || expense.getStatus().isEmpty()) {
                expense.setStatus("PENDING");
            }
            model.addAttribute("msg", expensesService.saveExpense(expense));
        } catch (Exception e) {
            model.addAttribute("msg", "Error saving expense: " + e.getMessage());
        }
        return "redirect:/FinancePortal/expenses/list";
    }

    @GetMapping({"edit/{id}"})
    public String showEditExpenseForm(@PathVariable("id") Long id, Model model,
                                      Principal principal) {
        if (principal != null) {
            model.addAttribute("user", principal.getName());
        }
        Expenses expense = expensesService.findExpenseById(id);
        if (expense == null) {
            model.addAttribute("msg", "Expense not found");
            return "redirect:/FinancePortal/expenses/list";
        }
        model.addAttribute("expense", expense);
        model.addAttribute("editExpense", true);
        populateLookups(model);
        return "FinancePortal";
    }

    @PostMapping({"update"})
    public String updateExpense(@ModelAttribute Expenses expense,
                                @RequestParam(required = false) Long termId,
                                @RequestParam(required = false) Long academicYearId,
                                Principal principal, Model model) {
        try {
            if (termId != null) {
                TS.findTermById(termId).ifPresent(term -> {
                    expense.setTerm(term);
                    if (term.getAcademicyear() != null) {
                        expense.setAcademicYear(AS.FindAcademic(term.getAcademicyear()));
                    }
                });
            }
            if (principal != null) {
                users user = userService.FindUserByUserName(principal);
                if (user != null) {
                    expense.setUser(user);
                }
            }
            model.addAttribute("msg", expensesService.saveExpense(expense));
        } catch (Exception e) {
            model.addAttribute("msg", "Error updating expense: " + e.getMessage());
        }
        return "redirect:/FinancePortal/expenses/list";
    }

    @GetMapping({"delete/{id}"})
    public String deleteExpense(@PathVariable("id") Long id) {
        expensesService.deleteExpense(id);
        return "redirect:/FinancePortal/expenses/list";
    }

    @GetMapping({"report"})
    public String showExpenseReport(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("user", principal.getName());
        }
        model.addAttribute("expenseReport", true);
        try {
            Term currentTerm = TS.getCurrentTerm();
            Academic currentAcademic = currentTerm.getAcademicyear();
            model.addAttribute("currentTerm", currentTerm);
            model.addAttribute("currentAcademic", currentAcademic);
            model.addAttribute("totalExpensesTerm",
                    expensesService.getTotalExpensesByTerm(currentTerm));
            model.addAttribute("totalExpensesYear",
                    expensesService.getTotalExpensesByAcademicYear(currentAcademic));
            model.addAttribute("expensesByCategoryTerm",
                    expensesService.getExpensesByCategoryForTerm(currentTerm));
            model.addAttribute("expensesByCategoryYear",
                    expensesService.getExpensesByCategoryForAcademicYear(currentAcademic));
            model.addAttribute("termExpenses",
                    expensesService.ListAllExpensesTermly(currentTerm));
        } catch (Exception e) {
            model.addAttribute("msg", "Error loading report: " + e.getMessage());
        }
        return "FinancePortal";
    }

    @GetMapping({"financial-summary"})
    public String showFinancialSummary(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("user", principal.getName());
        }
        model.addAttribute("financialSummary", true);
        try {
            Term currentTerm = TS.getCurrentTerm();
            Academic currentAcademic = currentTerm.getAcademicyear();
            model.addAttribute("currentTerm", currentTerm);
            model.addAttribute("currentAcademic", currentAcademic);
            double totalExpenses = expensesService.getTotalExpensesByTerm(currentTerm);
            model.addAttribute("expensesByCategoryTerm",
                    expensesService.getExpensesByCategoryForTerm(currentTerm));
            model.addAttribute("totalRevenue", 0.0);
            model.addAttribute("totalExpenses", totalExpenses);
            model.addAttribute("netProfit", -totalExpenses);
        } catch (Exception e) {
            model.addAttribute("msg", "Error loading financial summary: " + e.getMessage());
        }
        return "FinancePortal";
    }

    // ---------------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------------

    private void populateLookups(Model model) {
        try {
            model.addAttribute("terms", TS.terms());
            model.addAttribute("academicYears", AS.AllAcademic());
        } catch (Exception e) {
            model.addAttribute("msg", "Please set current term and academic year");
        }
    }
}
