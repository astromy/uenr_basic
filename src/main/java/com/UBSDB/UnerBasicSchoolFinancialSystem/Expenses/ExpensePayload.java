package com.UBSDB.UnerBasicSchoolFinancialSystem.Expenses;

import lombok.Data;

import java.time.LocalDate;

/**
 * Lightweight DTO used by the JSON API endpoints (add / edit modal) so the
 * frontend does not have to know about the JPA entity.
 */
@Data
public class ExpensePayload {
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
    private Long academicYearId;
}
