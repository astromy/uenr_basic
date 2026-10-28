/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Expenses;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Academic.Academic;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author enock
 */
@Slf4j
@Service
public class ExpensesService {
    @Autowired private ExpenseRepository expenseRepository;

    // ---------------------------------------------------------------------
    // CRUD
    // ---------------------------------------------------------------------

    public String saveExpense(Expenses expense) {
        try {
            expenseRepository.save(expense);
            log.info("saved successfully");
            return "Saved Successfully";
        } catch (Exception ex) {
            log.error(ex.toString());
            return "Error please contact admin " + ex.toString();
        }
    }

    public Expenses findExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    public List<ExpenseResponse> findAllExpenses() {
        return expenseRepository.findAll(
                Sort.by(Sort.Direction.DESC, "expenseDate")
        ).stream().map(Expenses::getResponse).toList();
    }

    public Page<ExpenseResponse> findAllExpenses(Pageable pageable) {
        return expenseRepository.findAll(pageable).map(Expenses::getResponse);
    }

    public String deleteExpense(Long id) {
        try {
            expenseRepository.deleteById(id);
            return "Deleted Successfully";
        } catch (Exception ex) {
            return "Error deleting expense: " + ex.toString();
        }
    }

    // ---------------------------------------------------------------------
    // Filterable / paginated listing
    // ---------------------------------------------------------------------

    /**
     * Returns a paginated, filtered list of expenses.
     *
     * Empty/blank filters are ignored, so passing {@code null} for every
     * argument returns the same result as the unfiltered listing.
     */
    public Page<ExpenseResponse> search(ExpenseFilter filter, Pageable pageable) {
        Specification<Expenses> spec = buildSpecification(filter);
        Page<Expenses> page = expenseRepository.findAll(spec, pageable);
        List<ExpenseResponse> mapped = page.getContent().stream()
                .map(Expenses::getResponse)
                .toList();
        return new PageImpl<>(mapped, pageable, page.getTotalElements());
    }

    private Specification<Expenses> buildSpecification(ExpenseFilter f) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (f == null) {
                return cb.conjunction();
            }
            if (StringUtils.hasText(f.category())) {
                predicates.add(cb.equal(cb.upper(root.get("category")),
                        f.category().toUpperCase()));
            }
            if (StringUtils.hasText(f.status())) {
                predicates.add(cb.equal(cb.upper(root.get("status")),
                        f.status().toUpperCase()));
            }
            if (StringUtils.hasText(f.search())) {
                String like = "%" + f.search().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("description")), like),
                        cb.like(cb.lower(root.get("referenceNumber")), like)
                ));
            }
            if (f.termId() != null) {
                predicates.add(cb.equal(root.get("term").get("id"), f.termId()));
            }
            if (f.academicYearId() != null) {
                predicates.add(cb.equal(root.get("academicYear").get("id"),
                        f.academicYearId()));
            }
            if (f.from() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("expenseDate"), f.from()));
            }
            if (f.to() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("expenseDate"), f.to()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    // ---------------------------------------------------------------------
    // Legacy listing helpers (kept for the report / dashboard flows)
    // ---------------------------------------------------------------------

    public List<Expenses> ListAllExpensesTermly(Term term) {
        List<Expenses> allExpenses = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(term.getStartDate());
        LocalDate endDate = LocalDate.parse(term.getEndDate());

        for (Expenses ex : expenseRepository.findAll()) {
            LocalDate expenseDate = ex.getExpenseDate();
            if ((expenseDate.isEqual(startDate) || expenseDate.isAfter(startDate)) &&
               (expenseDate.isEqual(endDate) || expenseDate.isBefore(endDate))) {
                allExpenses.add(ex);
            }
        }
        return allExpenses;
    }

    public List<Expenses> ListAllExpensesByAcademicYear(Academic academicYear) {
        List<Expenses> allExpenses = new ArrayList<>();
        for (Expenses ex : expenseRepository.findAll()) {
            if (ex.getExpenseDate().getYear() == academicYear.getAcademicYear().getYear()) {
                allExpenses.add(ex);
            }
        }
        return allExpenses;
    }

    public List<Expenses> MonthlyExpenses(LocalDate date) {
        List<Expenses> allExpenses = new ArrayList<>();
        for (Expenses ex : expenseRepository.findAll()) {
            if (ex.getExpenseDate().getMonth().equals(date.getMonth()) &&
               ex.getExpenseDate().getYear() == date.getYear()) {
                allExpenses.add(ex);
            }
        }
        return allExpenses;
    }

    // ---------------------------------------------------------------------
    // Reporting
    // ---------------------------------------------------------------------

    public double getTotalExpensesByTerm(Term term) {
        return expenseRepository.sumExpensesByTerm(term);
    }

    public double getTotalExpensesByAcademicYear(Academic academicYear) {
        return expenseRepository.sumExpensesByAcademicYear(academicYear);
    }

    public double getTotalExpensesByTermAndCategory(Term term, String category) {
        return expenseRepository.sumExpensesByTermAndCategory(term, category);
    }

    public double getTotalExpensesByAcademicYearAndCategory(Academic academicYear, String category) {
        return expenseRepository.sumExpensesByAcademicYearAndCategory(academicYear, category);
    }

    public Map<String, Double> getExpensesByCategoryForTerm(Term term) {
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Object[] result : expenseRepository.sumExpensesByCategoryForTerm(term)) {
            categoryTotals.put((String) result[0], (Double) result[1]);
        }
        return categoryTotals;
    }

    public Map<String, Double> getExpensesByCategoryForAcademicYear(Academic academicYear) {
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Object[] result : expenseRepository.sumExpensesByCategoryForAcademicYear(academicYear)) {
            categoryTotals.put((String) result[0], (Double) result[1]);
        }
        return categoryTotals;
    }

    public double getTotalExpenses() {
        return expenseRepository.findAll().stream().mapToDouble(Expenses::getAmount).sum();
    }
}
