package com.UBSDB.UnerBasicSchoolFinancialSystem.Expenses;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Academic.Academic;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO used to render expense rows in the UI. Decouples the JPA entity from
 * the template so we can add fields like id, username, and academic year
 * metadata without polluting the persisted model.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponse {
    private Long id;
    private String expensesType;
    private String category;
    private String description;
    private double amount;
    private LocalDate expenseDate;
    private String paymentMethod;
    private String referenceNumber;
    private String status;
    private Long termId;
    private String termName;
    private Long academicYearId;
    private String academicYearName;
    private String username;

    /**
     * Convenience: create a response from the JPA entity, tolerating null
     * relations so a single bad row does not break the whole listing.
     */
    public static ExpenseResponse from(Expenses e) {
        if (e == null) {
            return null;
        }
        Term term = e.getTerm();
        Academic academicYear = e.getAcademicYear();
        return ExpenseResponse.builder()
                .id(e.getId())
                .expensesType(e.getExpensesType())
                .category(e.getCategory())
                .description(e.getDescription())
                .amount(e.getAmount())
                .expenseDate(e.getExpenseDate())
                .paymentMethod(e.getPaymentMethod())
                .referenceNumber(e.getReferenceNumber())
                .status(e.getStatus())
                .termId(term != null ? term.getId() : null)
                .termName(term != null ? term.getTermName() : null)
                .academicYearId(academicYear != null ? academicYear.getId() : null)
                .academicYearName(academicYear != null ? academicYear.getAcademicName() : null)
                .username(e.getUser() != null ? e.getUser().getUsername() : null)
                .build();
    }
}
