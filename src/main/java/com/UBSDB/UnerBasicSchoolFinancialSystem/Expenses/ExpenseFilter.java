package com.UBSDB.UnerBasicSchoolFinancialSystem.Expenses;

import java.time.LocalDate;

/**
 * Immutable filter used by {@link ExpensesService#search(ExpenseFilter,
 * org.springframework.data.domain.Pageable)}. Any {@code null} or blank field
 * is ignored, allowing callers to pass only the criteria they care about.
 */
public record ExpenseFilter(
        String search,
        String category,
        String status,
        Long termId,
        Long academicYearId,
        LocalDate from,
        LocalDate to
) {
    public static ExpenseFilter empty() {
        return new ExpenseFilter(null, null, null, null, null, null, null);
    }
}
