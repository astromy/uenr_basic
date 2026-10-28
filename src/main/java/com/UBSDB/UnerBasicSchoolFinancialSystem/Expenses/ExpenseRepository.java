/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Expenses;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Academic.Academic;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author enock
 */
@Repository
public interface ExpenseRepository
        extends JpaRepository<Expenses, Long>, JpaSpecificationExecutor<Expenses> {

    @Query("SELECT COALESCE(SUM(e.Amount), 0.0) FROM Expenses e WHERE e.term = :term")
    double sumExpensesByTerm(@Param("term") Term term);

    @Query("SELECT COALESCE(SUM(e.Amount), 0.0) FROM Expenses e WHERE e.academicYear = :academicYear")
    double sumExpensesByAcademicYear(@Param("academicYear") Academic academicYear);

    @Query("SELECT COALESCE(SUM(e.Amount), 0.0) FROM Expenses e WHERE e.term = :term AND e.category = :category")
    double sumExpensesByTermAndCategory(@Param("term") Term term, @Param("category") String category);

    @Query("SELECT COALESCE(SUM(e.Amount), 0.0) FROM Expenses e WHERE e.academicYear = :academicYear AND e.category = :category")
    double sumExpensesByAcademicYearAndCategory(@Param("academicYear") Academic academicYear, @Param("category") String category);

    @Query("SELECT e.category, COALESCE(SUM(e.Amount), 0.0) FROM Expenses e WHERE e.term = :term GROUP BY e.category")
    List<Object[]> sumExpensesByCategoryForTerm(@Param("term") Term term);

    @Query("SELECT e.category, COALESCE(SUM(e.Amount), 0.0) FROM Expenses e WHERE e.academicYear = :academicYear GROUP BY e.category")
    List<Object[]> sumExpensesByCategoryForAcademicYear(@Param("academicYear") Academic academicYear);

    /**
     * Helper used to validate date-range inputs without hitting the DB.
     */
    default boolean isValidDateRange(LocalDate from, LocalDate to) {
        return from == null || to == null || !from.isAfter(to);
    }
}
