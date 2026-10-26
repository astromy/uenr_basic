package com.UBSDB.UnerBasicSchoolFinancialSystem.Expenses;

/**
 * Lifecycle status of an expense.
 */
public enum ExpenseStatus {
    PENDING("Pending"),
    PAID("Paid"),
    CANCELLED("Cancelled");

    private final String display;

    ExpenseStatus(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }

    public String code() {
        return name();
    }
}
