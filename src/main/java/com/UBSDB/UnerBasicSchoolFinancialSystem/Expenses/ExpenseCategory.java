package com.UBSDB.UnerBasicSchoolFinancialSystem.Expenses;

/**
 * Centralized list of expense categories used by both the UI dropdowns and
 * the filter / validation logic.
 */
public enum ExpenseCategory {
    SALARIES("Salaries"),
    UTILITIES("Utilities"),
    SUPPLIES("Supplies"),
    MAINTENANCE("Maintenance"),
    TRANSPORT("Transport"),
    FOOD("Food/Catering"),
    FUEL("Fuel"),
    REPAIRS("Repairs"),
    OTHER("Other");

    private final String display;

    ExpenseCategory(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }

    public String code() {
        return name();
    }
}
